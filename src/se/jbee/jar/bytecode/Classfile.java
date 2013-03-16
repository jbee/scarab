package se.jbee.jar.bytecode;

import static se.jbee.jar.TypeRef.typeRef;

import java.io.IOException;

import se.jbee.jar.JarProcessor;
import se.jbee.jar.Modifiers;
import se.jbee.jar.Type;
import se.jbee.jar.TypeRef;

public final class Classfile {

	private static final int MAGIC_NUMBER = 0xcafebabe;

	/**
	 * Extracts the constant pool from the specified data stream of a class file.
	 * 
	 * @param in
	 *            Input stream of a class file starting at the first byte.
	 * @return extracted array of constants.
	 * @throws IOException
	 *             in case of reading errors or invalid class file.
	 */
	public static void readClassfile( ClassInputStream in, JarProcessor out )
			throws IOException {
		if ( in.int32bit() != MAGIC_NUMBER ) {
			throw new IOException( "Not a class file: Expected Magic number 0xcafebabe." );
		}
		in.uint16bit(); // minor version
		in.uint16bit(); // major version
		ConstantPool cp = ConstantPool.read( in );

		Modifiers access = Modifiers.modifiers( in.uint16bit() );
		Type type = Type.type( access, typeRef( cp.name( in.uint16bit() ) ) );
		if ( out.filter().process( type ) ) {
			TypeRef superclass = typeRef( cp.name( in.uint16bit() ) );
			int interfaceCount = in.uint16bit();
			TypeRef[] superinterfaces = readInterfaces( in, cp, interfaceCount );
			out.process( type, superclass, superinterfaces, cp );
			//int fieldCount = stream.readUnsignedShort();
		}
	}

	private static TypeRef[] readInterfaces( ClassInputStream stream, ConstantPool cp,
			int interfaceCount )
			throws IOException {
		TypeRef[] superinterfaces = new TypeRef[interfaceCount];
		for ( int i = 0; i < interfaceCount; i++ ) {
			superinterfaces[i] = typeRef( cp.name( stream.uint16bit() ) );
		}
		return superinterfaces;
	}
}
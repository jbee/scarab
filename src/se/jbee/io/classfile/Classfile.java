package se.jbee.io.classfile;

import static se.jbee.io.classfile.TypeRef.typeRef;

import java.io.IOException;

public class Classfile {

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
	public static void readClassfile( ClassInputStream in, ClassVisitor out )
			throws IOException {
		if ( in.int32bit() != MAGIC_NUMBER ) {
			throw new IOException( "Not a class file: Magic number missing." );
		}
		in.uint16bit(); // minor version
		in.uint16bit(); // major version
		ConstantPool cp = ConstantPool.read( in );

		Modifiers access = Modifiers.modifiers( in.uint16bit() );
		Type self = Type.type( access, typeRef( cp.name( in.uint16bit() ) ) );
		if ( out.visit( self ) ) {
			TypeRef superclass = typeRef( cp.name( in.uint16bit() ) );
			int interfaceCount = in.uint16bit();
			TypeRef[] superinterfaces = readInterfaces( in, cp, interfaceCount );
			out.visit( self, superclass, superinterfaces, cp );
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
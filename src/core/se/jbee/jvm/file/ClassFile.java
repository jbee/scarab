package se.jbee.jvm.file;

import static se.jbee.jvm.reflect.ClassDeclaration.classDeclaration;

import java.io.IOException;

import se.jbee.jvm.Archive;
import se.jbee.jvm.Class;
import se.jbee.jvm.Modifiers;
import se.jbee.jvm.reflect.ClassDeclaration;
import se.jbee.jvm.reflect.ClassProcessor;

public final class ClassFile {

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
	public static void readClassfile( Archive archive, ClassInputStream in, ClassProcessor out )
			throws IOException {
		if ( in.int32bit() != MAGIC_NUMBER ) {
			throw new IOException( "Not a class file: Expected Magic number 0xcafebabe." );
		}
		in.uint16bit(); // minor version
		in.uint16bit(); // major version
		ConstantPool cp = ConstantPool.read( in );

		Modifiers modifiers = Modifiers.classModifiers( in.uint16bit() );
		Class cls = Class.cls( modifiers, cp.utf0( in.uint16bit() ) );
		Class superclass = Class.unknownClass( cp.utf0( in.uint16bit() ) );
		Class[] interfaces = readInterfaces( in, cp, in.uint16bit() );
		DeclarationPool dp = DeclarationPool.read( in, cls, cp );
		ClassDeclaration self = classDeclaration( archive, cls, superclass, interfaces, dp, cp );
		out.process( self );
	}

	private static Class[] readInterfaces( ClassInputStream stream, ConstantPool cp,
			int interfaceCount )
			throws IOException {
		Class[] superinterfaces = new Class[interfaceCount];
		for ( int i = 0; i < interfaceCount; i++ ) {
			superinterfaces[i] = Class.unknownInterface( cp.utf0( stream.uint16bit() ) );
		}
		return superinterfaces;
	}

}
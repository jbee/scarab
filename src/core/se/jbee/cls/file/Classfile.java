package se.jbee.cls.file;

import static se.jbee.cls.ref.Type.type;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import se.jbee.cls.ref.Class;
import se.jbee.cls.ref.Modifiers;
import se.jbee.cls.ref.Type;
import se.jbee.cls.sca.JarProcessor;

public final class Classfile {

	private static final int MAGIC_NUMBER = 0xcafebabe;

	private static final Class[] PRIMITIVES = new Class[26];

	static {
		for ( TypeCode c : TypeCode.values() ) {
			PRIMITIVES[c.name().charAt( 0 ) - 'A'] = Class.cls( c.name, 0 );
		}
	}

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

		Modifiers modifiers = Modifiers.classModifiers( in.uint16bit() );
		Class cls = cls( cp.utf0( in.uint16bit() ) );
		Class superclass = cls( cp.utf0( in.uint16bit() ) );
		Class[] interfaces = readInterfaces( in, cp, in.uint16bit() );
		DeclarationPool dp = DeclarationPool.read( in, cls, cp );
		Type type = type( modifiers, cls, superclass, interfaces, dp, cp );
		out.process( type );
	}

	private static Class[] readInterfaces( ClassInputStream stream, ConstantPool cp,
			int interfaceCount )
			throws IOException {
		Class[] superinterfaces = new Class[interfaceCount];
		for ( int i = 0; i < interfaceCount; i++ ) {
			superinterfaces[i] = cls( cp.utf0( stream.uint16bit() ) );
		}
		return superinterfaces;
	}

	public static Class cls( String name ) {
		if ( name == null || name.isEmpty() ) {
			return Class.NONE;
		}
		if ( !Character.isLowerCase( name.charAt( 0 ) ) && name.endsWith( ";" ) ) {
			return classes( name )[0];
		}
		return Class.cls( name );
	}

	public static Class[] classes( String descriptor ) {
		int index = 0;
		List<Class> names = new ArrayList<Class>();
		char[] dc = descriptor.toCharArray();
		int arrayDimentions = 0;
		while ( index < dc.length ) {
			final char c = dc[index++];
			if ( c == '[' ) {
				arrayDimentions++;
			} else {
				Class ref = null;
				if ( c == 'L' ) {
					int end = descriptor.indexOf( ';', index );
					String name = descriptor.substring( index, end );
					ref = Class.cls( name, arrayDimentions );
					index = end + 1;
				} else {
					ref = PRIMITIVES[c - 'A'];
				}
				names.add( ref );
				arrayDimentions = 0;
			}
		}
		return names.toArray( new Class[names.size()] );
	}

	private static enum TypeCode {
		B( byte.class ),
		C( char.class ),
		D( double.class ),
		F( float.class ),
		I( int.class ),
		J( long.class ),
		S( short.class ),
		Z( boolean.class ),
		V( void.class ),
		L( Object.class );

		final String name;

		private TypeCode( java.lang.Class<?> type ) {
			this.name = type.getCanonicalName().replace( '.', '/' ).intern();
		}

	}
}
package se.jbee.cls.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import se.jbee.cls.ref.Class;
import se.jbee.cls.ref.Modifiers;
import se.jbee.cls.ref.Type;
import se.jbee.cls.sca.JarProcessor;

public final class Classfile {

	private static final int MAGIC_NUMBER = 0xcafebabe;

	private static final Type[] PRIMITIVES = new Type[26];

	static {
		for ( TypeCode c : TypeCode.values() ) {
			PRIMITIVES[c.name().charAt( 0 ) - 'A'] = Type.type( c.name, 0 );
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

		Modifiers access = Modifiers.modifiers( in.uint16bit() );
		Class type = Class.cls( access, type( cp.name0( in.uint16bit() ) ) );
		if ( out.filter().process( type ) ) {
			Type superclass = type( cp.name0( in.uint16bit() ) );
			int interfaceCount = in.uint16bit();
			Type[] superinterfaces = readInterfaces( in, cp, interfaceCount );
			out.process( type, superclass, superinterfaces, cp );
			//int fieldCount = stream.readUnsignedShort();
		}
	}

	private static Type[] readInterfaces( ClassInputStream stream, ConstantPool cp,
			int interfaceCount )
			throws IOException {
		Type[] superinterfaces = new Type[interfaceCount];
		for ( int i = 0; i < interfaceCount; i++ ) {
			superinterfaces[i] = type( cp.name0( stream.uint16bit() ) );
		}
		return superinterfaces;
	}

	public static Type type( String name ) {
		if ( name == null || name.isEmpty() ) {
			return Type.NONE;
		}
		if ( !Character.isLowerCase( name.charAt( 0 ) ) && name.endsWith( ";" ) ) {
			return types( name )[0];
		}
		return Type.type( name );
	}

	public static Type[] types( String descriptor ) {
		int index = 0;
		List<Type> names = new ArrayList<Type>();
		char[] dc = descriptor.toCharArray();
		int arrayDimentions = 0;
		while ( index < dc.length ) {
			final char c = dc[index++];
			if ( c == '[' ) {
				arrayDimentions++;
			} else {
				Type ref = null;
				if ( c == 'L' ) {
					int end = descriptor.indexOf( ';', index );
					String name = descriptor.substring( index, end );
					ref = Type.type( name, arrayDimentions );
					index = end + 1;
				} else {
					ref = PRIMITIVES[c - 'A'];
				}
				names.add( ref );
				arrayDimentions = 0;
			}
		}
		return names.toArray( new Type[names.size()] );
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
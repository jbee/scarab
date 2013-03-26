package se.jbee.cls.file;

import java.util.ArrayList;
import java.util.List;

import se.jbee.cls.Class;
import se.jbee.cls.Modifiers;

public final class ClassDeclaration {

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

	private static final Class[] PRIMITIVES = new Class[26];

	static {
		for ( TypeCode c : TypeCode.values() ) {
			//TODO change modifiers to what is correct for primitives
			PRIMITIVES[c.name().charAt( 0 ) - 'A'] = Class.cls( Modifiers.UNKNOWN_CLASS, c.name, 0 );
		}
	}

	public static ClassDeclaration classDeclaration( String declaration ) {
		return new ClassDeclaration( declaration );
	}

	private final String declaration;

	private ClassDeclaration( String declaration ) {
		super();
		this.declaration = declaration;
	}

	public Class cls() {
		return cls( Modifiers.UNKNOWN_CLASS );
	}

	public Class cls( Modifiers modifiers ) {
		if ( declaration == null || declaration.isEmpty() ) {
			return Class.NONE;
		}
		if ( !Character.isLowerCase( declaration.charAt( 0 ) ) && !declaration.endsWith( ";" ) ) {
			return classes()[0];
		}
		return Class.cls( modifiers, declaration );
	}

	public Class[] classes() {
		int index = 0;
		List<Class> names = new ArrayList<Class>();
		char[] dc = declaration.toCharArray();
		int arrayDimentions = 0;
		while ( index < dc.length ) {
			final char c = dc[index++];
			if ( c == '[' ) {
				arrayDimentions++;
			} else {
				Class ref = null;
				if ( c == 'L' ) {
					int end = declaration.indexOf( ';', index );
					String name = declaration.substring( index, end );
					ref = Class.cls( Modifiers.UNKNOWN_CLASS, name, arrayDimentions );
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
}

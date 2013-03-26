package se.jbee.cls.file;

import static se.jbee.cls.Modifiers.modifiers;

import java.util.ArrayList;
import java.util.List;

import se.jbee.cls.Class;
import se.jbee.cls.Modifier;
import se.jbee.cls.Modifier.ModifierMode;
import se.jbee.cls.Modifiers;

public final class ClassDescriptor {

	private static final Modifiers PRIMITIVES_MODIFIERS = modifiers( ModifierMode.CLASS,
			Modifier.PUBLIC, Modifier.ABSTRACT, Modifier.FINAL );

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
			PRIMITIVES[c.name().charAt( 0 ) - 'A'] = Class.cls( PRIMITIVES_MODIFIERS, c.name, 0 );
		}
	}

	public static ClassDescriptor classDescriptor( String descriptor ) {
		return new ClassDescriptor( descriptor );
	}

	private final String descriptor;

	private ClassDescriptor( String descriptor ) {
		super();
		this.descriptor = descriptor;
	}

	public Class cls() {
		return cls( Modifiers.UNKNOWN_CLASS );
	}

	public Class cls( Modifiers modifiers ) {
		if ( descriptor == null || descriptor.isEmpty() ) {
			return Class.NONE;
		}
		if ( !Character.isLowerCase( descriptor.charAt( 0 ) ) && !descriptor.endsWith( ";" ) ) {
			return classes()[0];
		}
		return Class.cls( modifiers, descriptor );
	}

	public Class[] classes() {
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

	@Override
	public String toString() {
		return descriptor;
	}
}

package se.jbee.jar;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * FieldDescriptor = FieldType
 * 
 * ComponentType   = FieldType
 * FieldType       = BaseType , ObjectType , ArrayType
 * BaseType        = B , C , D , F , I , J , S , Z
 * ObjectType      = LClassname;
 * ArrayType       = [ComponentType
 * 
 * MethodeDescriptor   = ( ParameterDescriptor* ) ReturnDescriptor
 * 
 * ParameterDescriptor = FieldDescriptor
 * ReturnDescriptor    = FieldDescriptor , VoidDescriptor
 * VoidDescriptor      = V
 * 
 * </pre>
 * 
 */
public final class TypeRef {

	private static final TypeRef[] PRIMITIVES = new TypeRef[26];

	static {
		for ( TypeCode c : TypeCode.values() ) {
			PRIMITIVES[c.name().charAt( 0 ) - 'A'] = new TypeRef( 0, c.name );
		}
	}

	public static final TypeRef NONE = new TypeRef( 0, "".intern() );

	public static TypeRef typeRef( String name ) {
		if ( name == null || name.isEmpty() ) {
			return NONE;
		}
		if ( !Character.isLowerCase( name.charAt( 0 ) ) && name.endsWith( ";" ) ) {
			return typeRefsFromDescriptor( name )[0];
		}
		return new TypeRef( 0, name.intern() );
	}

	public static TypeRef[] typeRefsFromDescriptor( String descriptor ) {
		int index = 0;
		List<TypeRef> names = new ArrayList<TypeRef>();
		char[] dc = descriptor.toCharArray();
		int arrayDimentions = 0;
		while ( index < dc.length ) {
			final char c = dc[index++];
			if ( c == '[' ) {
				arrayDimentions++;
			} else {
				TypeRef ref = null;
				if ( c == 'L' ) {
					int end = descriptor.indexOf( ';', index );
					String name = descriptor.substring( index, end );
					ref = new TypeRef( arrayDimentions, name );
					index = end + 1;
				} else {
					ref = PRIMITIVES[c - 'A'];
				}
				names.add( ref );
				arrayDimentions = 0;
			}
		}
		return names.toArray( new TypeRef[names.size()] );
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

		private TypeCode( Class<?> type ) {
			this.name = type.getCanonicalName().replace( '.', '/' ).intern();
		}

	}

	public final int arrayDimensions;
	private final String name;

	private TypeRef( int arrayDimentions, String name ) {
		super();
		this.arrayDimensions = arrayDimentions;
		this.name = name;
	}

	public String canonicalName() {
		return name.replace( '/', '.' );
	}

	public String simpleName() {
		return name.substring( name.lastIndexOf( '/' ) + 1 );
	}

	@Override
	public boolean equals( Object obj ) {
		return obj instanceof TypeRef && isEqual( (TypeRef) obj );
	}

	public boolean isEqual( TypeRef other ) {
		return name == other.name;
	}

	public boolean isNone() {
		return name == NONE.name;
	}

	public boolean isArray() {
		return arrayDimensions > 0;
	}

	public boolean isAnonymous() {
		return name.lastIndexOf( '$' ) > 0;
	}

	public TypeRef elementTypeRef() {
		return isArray()
			? new TypeRef( 0, name )
			: this;
	}

	@Override
	public String toString() {
		String res = name;
		for ( int i = 0; i < arrayDimensions; i++ ) {
			res += "[]";
		}
		return res;
	}
}

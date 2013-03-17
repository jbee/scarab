package se.jbee.cls.ref;

public final class Class {

	public static final Class OBJECT = cls( "java/lang/Object" );

	public static final Class NONE = new Class( 0, "" );

	public static Class cls( String name ) {
		return type( name, 0 );
	}

	public static Class type( String name, int arrayDimentions ) {
		return name == null || name.isEmpty()
			? Class.NONE
			: new Class( arrayDimentions, name );
	}

	public final int arrayDimensions;
	/**
	 * The type name as used within the JVM. Example:
	 * 
	 * <pre>
	 * java / lang / Object
	 * </pre>
	 */
	public final String name;

	private Class( int arrayDimentions, String name ) {
		super();
		this.arrayDimensions = arrayDimentions;
		this.name = name.intern();
	}

	public Package pkg() {
		int idx = name.lastIndexOf( '/' );
		return idx < 0
			? Package.DEFAULT
			: Package.pkg( name.substring( 0, idx ) );
	}

	public String canonicalName() {
		return name.replace( '/', '.' );
	}

	public String simpleName() {
		return name.substring( name.lastIndexOf( '/' ) + 1 );
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals( Object obj ) {
		return obj instanceof Class && equalTo( (Class) obj );
	}

	public boolean equalTo( Class other ) {
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

	public Class elementClass() {
		return isArray()
			? new Class( 0, name )
			: this;
	}

	@Override
	public String toString() {
		String res = canonicalName();
		for ( int i = 0; i < arrayDimensions; i++ ) {
			res += "[]";
		}
		return res;
	}
}

package se.jbee.cls;

public final class Class {

	//TODO change modifiers to what is correct for Object
	public static final Class OBJECT = cls( Modifiers.UNKNOWN_CLASS, "java/lang/Object" );

	public static final Class NONE = new Class( Modifiers.UNKNOWN_CLASS, 0, "" );

	public static Class unknownClass( String name ) {
		return cls( Modifiers.UNKNOWN_CLASS, name );
	}

	public static Class cls( Modifiers modifiers, String name ) {
		return cls( modifiers, name, 0 );
	}

	public static Class cls( Modifiers modifiers, String name, int arrayDimentions ) {
		return name == null || name.isEmpty()
			? Class.NONE
			: new Class( modifiers, arrayDimentions, name );
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

	public final Modifiers modifiers;

	private Class( Modifiers modifiers, int arrayDimentions, String name ) {
		super();
		this.modifiers = modifiers;
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
		int idx = name.lastIndexOf( '$' );
		if ( idx < 0 || idx + 2 == name.length() ) {
			idx = name.lastIndexOf( '/' );
		}
		return name.substring( idx + 1 );
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

	public boolean isObject() {
		return equalTo( Class.OBJECT );
	}

	public boolean isInner() {
		return name.indexOf( '$' ) > 0;
	}

	public Class outerClass() {
		return isInner()
			? cls( modifiers, name.substring( 0, name.indexOf( '$' ) ) )
			: this;
	}

	public Class elementClass() {
		return isArray()
			? new Class( modifiers, 0, name )
			: this;
	}

	public Class with( Modifiers modifiers ) {
		return new Class( modifiers, arrayDimensions, name );
	}

	@Override
	public String toString() {
		String res = canonicalName();
		for ( int i = 0; i < arrayDimensions; i++ ) {
			res += "[]";
		}
		return modifiers + " " + res;
	}
}

package se.jbee.cls.ref;

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
public final class Type {

	public static final Type OBJECT = type( "java/lang/Object" );

	public static final Type NONE = new Type( 0, "" );

	public static Type type( String name ) {
		return type( name, 0 );
	}

	public static Type type( String name, int arrayDimentions ) {
		return name == null || name.isEmpty()
			? Type.NONE
			: new Type( arrayDimentions, name );
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

	private Type( int arrayDimentions, String name ) {
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
		return obj instanceof Type && equalTo( (Type) obj );
	}

	public boolean equalTo( Type other ) {
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

	public Type elementTypeRef() {
		return isArray()
			? new Type( 0, name )
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

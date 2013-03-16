package se.jbee.jar;

public final class Type {

	public static Type type( Modifiers modifiers, TypeRef canonicalName ) {
		return new Type( canonicalName, modifiers );
	}

	public final TypeRef type;
	public final Modifiers modifiers;

	private Type( TypeRef type, Modifiers modifiers ) {
		super();
		this.type = type;
		this.modifiers = modifiers;
	}

	@Override
	public int hashCode() {
		return type.hashCode();
	}

	@Override
	public boolean equals( Object obj ) {
		return obj instanceof Type && equalTo( (Type) obj );
	}

	public boolean equalTo( Type other ) {
		return type.equalTo( other.type );
	}

	@Override
	public String toString() {
		return modifiers + " " + type;
	}
}

package se.jbee.jar;

public final class Type {

	public static Type type( Modifiers modifiers, TypeRef canonicalName ) {
		return new Type( canonicalName, modifiers );
	}

	public final TypeRef type;
	public final Modifiers modifiers;

	private Type( TypeRef canonicalName, Modifiers modifiers ) {
		super();
		this.type = canonicalName;
		this.modifiers = modifiers;
	}

	@Override
	public String toString() {
		return modifiers + " " + type;
	}
}

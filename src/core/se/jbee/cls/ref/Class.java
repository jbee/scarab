package se.jbee.cls.ref;


public final class Class {

	public static Class cls( Modifiers modifiers, Type type ) {
		return new Class( type, modifiers );
	}

	public final Type type;
	public final Modifiers modifiers;

	private Class( Type type, Modifiers modifiers ) {
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
		return obj instanceof Class && equalTo( (Class) obj );
	}

	public boolean equalTo( Class other ) {
		return type.equalTo( other.type );
	}

	@Override
	public String toString() {
		return modifiers + " " + type;
	}
}

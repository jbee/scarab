package se.jbee.cls.ref;

public final class Class {

	public static Class cls( Modifiers modifiers, Type type, Type superclass, Type[] interfaces ) {
		return new Class( modifiers, type, superclass, interfaces );
	}

	public final Modifiers modifiers;
	public final Type type;
	public final Type superclass;
	public final Type[] interfaces;

	private Class( Modifiers modifiers, Type type, Type superclass, Type[] interfaces ) {
		super();
		this.modifiers = modifiers;
		this.type = type;
		this.superclass = superclass;
		this.interfaces = interfaces;
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

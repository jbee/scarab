package se.jbee.cls.ref;

public final class Type {

	//OPEN if this is just used from within the CP the usages and own methods and fields could be added here

	public static Type type( Modifiers modifiers, Class type, Class superclass, Class[] interfaces ) {
		return new Type( modifiers, type, superclass, interfaces );
	}

	public final Modifiers modifiers;
	public final Class cls;
	public final Class superclass;
	public final Class[] interfaces;

	private Type( Modifiers modifiers, Class type, Class superclass, Class[] interfaces ) {
		super();
		this.modifiers = modifiers;
		this.cls = type;
		this.superclass = superclass;
		this.interfaces = interfaces;
	}

	@Override
	public int hashCode() {
		return cls.hashCode();
	}

	@Override
	public boolean equals( Object obj ) {
		return obj instanceof Type && equalTo( (Type) obj );
	}

	public boolean equalTo( Type other ) {
		return cls.equalTo( other.cls );
	}

	@Override
	public String toString() {
		return modifiers + " " + cls;
	}
}

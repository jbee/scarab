package se.jbee.cls.ref;

public final class ClassSignature {

	public static ClassSignature classSignature( Modifiers modifiers, Class type, Class superclass, Class[] interfaces ) {
		return new ClassSignature( modifiers, type, superclass, interfaces );
	}

	public final Modifiers modifiers;
	public final Class cls;
	public final Class superclass;
	public final Class[] interfaces;

	private ClassSignature( Modifiers modifiers, Class type, Class superclass, Class[] interfaces ) {
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
		return obj instanceof ClassSignature && equalTo( (ClassSignature) obj );
	}

	public boolean equalTo( ClassSignature other ) {
		return cls.equalTo( other.cls );
	}

	@Override
	public String toString() {
		return modifiers + " " + cls;
	}
}

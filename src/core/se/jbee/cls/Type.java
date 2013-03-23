package se.jbee.cls;

public final class Type {

	public static Type type( Archive archive, Modifiers modifiers, Class cls, Class superclass,
			Class[] interfaces, Declarations declarations, References references ) {
		return new Type( archive, modifiers, cls, superclass, interfaces, declarations, references );
	}

	public final Archive archive;
	public final Modifiers modifiers;
	public final Class cls;
	public final Class superclass;
	public final Class[] interfaces;
	public final Declarations declarations;
	public final References references;

	private Type( Archive archive, Modifiers modifiers, Class cls, Class superclass,
			Class[] interfaces, Declarations declarations, References references ) {
		super();
		this.archive = archive;
		this.modifiers = modifiers;
		this.cls = cls;
		this.superclass = superclass;
		this.interfaces = interfaces;
		this.declarations = declarations;
		this.references = references;
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

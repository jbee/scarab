package se.jbee.jvm;

public final class Field
		implements Member {

	public static Field field( Class declaringClass, Modifiers modifiers, Class type, String name ) {
		return new Field( declaringClass, modifiers, type, name );
	}

	public final Class declaringClass;
	public final Modifiers modifiers;
	public final Class type;
	public final String name;

	private Field( Class declaringClass, Modifiers modifiers, Class type, String name ) {
		super();
		this.declaringClass = declaringClass;
		this.modifiers = modifiers;
		this.type = type;
		this.name = name.intern();
	}

	@Override
	public Class declaringClass() {
		return declaringClass;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public Modifiers modifiers() {
		return modifiers;
	}

	@Override
	public int hashCode() {
		return declaringClass.hashCode() ^ name.hashCode();
	}

	@Override
	public boolean equals( Object obj ) {
		return obj instanceof Field && equalTo( (Field) obj );
	}

	public boolean equalTo( Field other ) {
		return declaringClass.equalTo( other.declaringClass ) && name.equals( other.name );
	}

	@Override
	public String toString() {
		return modifiers + declaringClass.canonicalName() + "#" + type + " " + name;
	}
}

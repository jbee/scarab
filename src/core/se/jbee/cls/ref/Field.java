package se.jbee.cls.ref;

public final class Field {

	public static Field field( Class declaringClass, Class type, String name ) {
		return new Field( declaringClass, type, name );
	}

	public final Class declaringClass;
	public final Class type;
	public final String name;

	private Field( Class declaringClass, Class type, String name ) {
		super();
		this.declaringClass = declaringClass;
		this.type = type;
		this.name = name.intern();
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
		return declaringClass + "#" + type + " " + name;
	}
}

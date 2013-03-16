package se.jbee.cls.ref;

public final class Field {

	public static Field field( Type declaringClass, Type type, String name ) {
		return new Field( declaringClass, type, name );
	}

	public final Type declaringClass;
	public final Type type;
	public final String name;

	private Field( Type declaringClass, Type type, String name ) {
		super();
		this.declaringClass = declaringClass;
		this.type = type;
		this.name = name.intern();
	}

	@Override
	public String toString() {
		return declaringClass + "#" + type + " " + name;
	}
}

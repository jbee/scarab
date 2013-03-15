package se.jbee.io.classfile;

public final class Modifiers {

	public static Modifiers modifiers(int flags) {
		return new Modifiers(flags);
	}

	private final int flags;

	private Modifiers(int flags) {
		super();
		this.flags = flags;
	}

	public boolean has(Modifier modifier) {
		return modifier.isSetIn(flags);
	}

	public boolean isInterface() {
		return has(Modifier.ACC_INTERFACE);
	}

	public boolean isEnum() {
		return has(Modifier.ACC_ENUM);
	}

	public boolean isAbstract() {
		return has(Modifier.ACC_ABSTRACT);
	}

	public boolean isAnnotation() {
		return has(Modifier.ACC_ANNOTATION);
	}

	public boolean isFinal() {
		return has(Modifier.ACC_FINAL);
	}

	public boolean isClass() {
		return !isInterface() && !isEnum() && !isAnnotation();
	}

	@Override
	public String toString() {
		String extending = isAbstract() ? isInterface() ? "" : "abstract " : isFinal() && !isEnum() ? "final " : "";
		String type = isInterface() ? "interface" : isEnum() ? "enum" : isAnnotation() ? "@interface" : "class";
		return extending+type;
	}
}

package se.jbee.cls.ref;

import se.jbee.cls.ref.Modifier.ModifierMode;

public final class Modifiers {

	/**
	 * A special null object having none of the usual JVM ACC flags but instead a own flag so it can
	 * be distinguished from the others.
	 */
	public static final Modifiers NONE = new Modifiers( ModifierMode.CLASS, 1 << 9 );

	public static Modifiers classModifiers( int accFlags ) {
		return modifiers( ModifierMode.CLASS, accFlags );
	}

	public static Modifiers fieldModifiers( int accFlags ) {
		return modifiers( ModifierMode.FIELD, accFlags );
	}

	public static Modifiers methodModifiers( int accFlags ) {
		return modifiers( ModifierMode.METHOD, accFlags );
	}

	private static Modifiers modifiers( ModifierMode mode, int accFlags ) {
		return new Modifiers( mode, accFlags );
	}

	public static Modifiers modifiers( ModifierMode mode, Modifier... modifiers ) {
		int flags = 0;
		for ( Modifier m : modifiers ) {
			flags |= m.accFlag;
		}
		return new Modifiers( mode, flags );
	}

	public final ModifierMode mode;
	private final int accFlags;

	private Modifiers( ModifierMode mode, int accFlags ) {
		super();
		this.mode = mode;
		this.accFlags = accFlags;
	}

	public boolean has( Modifier modifier ) {
		return modifier.accFlagIsContainedIn( accFlags );
	}

	public boolean isInterface() {
		return has( Modifier.INTERFACE );
	}

	public boolean isEnum() {
		return has( Modifier.ENUM );
	}

	public boolean isAbstract() {
		return has( Modifier.ABSTRACT );
	}

	public boolean isAnnotation() {
		return has( Modifier.ANNOTATION );
	}

	public boolean isFinal() {
		return has( Modifier.FINAL );
	}

	public boolean isClass() {
		return !isInterface() && !isEnum() && !isAnnotation() && !isNone();
	}

	public boolean isNone() {
		return accFlags == NONE.accFlags;
	}

	@Override
	public String toString() {
		String extending = isAbstract()
			? isInterface()
				? ""
				: "abstract "
			: isFinal() && !isEnum()
				? "final "
				: "";
		String type = isInterface()
			? isAnnotation()
				? "@interface"
				: "interface"
			: isEnum()
				? "enum"
				: "class";
		return extending + type;
	}

	public boolean all( Modifiers other ) {
		return ( accFlags & other.accFlags ) == other.accFlags;
	}
}

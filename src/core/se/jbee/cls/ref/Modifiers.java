package se.jbee.cls.ref;

public final class Modifiers {

	/**
	 * A special null object having none of the usual JVM ACC flags but instead a own flag so it can
	 * be distinguished from the others.
	 */
	public static final Modifiers NONE = new Modifiers( 1 << 9 );

	public static Modifiers modifiers( int accFlags ) {
		return new Modifiers( accFlags );
	}

	public static Modifiers modifiers( Modifier... modifiers ) {
		int flags = 0;
		for ( Modifier m : modifiers ) {
			flags |= m.accFlag;
		}
		return new Modifiers( flags );
	}

	private final int accFlags;

	private Modifiers( int accFlags ) {
		super();
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

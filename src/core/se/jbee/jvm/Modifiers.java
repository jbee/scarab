package se.jbee.jvm;

import se.jbee.jvm.Modifier.ModifierMode;

public final class Modifiers {

	public static final Modifiers UNKNOWN_CLASS = new Modifiers( ModifierMode.CLASS, 0, true );
	public static final Modifiers UNKNOWN_INTERFACE = new Modifiers( ModifierMode.CLASS,
			Modifier.INTERFACE.accFlag | Modifier.ABSTRACT.accFlag, true );
	public static final Modifiers UNKNOWN_FIELD = new Modifiers( ModifierMode.FIELD, 0, true );
	public static final Modifiers UNKNOWN_METHOD = new Modifiers( ModifierMode.METHOD, 0, true );

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
		return new Modifiers( mode, accFlags, false );
	}

	public static Modifiers modifiers( ModifierMode mode, Modifier... modifiers ) {
		int flags = 0;
		for ( Modifier m : modifiers ) {
			flags |= m.accFlag;
		}
		return new Modifiers( mode, flags, false );
	}

	public final ModifierMode mode;
	private final int accFlags;
	private final boolean derived;

	private Modifiers( ModifierMode mode, int accFlags, boolean derived ) {
		super();
		this.mode = mode;
		this.accFlags = accFlags;
		this.derived = derived;
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
		return !isInterface() && !isEnum() && !isAnnotation() && !isUnknown();
	}

	public boolean isStatic() {
		return has( Modifier.STATIC );
	}

	public boolean isUnknown() {
		return derived && accFlags == 0;
	}

	@Override
	public String toString() {
		if ( isUnknown() ) {
			return "?";
		}
		//TODO this is for class mode
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

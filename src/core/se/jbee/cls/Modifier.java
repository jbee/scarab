package se.jbee.cls;

import static se.jbee.cls.Modifier.ModifierMode.CLASS;
import static se.jbee.cls.Modifier.ModifierMode.FIELD;
import static se.jbee.cls.Modifier.ModifierMode.METHOD;

import java.util.EnumSet;

/**
 * Type level modifiers.
 * 
 * @author Jan Bernitt (jan@jbee.se)
 */
public enum Modifier {

	/**
	 * Class: A public class
	 */
	PUBLIC( 0x0001, CLASS, FIELD, METHOD ),

	PRIVATE( 0x0002, FIELD, METHOD ),

	PROTECTED( 0x0004, FIELD, METHOD ),

	STATIC( 0x0008, FIELD, METHOD ),

	/**
	 * Class: A final class
	 */
	FINAL( 0x0010, CLASS, FIELD, METHOD ),
	/**
	 * Class: Special handling of super-method calls
	 */
	SUPER( 0x0020, CLASS ),

	SYNCHRONIZED( 0x0020, METHOD ),

	VOLATILE( 0x0040, FIELD ),

	BRIDGE( 0x0040, METHOD ),

	TRANSIENT( 0x0080, FIELD ),

	VARARGS( 0x0080, METHOD ),

	NATIVE( 0x0100, METHOD ),

	/**
	 * Class: An interface class. Will always occur together with {@link #ABSTRACT}
	 * 
	 * Method: OBS! As a method flag this is not part of JVN spec. It is used to mark that a method
	 * is a interface method.
	 */
	INTERFACE( 0x0200, CLASS, METHOD ),
	/**
	 * Class: A abstract class or interface
	 */
	ABSTRACT( 0x0400, CLASS, METHOD ),

	STRICT( 0x0800, METHOD ),

	/**
	 * Class: Compiler generated class (should never occur in a class file)
	 */
	SYNTHETIC( 0x1000, CLASS, FIELD, METHOD ),
	/**
	 * Class: An annotation class. Will always occur with {@link #INTERFACE}
	 */
	ANNOTATION( 0x2000, CLASS ),
	/**
	 * Class: An enum class.
	 */
	ENUM( 0x4000, CLASS, FIELD );

	//TODO better: unknown as modifier flag

	public final int accFlag;
	public final String description;
	public final EnumSet<ModifierMode> modes;

	private Modifier( int accFlag, ModifierMode... modes ) {
		this.accFlag = accFlag;
		this.description = name().toLowerCase().intern();
		this.modes = EnumSet.of( modes[0], modes );
	}

	public boolean accFlagIsContainedIn( int accFlags ) {
		return ( accFlags & accFlag ) == accFlag;
	}

	public static enum ModifierMode {
		CLASS,
		FIELD,
		METHOD,
	}
}

package se.jbee.cls.ref;

/**
 * Type level modifiers.
 * 
 * @author Jan Bernitt (jan@jbee.se)
 */
public enum Modifier {

	PUBLIC( 0x0001 ),
	FINAL( 0x0010 ),
	SUPER( 0x0020 ),
	/**
	 * Will always occur together with {@link #ABSTRACT}
	 */
	INTERFACE( 0x0200 ),
	/**
	 * A abstract class or interface
	 */
	ABSTRACT( 0x0400 ),
	/**
	 * Compiler generated class (should never occur in a class file)
	 */
	SYNTHETIC( 0x1000 ),
	/**
	 * Will always occur with {@link #INTERFACE}
	 */
	ANNOTATION( 0x2000 ),
	ENUM( 0x4000 );

	public final int accFlag;

	private Modifier( int accFlag ) {
		this.accFlag = accFlag;

	}

	public boolean accFlagIsContainedIn( int accFlags ) {
		return ( accFlags & accFlag ) == accFlag;
	}
}

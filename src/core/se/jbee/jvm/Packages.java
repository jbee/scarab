package se.jbee.jvm;

import java.util.Arrays;

/**
 * A immutable set of {@link Package}s.
 * 
 * @author Jan Bernitt (jan@jbee.se)
 */
public final class Packages {

	public static Packages packages( Package... packages ) {
		return new Packages( packages );
	}

	private final Package[] packages;

	private Packages( Package[] packages ) {
		super();
		this.packages = packages;
	}

	public boolean isSubpackage( Package pkg ) {
		for ( int i = 0; i < packages.length; i++ ) {
			if ( packages[i].isSubpackage( pkg ) ) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return Arrays.toString( packages );
	}

}

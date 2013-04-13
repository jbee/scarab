package se.jbee.jvm;

import static org.junit.Assert.assertTrue;
import static se.jbee.jvm.Package.pkg;

import org.junit.Test;

import se.jbee.jvm.Package;
import se.jbee.jvm.Packages;

public class TestPackages {

	@Test
	public void thatSubpackagesAreCorrect() {
		Package root = pkg( "se/jbee/cls" );
		Packages packages = Packages.packages( root );
		assertTrue( packages.isSubpackage( root ) );
		assertTrue( packages.isSubpackage( pkg( "se/jbee/cls/io" ) ) );
	}
}

package se.jbee.cls;

import static org.junit.Assert.assertTrue;
import static se.jbee.cls.Package.pkg;

import org.junit.Test;

public class TestPackages {

	@Test
	public void thatSubpackagesAreCorrect() {
		Package root = pkg( "se/jbee/cls" );
		Packages packages = Packages.packages( root );
		assertTrue( packages.isSubpackage( root ) );
		assertTrue( packages.isSubpackage( pkg( "se/jbee/cls/io" ) ) );
	}
}

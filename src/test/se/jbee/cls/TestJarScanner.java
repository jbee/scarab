package se.jbee.cls;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

import se.jbee.cls.ref.Package;
import se.jbee.cls.sca.JarProcessor;
import se.jbee.cls.sca.JarScanner;
import se.jbee.cls.sca.TypeFilter;
import se.jbee.cls.sca.graph.ClassGraph;
import se.jbee.cls.sca.graph.PackageNode;

public class TestJarScanner {

	@Test
	public void testScanGraph() {
		ClassGraph g = new ClassGraph();
		scanTestJar( TypeFilter.ALL, g );
		Package root = Package.pkg( "se/jbee/inject" );
		PackageNode jbee = g.pkg( root.parent() );
		assertEquals( 1, jbee.subPackages.size() );
		assertTrue( jbee.subPackages.contains( root ) );
		PackageNode inject = jbee.subPackages.node( root );
		assertEquals( 5, inject.subPackages.size() );
		Package bind = Package.pkg( "se/jbee/inject/bind" );
		assertFalse( inject.references.contains( bind ) );
		assertTrue( g.pkg( bind ).references.contains( root ) );
	}

	private void scanTestJar( TypeFilter filter, JarProcessor out ) {
		String file = "/home/jan/project/silk/dist/silk-di-0.4.3.jar";
		try {
			new JarScanner( out, filter ).scan( file );
		} catch ( IOException e ) {
			e.printStackTrace();
			fail( "Exception occured: " + e.getMessage() );
		}
	}
}

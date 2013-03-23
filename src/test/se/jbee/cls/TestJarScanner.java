package se.jbee.cls;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import se.jbee.cls.graph.ClassGraph;
import se.jbee.cls.graph.ClassNode;
import se.jbee.cls.graph.MethodNode;
import se.jbee.cls.graph.PackageNode;
import se.jbee.cls.sca.JarProcessor;
import se.jbee.cls.sca.JarScanner;
import se.jbee.cls.sca.TypeFilter;

public class TestJarScanner {

	@Test
	public void testScanGraph() {
		ClassGraph g = new ClassGraph();
		scanJar( "/home/jan/project/silk/dist/silk-di-0.4.3.jar", TypeFilter.ALL, g );
		Package root = Package.pkg( "se/jbee/inject" );
		PackageNode jbee = g.pkg( root.parent() );
		assertEquals( 1, jbee.subPackages.size() );
		assertTrue( jbee.contains( root ) );
		PackageNode inject = jbee.subPackages.node( root );
		assertEquals( 5, inject.subPackages.size() );
		Package bind = Package.pkg( "se/jbee/inject/bind" );
		assertFalse( inject.references( bind ) );
		assertTrue( g.pkg( bind ).references( root ) );
		assertTrue( inject.contains( root.classWithSimpleName( "Type" ) ) );
		assertFalse( inject.contains( root.classWithSimpleName( "Foo" ) ) );
		ClassNode type = inject.cls( root.classWithSimpleName( "Type" ) );
		MethodNode fieldType = type.method( "fieldType" );
		assertTrue( fieldType.id().modifiers.isStatic() );
		assertEquals( 1, fieldType.parameterTypes.size() );
		assertEquals( "Field", fieldType.parameter( 0 ).id().simpleName() );
		MethodNode isAssignableTo = type.method( "isAssignableTo" );
		assertTrue( isAssignableTo.isOverridden() );
		ClassNode binder = g.pkg( bind ).cls( bind.classWithSimpleName( "Binder" ) );
		assertTrue( binder.subclasses.size() > 0 );
	}

	@Test
	@Ignore
	public void testScanLargerGraph() {
		ClassGraph g = new ClassGraph();
		scanJar( "/home/jan/spring-2.5.6.jar", TypeFilter.ALL, g );
		assertNotNull( g );
	}

	private void scanJar( String file, TypeFilter filter, JarProcessor out ) {
		try {
			new JarScanner( out, filter ).scan( file );
		} catch ( IOException e ) {
			e.printStackTrace();
			fail( "Exception occured: " + e.getMessage() );
		}
	}
}

package se.jbee.cls;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static se.jbee.cls.Archive.archive;
import static se.jbee.cls.Packages.packages;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

import se.jbee.cls.graph.ArchiveNode;
import se.jbee.cls.graph.ClassGraph;
import se.jbee.cls.graph.ClassNode;
import se.jbee.cls.graph.MethodNode;
import se.jbee.cls.graph.PackageNode;
import se.jbee.cls.sca.ClassProcessor;
import se.jbee.cls.sca.JarScanner;
import se.jbee.cls.sca.TypeFilter;

public class TestJarScanner {

	@Test
	public void testScanGraph() {
		Package root = Package.pkg( "se/jbee/inject" );
		ClassGraph g = new ClassGraph( packages( root ) );
		String file = "/home/jan/project/silk/dist/silk-di-0.4.3.jar";
		scanJar( file, TypeFilter.ALL, g );
		PackageNode jbee = g.pkg( root.parent() );
		assertEquals( 1, jbee.subPackages.size() );
		assertTrue( jbee.contains( root ) );
		PackageNode inject = jbee.subPackages.node( root );
		assertEquals( 5, inject.subPackages.size() );
		Package bind = Package.pkg( "se/jbee/inject/bind" );
		assertFalse( inject.references( bind ) );
		assertTrue( g.pkg( bind ).references( root ) );
		assertTrue( inject.contains( root.memberClass( "Type" ) ) );
		assertFalse( inject.contains( root.memberClass( "Foo" ) ) );
		ClassNode type = inject.cls( root.memberClass( "Type" ) );
		MethodNode fieldType = type.method( "fieldType" );
		assertTrue( fieldType.id().modifiers.isStatic() );
		assertEquals( 1, fieldType.parameterTypes.size() );
		assertEquals( "Field", fieldType.parameter( 0 ).id().simpleName() );
		MethodNode isAssignableTo = type.method( "isAssignableTo" );
		assertTrue( isAssignableTo.isOverridden() );
		ClassNode binder = g.pkg( bind ).cls( bind.memberClass( "Binder" ) );
		assertTrue( binder.subclasses.size() > 0 );
		assertEquals( 2, g.archives.size() );
		assertEquals( archive( file ), type.archive().id() );
		ArchiveNode silk = g.archive( archive( file ) );
		assertTrue( silk.classes.contains( Class.unknownClass( "se/jbee/inject/Packages" ) ) );
		assertTrue( silk.packages.contains( Package.pkg( "se/jbee/inject/util" ) ) );
	}

	@Test
	@Ignore
	public void testScanLargerGraph() {
		Package root = Package.pkg( "org/springframework" );
		ClassGraph g = new ClassGraph( packages( root ) );
		scanJar( "/home/jan/spring-2.5.6.jar", TypeFilter.ALL, g );
		assertNotNull( g );
	}

	private void scanJar( String file, TypeFilter filter, ClassProcessor out ) {
		try {
			new JarScanner( out, filter ).scan( file );
		} catch ( IOException e ) {
			e.printStackTrace();
			fail( "Exception occured: " + e.getMessage() );
		}
	}
}

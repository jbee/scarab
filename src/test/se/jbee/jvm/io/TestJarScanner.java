package se.jbee.jvm.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static se.jbee.jvm.Archive.archive;
import static se.jbee.jvm.Class.cls;
import static se.jbee.jvm.Packages.packages;

import java.io.IOException;

import org.junit.Test;
import org.junit.runners.Suite.SuiteClasses;

import se.jbee.jvm.Class;
import se.jbee.jvm.Package;
import se.jbee.jvm.graph.AnnotationNode;
import se.jbee.jvm.graph.ArchiveNode;
import se.jbee.jvm.graph.ClassGraph;
import se.jbee.jvm.graph.ClassNode;
import se.jbee.jvm.graph.MethodNode;
import se.jbee.jvm.graph.PackageNode;
import se.jbee.jvm.reflect.ClassProcessor;

public class TestJarScanner {

	@Test
	public void testScanGraph() {
		String file = "/home/jan/project/silk/dist/silk-di-0.4.3.jar";
		Package root = Package.pkg( "se/jbee/inject" );
		ClassGraph g = new ClassGraph( packages( root ) );
		scanJar( file, ArchiveFilter.ALL, g );
		PackageNode jbee = g.pkg( root.parent() );
		assertEquals( 1, jbee.subPackages.count() );
		assertTrue( jbee.contains( root ) );
		PackageNode inject = jbee.subPackages.node( root );
		assertEquals( 5, inject.subPackages.count() );
		Package bind = Package.pkg( "se/jbee/inject/bind" );
		assertFalse( inject.references( bind ) );
		assertTrue( g.pkg( bind ).references( root ) );
		assertTrue( inject.contains( root.memberClass( "Type" ) ) );
		assertFalse( inject.contains( root.memberClass( "Foo" ) ) );
		ClassNode type = inject.cls( root.memberClass( "Type" ) );
		MethodNode fieldType = type.method( "fieldType" );
		assertTrue( fieldType.id().modifiers.isStatic() );
		assertEquals( 1, fieldType.parameterTypes.count() );
		assertEquals( "Field", fieldType.parameter( 0 ).type.id().simpleName() );
		MethodNode isAssignableTo = type.method( "isAssignableTo" );
		assertTrue( isAssignableTo.isOverridden() );
		ClassNode binder = g.pkg( bind ).cls( bind.memberClass( "Binder" ) );
		assertTrue( binder.subclasses.count() > 0 );
		assertEquals( 2, g.archives.count() );
		assertEquals( archive( file ), type.archive().id() );
		ArchiveNode silk = g.archive( archive( file ) );
		assertTrue( silk.classes.contains( Class.unknownClass( "se/jbee/inject/Packages" ) ) );
		assertTrue( silk.packages.contains( Package.pkg( "se/jbee/inject/util" ) ) );
		assertTrue( inject.dependencies.contains( Package.pkg( "java/lang" ) ) );
		assertFalse( inject.dependencies.contains( bind ) );
		MethodNode isParameterized = type.method( "isParameterized" );
		assertTrue( isAssignableTo.calls.contains( isParameterized.id() ) );
	}

	@Test
	public void testScanGraph2() {
		String file = "/home/jan/Desktop/silk-all.jar";
		Package root = Package.pkg( "se/jbee/inject" );
		ClassGraph g = new ClassGraph( packages( root ) );
		scanJar( file, ArchiveFilter.ALL, g );
		assertTrue( g.annotations.count() > 0 );
		ClassNode suiteClasses = g.cls( cls( SuiteClasses.class ) );
		assertNotNull( suiteClasses );
		AnnotationNode suiteClassesA = g.annotation( cls( SuiteClasses.class ) );
		assertNotNull( suiteClassesA );
		AnnotationNode tests = g.annotation( cls( Test.class ) );
		assertNotNull( tests );
		assertEquals( 193, tests.annotatedMethods.count() );
	}

	@Test
	public void testScanLargerGraph() {
		Package root = Package.pkg( "org/springframework" );
		ClassGraph g = new ClassGraph( packages( root ) );
		scanJar( "/home/jan/spring-2.5.6.jar", ArchiveFilter.ALL, g );
		assertNotNull( g );
	}

	private void scanJar( String file, ArchiveFilter filter, ClassProcessor out ) {
		try {
			new JarScanner( out, filter ).scan( file );
		} catch ( IOException e ) {
			e.printStackTrace();
			fail( "Exception occured: " + e.getMessage() );
		}
	}
}

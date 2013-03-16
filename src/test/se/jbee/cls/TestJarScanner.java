package se.jbee.cls;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;

import se.jbee.cls.ref.Class;
import se.jbee.cls.ref.Modifier;
import se.jbee.cls.ref.Modifiers;
import se.jbee.cls.ref.Package;
import se.jbee.cls.ref.Type;
import se.jbee.cls.sca.JarProcessor;
import se.jbee.cls.sca.JarScanner;
import se.jbee.cls.sca.TypeFilter;
import se.jbee.cls.sca.TypeFilters;
import se.jbee.cls.sca.analyse.FieldAccessCountProcessor;
import se.jbee.cls.sca.analyse.TypeGraphProcessor;

public class TestJarScanner {

	@Test ( timeout = 1000 )
	public void testScan() {
		TypeFilter filter = TypeFilters.modifiers( Modifiers.modifiers( Modifier.ABSTRACT ) );
		JarProcessor out = new FieldAccessCountProcessor( filter, System.out );
		scanTestJar( out );
	}

	@Test
	public void testScanTypeGraph() {
		Map<Class, Set<Type>> typeUsages = new HashMap<Class, Set<Type>>();
		JarProcessor out = new TypeGraphProcessor( typeUsages );
		scanTestJar( out );
		Map<Package, Set<Package>> packageDeps = new HashMap<Package, Set<Package>>();
		for ( Entry<Class, Set<Type>> e : typeUsages.entrySet() ) {
			Package pkg = e.getKey().type.pkg();
			Set<Package> deps = packageDeps.get( pkg );
			if ( deps == null ) {
				deps = new HashSet<Package>();
				packageDeps.put( pkg, deps );
			}
			for ( Type t : e.getValue() ) {
				deps.add( t.pkg() );
			}
		}
		PrintStream p = System.out;
		for ( Entry<Package, Set<Package>> e : packageDeps.entrySet() ) {
			p.println( e.getKey() );
			for ( Package d : e.getValue() ) {
				p.print( '\t' );
				p.println( d );
			}
		}
	}

	private void scanTestJar( JarProcessor out ) {
		try {
			new JarScanner( out ).scan( "/home/jan/spring-2.5.6.jar" );
		} catch ( IOException e ) {
			e.printStackTrace();
			fail( "Exception occured: " + e.getMessage() );
		}
	}
}

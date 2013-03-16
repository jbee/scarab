package se.jbee.jar;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;

import se.jbee.jar.analyse.FieldAccessCountProcessor;
import se.jbee.jar.analyse.TypeGraphProcessor;

public class TestJarScanner {

	@Test ( timeout = 1000 )
	public void testScan() {
		TypeFilter filter = TypeFilters.modifiers( Modifiers.modifiers( Modifier.ACC_ABSTRACT ) );
		JarProcessor out = new FieldAccessCountProcessor( filter, System.out );
		scanTestJar( out );
	}

	@Test
	public void testScanTypeGraph() {
		Map<Type, Set<TypeRef>> typeUsages = new HashMap<Type, Set<TypeRef>>();
		JarProcessor out = new TypeGraphProcessor( typeUsages );
		scanTestJar( out );
		Map<Package, Set<Package>> packageDeps = new HashMap<Package, Set<Package>>();
		for ( Entry<Type, Set<TypeRef>> e : typeUsages.entrySet() ) {
			Package pkg = e.getKey().type.pkg();
			Set<Package> deps = packageDeps.get( pkg );
			if ( deps == null ) {
				deps = new HashSet<Package>();
				packageDeps.put( pkg, deps );
			}
			for ( TypeRef t : e.getValue() ) {
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

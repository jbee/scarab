package se.jbee.jar;

import java.io.IOException;

import org.junit.Test;

import se.jbee.jar.analyse.FieldAccessCountProcessor;

public class TestJarScanner {

	@Test ( timeout = 1000 )
	public void testScan()
			throws IOException {
		TypeFilter filter = TypeFilters.modifiers( Modifiers.modifiers( Modifier.ACC_ABSTRACT ) );
		JarProcessor out = new FieldAccessCountProcessor( filter, System.out );
		new JarScanner( out ).scan( "/home/jan/spring-2.5.6.jar" );
	}
}

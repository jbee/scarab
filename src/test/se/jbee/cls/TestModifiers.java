package se.jbee.cls;

import org.junit.Test;

public class TestModifiers {

	@Test
	public void testSame() {
		System.out.println( java.lang.reflect.Modifier.toString( Integer.class.getModifiers() ) );
	}
}

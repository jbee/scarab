package se.jbee.cls;

public class Append {

	//TODO change to stream based 
	public static void commaSeparated( StringBuilder b, Object... values ) {
		if ( values.length > 0 ) {
			for ( int i = 0; i < values.length; i++ ) {
				b.append( values[i] ).append( ", " );
			}
			b.setLength( b.length() - 2 );
		}
	}

}

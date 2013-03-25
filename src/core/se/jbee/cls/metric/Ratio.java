package se.jbee.cls.metric;

public final class Ratio
		implements Comparable<Ratio> {

	public static enum RatioType {
		RATIO,
		COUNT,
		NATURE
	}

	public static Ratio count( int numerator ) {
		return new Ratio( numerator, 1 );
	}

	public static Ratio ratio( int numerator, int denominator ) {
		return new Ratio( numerator, denominator );
	}

	public final int numerator;
	public final int denominator;

	private Ratio( int numerator, int denominator ) {
		super();
		this.numerator = numerator;
		this.denominator = denominator;
	}

	public double doubleValue() {
		return numerator / denominator;
	}

	@Override
	public String toString() {
		return String.format( "%.2f", doubleValue() );
	}

	@Override
	public int hashCode() {
		return numerator ^ denominator;
	}

	@Override
	public boolean equals( Object obj ) {
		return obj instanceof Ratio && equalTo( (Ratio) obj );
	}

	public boolean equalTo( Ratio other ) {
		return numerator == other.numerator && denominator == other.denominator;
	}

	@Override
	public int compareTo( Ratio other ) {
		return Double.compare( doubleValue(), other.doubleValue() );
	}
}

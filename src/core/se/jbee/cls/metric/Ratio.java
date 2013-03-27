package se.jbee.cls.metric;

public final class Ratio
		implements Comparable<Ratio> {

	public static enum RatioType {
		/**
		 * Part of total, a usual ratio
		 */
		RATIO,
		/**
		 * A absolute count (denominator will be 1)
		 */
		COUNT,
		/**
		 * How much something has a certain characteristic. A relative ratio where the relation
		 * might differ from measurement to measurement since it is also measured.
		 */
		NATURE,
		/**
		 * Actual count in relation to a absolute fix-point marking the 'normal' or 100%.
		 */
		BENCHMARK
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

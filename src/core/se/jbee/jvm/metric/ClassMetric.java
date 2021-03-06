package se.jbee.jvm.metric;

public final class ClassMetric {

	public static ClassMetric metric( String name, ClassMeasure measure, Rating rating ) {
		return new ClassMetric( name, measure, rating );
	}

	public final String name;
	public final ClassMeasure measure;
	public final Rating rating;

	private ClassMetric( String name, ClassMeasure measure, Rating rating ) {
		super();
		this.name = name;
		this.measure = measure;
		this.rating = rating;
	}

}

package se.jbee.cls.metric;

public final class ClassMetric {

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

package se.jbee.jvm.metric;

import static se.jbee.jvm.metric.Ratio.count;
import static se.jbee.jvm.metric.Ratio.ratio;
import se.jbee.jvm.graph.ClassNode;

public final class Metrics {

	public static final ClassMeasure UTILISATION = new Utilisation();
	public static final ClassMeasure NUMBER_OF_INSTANCE_METHODS = new NumberOfInstanceMethods();
	public static final ClassMeasure NUMBER_OF_INSTANCE_FIELDS = new NumberOfInstanceFields();
	public static final ClassMeasure NUMBER_OF_INCOMING_REFERENCES = new NumberOfIncomingReferences();
	public static final ClassMeasure NUMBER_OF_OUTGOING_REFERENCES = new NumberOfOutgoingReferences();
	public static final ClassMeasure EQUIPARTITION_1 = new Equipartition( 1 );

	public static final Rating NUMERATOR = new NumeratorRating();

	private Metrics() {
		throw new UnsupportedOperationException( "util" );
	}

	private static final class NumeratorRating
			implements Rating {

		NumeratorRating() {
			// make visible
		}

		@Override
		public int rate( Ratio ratio ) {
			return ratio.numerator;
		}

	}

	private static final class Equipartition
			implements ClassMeasure {

		private final int numerator;

		Equipartition( int numerator ) {
			super();
			this.numerator = numerator;
		}

		@Override
		public Ratio measure( ClassNode cls ) {
			return count( numerator );
		}

	}

	private static final class Utilisation
			implements ClassMeasure {

		Utilisation() {
			//make visible
		}

		@Override
		public Ratio measure( ClassNode cls ) {
			return ratio( cls.staticMethods.count(), cls.methods.count() );
		}

	}

	private static final class NumberOfInstanceMethods
			implements ClassMeasure {

		NumberOfInstanceMethods() {
			//make visible
		}

		@Override
		public Ratio measure( ClassNode cls ) {
			return count( cls.instanceMethods.count() );
		}

	}

	private static final class NumberOfInstanceFields
			implements ClassMeasure {

		NumberOfInstanceFields() {
			//make visible
		}

		@Override
		public Ratio measure( ClassNode cls ) {
			return count( cls.instanceFields.count() );
		}

	}

	private static final class NumberOfIncomingReferences
			implements ClassMeasure {

		NumberOfIncomingReferences() {
			//make visible
		}

		@Override
		public Ratio measure( ClassNode cls ) {
			return count( cls.referencedBy.count() );
		}

	}

	private static final class NumberOfOutgoingReferences
			implements ClassMeasure {

		NumberOfOutgoingReferences() {
			//make visible
		}

		@Override
		public Ratio measure( ClassNode cls ) {
			return count( cls.references.count() );
		}

	}
}

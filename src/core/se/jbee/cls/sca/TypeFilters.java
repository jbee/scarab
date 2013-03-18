package se.jbee.cls.sca;

import se.jbee.cls.ref.Type;
import se.jbee.cls.ref.Modifiers;

public class TypeFilters {

	public static TypeFilter modifiers( Modifiers modifiers ) {
		return new ModifiersFilter( modifiers );
	}

	private static class ModifiersFilter
			implements TypeFilter {

		private final Modifiers mandatory;

		ModifiersFilter( Modifiers mandatory ) {
			super();
			this.mandatory = mandatory;
		}

		@Override
		public boolean process( Archive archive ) {
			return true;
		}

		@Override
		public boolean process( Type type ) {
			return type.modifiers.all( mandatory );
		}

	}
}

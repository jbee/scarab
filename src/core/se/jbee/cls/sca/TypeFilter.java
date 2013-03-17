package se.jbee.cls.sca;

import se.jbee.cls.ref.ClassSignature;

public interface TypeFilter {

	static final TypeFilter ALL = new TypeFilter() {

		@Override
		public boolean process( ClassSignature type ) {
			return true;
		}

		@Override
		public boolean process( Archive archive ) {
			return true;
		}
	};

	//TODO extract to a separate interface so both can be combined better 
	boolean process( Archive archive );

	boolean process( ClassSignature type );
}

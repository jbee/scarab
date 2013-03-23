package se.jbee.cls.sca;

import se.jbee.cls.Archive;
import se.jbee.cls.Type;

public interface TypeFilter {

	static final TypeFilter ALL = new TypeFilter() {

		@Override
		public boolean process( Type type ) {
			return true;
		}

		@Override
		public boolean process( Archive archive ) {
			return true;
		}
	};

	//TODO extract to a separate interface so both can be combined better 
	boolean process( Archive archive );

	boolean process( Type type ); // this might not be useful any longer, filter could be decorated
}

package se.jbee.cls.sca;

import se.jbee.cls.Archive;

public interface ArchiveFilter {

	static final ArchiveFilter ALL = new ArchiveFilter() {

		@Override
		public boolean process( Archive archive ) {
			return true;
		}
	};

	boolean process( Archive archive );

}

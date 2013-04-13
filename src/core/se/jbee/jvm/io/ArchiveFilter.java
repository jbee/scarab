package se.jbee.jvm.io;

import se.jbee.jvm.Archive;

public interface ArchiveFilter {

	static final ArchiveFilter ALL = new ArchiveFilter() {

		@Override
		public boolean process( Archive archive ) {
			return true;
		}
	};

	boolean process( Archive archive );

}

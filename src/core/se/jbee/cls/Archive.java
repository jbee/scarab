package se.jbee.cls;

public final class Archive {

	public static final Archive NONE = new Archive( "" );

	public static Archive archive( String file ) {
		return new Archive( file );
	}

	private final String file;

	private Archive( String file ) {
		super();
		this.file = file;
	}

	@Override
	public String toString() {
		return file;
	}

}

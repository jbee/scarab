package se.jbee.io.classfile;

public class Archive {

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

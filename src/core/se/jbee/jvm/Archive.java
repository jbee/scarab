package se.jbee.jvm;

import java.util.regex.Pattern;

public final class Archive {

	private static final Pattern ARCHIVES = Pattern.compile( "\\.(?:zip|jar|war|ear)$" );

	public static boolean isArchiveFile( String name ) {
		return ARCHIVES.matcher( name ).find();
	}

	public static final Archive NONE = new Archive( "" );
	public static final Archive RUNTIME = new Archive( "(runtime)" );

	public static Archive archive( String file ) {
		return new Archive( file );
	}

	private final String file;

	private Archive( String file ) {
		super();
		this.file = file.intern();
	}

	@Override
	public boolean equals( Object obj ) {
		return obj instanceof Archive && equalTo( (Archive) obj );
	}

	public boolean equalTo( Archive other ) {
		return file == other.file;
	}

	@Override
	public int hashCode() {
		return file.hashCode();
	}

	@Override
	public String toString() {
		return file;
	}

}

package se.jbee.io.classfile;

import java.io.IOException;
import java.util.Enumeration;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class Scanner {

	private static final Pattern ZIPS = Pattern.compile( "\\.(?:zip|jar|war|ear)$" );

	private final ClassProcessor out;

	private Scanner( ClassProcessor out ) {
		super();
		this.out = out;
	}

	public void scan( String file )
			throws IOException {
		if ( isZipFile( file ) ) {
			scan( new ZipFile( file ), ".*/ist-.*" );
		}
	}

	public void scan( ZipFile zip, String innerArchivesRegex )
			throws IOException {
		Enumeration<? extends ZipEntry> entries = zip.entries();
		while ( entries.hasMoreElements() ) {
			ZipEntry entry = entries.nextElement();
			if ( !entry.isDirectory() ) {
				String name = entry.getName();
				if ( isClassFile( name ) ) {
					Classfile.readClassfile( new ClassInputStream( zip.getInputStream( entry ) ),
							out );
				} else if ( isZipFile( name ) && name.matches( innerArchivesRegex ) ) {
					ZipInputStream in = new ZipInputStream( zip.getInputStream( entry ) );
					entry = in.getNextEntry();
					while ( entry != null ) {
						if ( isClassFile( entry.getName() ) ) {
							Classfile.readClassfile( new ClassInputStream( in ), out );
						}
						in.closeEntry();
						entry = in.getNextEntry();
					}
					in.close();
				}
			}
		}
	}

	static boolean isClassFile( String name ) {
		return name.endsWith( ".class" );
	}

	static boolean isZipFile( String name ) {
		return ZIPS.matcher( name ).find();
	}

	public static void main( String[] args )
			throws IOException {
		new Scanner( new PrintClassProcessor( System.out ) ).scan( args[1] );
	}
}

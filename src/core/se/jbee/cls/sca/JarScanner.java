package se.jbee.cls.sca;

import static se.jbee.cls.Archive.archive;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import se.jbee.cls.Archive;
import se.jbee.cls.ClassProcessor;
import se.jbee.cls.file.ClassInputStream;
import se.jbee.cls.file.Classfile;

public class JarScanner {

	private static final Pattern ARCHIVES = Pattern.compile( "\\.(?:zip|jar|war|ear)$" );

	private final ClassProcessor out;
	private final TypeFilter filter;

	public JarScanner( ClassProcessor out, TypeFilter filter ) {
		super();
		this.out = out;
		this.filter = filter;
	}

	public void scan( String file )
			throws IOException {
		if ( isArchiveFile( file ) ) {
			scan( new ZipFile( file ) );
		}
	}

	public void scan( ZipFile zip )
			throws IOException {
		Enumeration<? extends ZipEntry> entries = zip.entries();
		Archive archive = archive( zip.getName() );
		while ( entries.hasMoreElements() ) {
			ZipEntry entry = entries.nextElement();
			if ( !entry.isDirectory() ) {
				String name = entry.getName();
				if ( isClassFile( name ) ) {
					scan( archive, zip.getInputStream( entry ) );
				} else if ( isArchiveFile( name ) && filter.process( archive( name ) ) ) {
					ZipInputStream in = new ZipInputStream( zip.getInputStream( entry ) );
					entry = in.getNextEntry();
					while ( entry != null ) {
						if ( isClassFile( entry.getName() ) ) {
							scan( archive( name ), in );
						}
						in.closeEntry();
						entry = in.getNextEntry();
					}
					in.close();
				}
			}
		}
	}

	private void scan( Archive archive, InputStream classInputStream )
			throws IOException {
		Classfile.readClassfile( archive, new ClassInputStream( classInputStream ), out );
	}

	static boolean isClassFile( String name ) {
		return name.endsWith( ".class" );
	}

	static boolean isArchiveFile( String name ) {
		return ARCHIVES.matcher( name ).find();
	}

}

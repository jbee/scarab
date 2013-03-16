package se.jbee.jar;

import static se.jbee.jar.Archive.archive;

import java.io.IOException;
import java.util.Enumeration;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import se.jbee.jar.analyse.FieldAccessCountProcessor;
import se.jbee.jar.analyse.PrintTypeProcessor;
import se.jbee.jar.bytecode.ClassInputStream;
import se.jbee.jar.bytecode.Classfile;

public class JarScanner {

	private static final Pattern ARCHIVES = Pattern.compile( "\\.(?:zip|jar|war|ear)$" );

	private final JarProcessor out;

	private JarScanner( JarProcessor out ) {
		super();
		this.out = out;
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
		while ( entries.hasMoreElements() ) {
			ZipEntry entry = entries.nextElement();
			if ( !entry.isDirectory() ) {
				String name = entry.getName();
				if ( isClassFile( name ) ) {
					Classfile.readClassfile( new ClassInputStream( zip.getInputStream( entry ) ),
							out );
				} else if ( isArchiveFile( name ) && out.filter().process( archive( name ) ) ) {
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

	static boolean isArchiveFile( String name ) {
		return ARCHIVES.matcher( name ).find();
	}

	public static void main( String[] args )
			throws IOException {
		long start = System.currentTimeMillis();
		JarProcessor out = new TypedJarProcessor( new PrintTypeProcessor( System.out ) );
		TypeFilter filter = TypeFilters.modifiers( Modifiers.modifiers( Modifier.ACC_ABSTRACT ) );
		out = new FieldAccessCountProcessor( filter, System.out );
		new JarScanner( out ).scan( args[2] );
		System.out.println( ( System.currentTimeMillis() - start ) + "ms" );
	}
}

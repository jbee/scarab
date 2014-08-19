package se.jbee.jvm.io;

import static se.jbee.jvm.Archive.archive;
import static se.jbee.jvm.Packages.packages;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import se.jbee.jvm.Archive;
import se.jbee.jvm.Package;
import se.jbee.jvm.file.ClassFile;
import se.jbee.jvm.file.ClassInputStream;
import se.jbee.jvm.graph.ClassGraph;
import se.jbee.jvm.reflect.ClassProcessor;

public class JarScanner {

	public static ClassGraph scan(String[] args) {
		String archive = args[0];
		String rootPackage = args[1];
		final String archiveFilter = args[2];
		
		Package root = Package.pkg( rootPackage );
		ClassGraph g = new ClassGraph( packages( root ) );
		JarScanner s = new JarScanner(g, new ArchiveFilter() {
			
			@Override
			public boolean process(Archive archive) {
				return archive.filename().matches(archiveFilter);
			}
		});
		try {
			s.scan(archive);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return g;
	}
	
	private final ClassProcessor out;
	private final ArchiveFilter filter;

	public JarScanner( ClassProcessor out, ArchiveFilter filter ) {
		super();
		this.out = out;
		this.filter = filter;
	}

	public void scan( String file )
			throws IOException {
		if ( Archive.isArchiveFile( file ) ) {
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
				} else if ( Archive.isArchiveFile( name ) && filter.process( archive( name ) ) ) {
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
		ClassFile.readClassfile( archive, new ClassInputStream( classInputStream ), out );
	}

	static boolean isClassFile( String name ) {
		return name.endsWith( ".class" );
	}

}

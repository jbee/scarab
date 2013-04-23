package se.jbee.jdp;

import static se.jbee.jvm.Packages.packages;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import se.jbee.jvm.Package;
import se.jbee.jvm.graph.ClassGraph;
import se.jbee.jvm.graph.ClassNode;
import se.jbee.jvm.graph.PackageNode;
import se.jbee.jvm.io.ArchiveFilter;
import se.jbee.jvm.io.JarScanner;

/**
 * Used as an example to experiment with d3.js treemap to visualize package structure.
 * 
 * @author Jan Bernitt (jan@jbee.se)
 */
public class ClassTreeMap {

	private final PrintStream out;

	private ClassTreeMap( PrintStream out ) {
		super();
		this.out = out;
	}

	public void generate( PackageNode root ) {
		out.println( '{' );
		out.println( "\t\"name\": \"" + root.name() + "\"," );
		out.println( "\t\"children\": [" );
		boolean hasPackages = !root.subPackages.isEmpty();
		if ( hasPackages ) {
			int i = 0;
			for ( PackageNode s : root.subPackages ) {
				if ( i > 0 ) {
					out.println( "," );
				}
				generate( s );
				i++;
			}
		}
		if ( !root.classes.isEmpty() ) {
			if ( hasPackages ) {
				out.println( "," );
			}
			int i = 0;
			for ( ClassNode c : root.classes ) {
				if ( i > 0 ) {
					out.println( "," );
				}
				out.print( "\t\t{\"name\": \"" + c.id().simpleName() + "\"," );
				out.print( "\"type\": \""
						+ c.modifiers().toString().replace( "@interface", "annotation" ) + "\", " );
				out.print( "\"c_method\": " + c.methods.count() + ", " );
				out.print( "\"c_field\": " + c.instanceFields.count() + ", " );
				out.print( "\"c_references\": " + c.references.count() + ", " );
				out.print( "\"c_referencedBy\": " + c.referencedBy.count() );
				out.print( '}' );
				i++;
			}
		}
		out.println( ']' );
		out.println( '}' );
	}

	public static void main( String[] args )
			throws IOException {
		String file = "/home/jan/project/silk/dist/silk-di-0.4.3.jar";
		Package root = Package.pkg( "se/jbee/inject" );
		if ( false ) {
			file = "/home/jan/spring-2.5.6.jar";
			root = Package.pkg( "org/springframework" );
		}
		String json = "/home/jan/project/scarab/src/jdp/data.js";
		ClassGraph g = new ClassGraph( packages( root ) );
		new JarScanner( g, ArchiveFilter.ALL ).scan( file );
		FileOutputStream out2 = new FileOutputStream( json );
		try {
			out2.write( "var data = ".getBytes() );
			ClassTreeMap map = new ClassTreeMap( new PrintStream( out2 ) );
			map.generate( g.pkg( root ) );
			out2.write( ";".getBytes() );
		} finally {
			out2.close();
		}
	}
}

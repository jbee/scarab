package se.jbee.cls.vis;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import se.jbee.cls.Package;
import se.jbee.cls.graph.ClassGraph;
import se.jbee.cls.graph.ClassNode;
import se.jbee.cls.graph.PackageNode;
import se.jbee.cls.sca.JarScanner;
import se.jbee.cls.sca.TypeFilter;

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
				out.print( "\"c_method\": " + c.methods.size() + ", " );
				out.print( "\"c_field\": " + c.fields.size() + ", " );
				out.print( "\"c_references\": " + c.references.size() + ", " );
				out.print( "\"c_referencedBy\": " + c.referencedBy.size() );
				out.print( '}' );
				i++;
			}
		}
		out.println( ']' );
		out.println( '}' );
	}

	public static void main( String[] args )
			throws IOException {
		ClassGraph g = new ClassGraph();
		String file = "/home/jan/project/silk/dist/silk-di-0.4.3.jar";
		String json = "/home/jan/project/scarab/src/vis/data.json";
		new JarScanner( g, TypeFilter.ALL ).scan( file );
		FileOutputStream out2 = new FileOutputStream( json );
		try {
			ClassTreeMap map = new ClassTreeMap( new PrintStream( out2 ) );
			map.generate( g.pkg( Package.pkg( "se/jbee/inject" ) ) );
		} finally {
			out2.close();
		}
	}
}

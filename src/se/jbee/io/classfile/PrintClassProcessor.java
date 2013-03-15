package se.jbee.io.classfile;

import java.io.PrintStream;

public class PrintClassProcessor
		implements ClassProcessor {

	private final PrintStream out;

	private int nr = 0;

	public PrintClassProcessor( PrintStream out ) {
		super();
		this.out = out;
	}

	@Override
	public boolean process( Type type ) {
		return true;
	}

	@Override
	public void processClass( Type type, TypeRef extendsClass, TypeRef[] implementsInterfaces,
			References references ) {
		process( type, extendsClass, implementsInterfaces, references );
	}

	private void process( Type type, TypeRef extendsClass, TypeRef[] implementsInterfaces,
			References references ) {
		out.print( nr++ );
		out.print( "\t" );
		out.print( type );
		out.print( "\n" );
		if ( true ) {
			return;
		}
		if ( !extendsClass.isNone() ) {
			out.print( "\n\textends " );
			out.print( extendsClass );
		}
		if ( implementsInterfaces.length > 0 ) {
			out.print( "\n\timplements " );
			for ( TypeRef i : implementsInterfaces ) {
				out.print( i );
				out.print( ", " );
			}
		}
		out.print( "\n\tcalls methods:\n" );
		for ( MethodRef r : references.methods() ) {
			out.print( "\t\t" );
			out.print( r );
			out.print( "\n" );
		}
		out.print( "\n\tcalls interface-methods:\n" );
		for ( MethodRef r : references.interfaceMethods() ) {
			out.print( "\t\t" );
			out.print( r );
			out.print( "\n" );
		}
		out.print( "\n\taccesses fields:\n" );
		for ( FieldRef r : references.fields() ) {
			out.print( "\t\t" );
			out.print( r );
			out.print( "\n" );
		}
		out.print( "\n\treferences types:\n" );
		for ( TypeRef r : references.types() ) {
			out.print( "\t\t" );
			out.print( r );
			out.print( "\n" );
		}
	}

	@Override
	public void processEnum( Type type, TypeRef[] implementsInterfaces, References references ) {
		process( type, TypeRef.NONE, implementsInterfaces, references );
	}

	@Override
	public void processInterface( Type type, TypeRef[] extendsInterfaces, References references ) {
		process( type, TypeRef.NONE, extendsInterfaces, references );
	}

	@Override
	public void processAnnotation( Type type, References references ) {
		process( type, TypeRef.NONE, new TypeRef[0], references );
	}
}

package se.jbee.jar.analyse;

import java.io.PrintStream;

import se.jbee.jar.FieldRef;
import se.jbee.jar.MethodRef;
import se.jbee.jar.Type;
import se.jbee.jar.TypeProcessor;
import se.jbee.jar.TypeRef;
import se.jbee.jar.Usages;

public class PrintTypeProcessor
		implements TypeProcessor {

	private final PrintStream out;

	private int nr = 0;

	public PrintTypeProcessor( PrintStream out ) {
		super();
		this.out = out;
	}

	@Override
	public void processClass( Type type, TypeRef superclass, TypeRef[] interfaces, Usages usages ) {
		process( type, superclass, interfaces, usages );
	}

	private void process( Type type, TypeRef extendsClass, TypeRef[] implementsInterfaces,
			Usages references ) {
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
	public void processEnum( Type type, TypeRef[] interfaces, Usages usages ) {
		process( type, TypeRef.NONE, interfaces, usages );
	}

	@Override
	public void processInterface( Type type, TypeRef[] superinterfaces, Usages usages ) {
		process( type, TypeRef.NONE, superinterfaces, usages );
	}

	@Override
	public void processAnnotation( Type type, Usages usages ) {
		process( type, TypeRef.NONE, new TypeRef[0], usages );
	}
}

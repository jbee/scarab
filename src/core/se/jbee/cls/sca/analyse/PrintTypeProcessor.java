package se.jbee.cls.sca.analyse;

import java.io.PrintStream;

import se.jbee.cls.ref.Class;
import se.jbee.cls.ref.Field;
import se.jbee.cls.ref.Method;
import se.jbee.cls.ref.Type;
import se.jbee.cls.ref.Usages;
import se.jbee.cls.sca.TypeProcessor;

public class PrintTypeProcessor
		implements TypeProcessor {

	private final PrintStream out;

	private int nr = 0;

	public PrintTypeProcessor( PrintStream out ) {
		super();
		this.out = out;
	}

	@Override
	public void processClass( Class cls, Type superclass, Type[] interfaces, Usages usages ) {
		process( cls, superclass, interfaces, usages );
	}

	private void process( Class type, Type extendsClass, Type[] implementsInterfaces,
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
			for ( Type i : implementsInterfaces ) {
				out.print( i );
				out.print( ", " );
			}
		}
		out.print( "\n\tcalls methods:\n" );
		for ( Method r : references.methods() ) {
			out.print( "\t\t" );
			out.print( r );
			out.print( "\n" );
		}
		out.print( "\n\tcalls interface-methods:\n" );
		for ( Method r : references.interfaceMethods() ) {
			out.print( "\t\t" );
			out.print( r );
			out.print( "\n" );
		}
		out.print( "\n\taccesses fields:\n" );
		for ( Field r : references.fields() ) {
			out.print( "\t\t" );
			out.print( r );
			out.print( "\n" );
		}
		out.print( "\n\treferences types:\n" );
		for ( Type r : references.types() ) {
			out.print( "\t\t" );
			out.print( r );
			out.print( "\n" );
		}
	}

	@Override
	public void processEnum( Class cls, Type[] interfaces, Usages usages ) {
		process( cls, Type.NONE, interfaces, usages );
	}

	@Override
	public void processInterface( Class cls, Type[] superinterfaces, Usages usages ) {
		process( cls, Type.NONE, superinterfaces, usages );
	}

	@Override
	public void processAnnotation( Class cls, Usages usages ) {
		process( cls, Type.NONE, new Type[0], usages );
	}
}

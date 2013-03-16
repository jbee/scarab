package se.jbee.jar.analyse;

import java.io.PrintStream;

import se.jbee.jar.JarProcessor;
import se.jbee.jar.Type;
import se.jbee.jar.TypeFilter;
import se.jbee.jar.TypeRef;
import se.jbee.jar.Usages;

public class FieldAccessCountProcessor
		implements JarProcessor {

	private final TypeFilter filter;
	private final PrintStream out;

	public FieldAccessCountProcessor( TypeFilter filter, PrintStream out ) {
		super();
		this.filter = filter;
		this.out = out;
	}

	@Override
	public void process( Type type, TypeRef superclass, TypeRef[] interfaces, Usages usages ) {
		out.print( usages.fields().count() );
		out.print( '\t' );
		out.print( type );
		out.print( '\n' );
	}

	@Override
	public TypeFilter filter() {
		return filter;
	}

}

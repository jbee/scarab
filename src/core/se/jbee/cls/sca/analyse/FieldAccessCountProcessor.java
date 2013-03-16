package se.jbee.cls.sca.analyse;

import java.io.PrintStream;

import se.jbee.cls.ref.Class;
import se.jbee.cls.ref.Type;
import se.jbee.cls.ref.Usages;
import se.jbee.cls.sca.JarProcessor;
import se.jbee.cls.sca.TypeFilter;

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
	public void process( Class type, Type superclass, Type[] interfaces, Usages usages ) {
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

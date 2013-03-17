package se.jbee.cls.sca.analyse;

import java.io.PrintStream;

import se.jbee.cls.ref.ClassSignature;
import se.jbee.cls.ref.Usages;
import se.jbee.cls.sca.JarProcessor;

public class FieldAccessCountProcessor
		implements JarProcessor {

	private final PrintStream out;

	public FieldAccessCountProcessor( PrintStream out ) {
		super();
		this.out = out;
	}

	@Override
	public void process( ClassSignature signature, Usages usages ) {
		out.print( usages.fields().count() );
		out.print( '\t' );
		out.print( signature );
		out.print( '\n' );
	}

}

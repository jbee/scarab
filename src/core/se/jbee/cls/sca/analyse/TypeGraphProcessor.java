package se.jbee.cls.sca.analyse;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import se.jbee.cls.ref.Class;
import se.jbee.cls.ref.Type;
import se.jbee.cls.ref.Usages;
import se.jbee.cls.sca.JarProcessor;

public class TypeGraphProcessor
		implements JarProcessor {

	private final Map<Class, Set<Type>> typeUsages;

	public TypeGraphProcessor( Map<Class, Set<Type>> typeUsages ) {
		super();
		this.typeUsages = typeUsages;
	}

	@Override
	public void process( Class cls, Usages usages ) {
		Set<Type> set = new HashSet<Type>();
		set.add( cls.superclass );
		set.addAll( Arrays.asList( cls.interfaces ) );
		for ( Type t : usages.types() ) {
			set.add( t );
		}
		typeUsages.put( cls, set );
	}

}

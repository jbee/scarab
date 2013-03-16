package se.jbee.jar.analyse;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import se.jbee.jar.JarProcessor;
import se.jbee.jar.Type;
import se.jbee.jar.TypeFilter;
import se.jbee.jar.TypeRef;
import se.jbee.jar.Usages;

public class TypeGraphProcessor
		implements JarProcessor {

	private final Map<Type, Set<TypeRef>> typeUsages;

	public TypeGraphProcessor( Map<Type, Set<TypeRef>> typeUsages ) {
		super();
		this.typeUsages = typeUsages;
	}

	@Override
	public TypeFilter filter() {
		return TypeFilter.ALL;
	}

	@Override
	public void process( Type type, TypeRef superclass, TypeRef[] interfaces, Usages usages ) {
		Set<TypeRef> set = new HashSet<TypeRef>();
		set.add( superclass );
		set.addAll( Arrays.asList( interfaces ) );
		for ( TypeRef t : usages.types() ) {
			set.add( t );
		}
		typeUsages.put( type, set );
	}

}

package se.jbee.jar;

public interface JarProcessor {

	TypeFilter filter();

	void process( Type type, TypeRef superclass, TypeRef[] interfaces, Usages usages );
}

package se.jbee.jar;

public interface TypeProcessor {

	void processClass( Type type, TypeRef superclass, TypeRef[] interfaces, Usages usages );

	void processEnum( Type type, TypeRef[] interfaces, Usages usages );

	void processInterface( Type type, TypeRef[] superinterfaces, Usages usages );

	void processAnnotation( Type type, Usages usages );
}

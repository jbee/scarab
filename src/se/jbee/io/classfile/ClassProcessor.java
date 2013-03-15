package se.jbee.io.classfile;

public interface ClassProcessor {

	void processInterface( Type type, TypeRef[] extendsInterfaces, Usages references );

	void processClass( Type type, TypeRef extendsClass, TypeRef[] implementsInterfaces,
			Usages references );

	void processEnum( Type type, TypeRef[] implementsInterfaces, Usages references );

	void processAnnotation( Type type, Usages references );
}

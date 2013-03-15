package se.jbee.io.classfile;

public interface ClassVisitor {

	boolean visit( Archive archive );

	boolean visit( Type type );

	void visit( Type type, TypeRef superclass, TypeRef[] interfaces, Usages usages );
}

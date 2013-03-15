package se.jbee.io.classfile;

public class ClassProcessorInterpreter
		implements ClassVisitor {

	private final ClassProcessor out;

	public ClassProcessorInterpreter( ClassProcessor out ) {
		super();
		this.out = out;
	}

	@Override
	public boolean visit( Archive archive ) {
		return true;
	}

	@Override
	public boolean visit( Type type ) {
		return true;
	}

	@Override
	public void visit( Type type, TypeRef superclass, TypeRef[] interfaces,
			Usages usages ) {
		if ( type.modifiers.isInterface() ) {
			out.processInterface( type, interfaces, usages );
		} else if ( type.modifiers.isEnum() ) {
			out.processEnum( type, interfaces, usages );
		} else if ( type.modifiers.isAnnotation() ) {
			out.processAnnotation( type, usages );
		} else {
			out.processClass( type, superclass, interfaces, usages );
		}
	}

}

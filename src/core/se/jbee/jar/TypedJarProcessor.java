package se.jbee.jar;

public class TypedJarProcessor
		implements JarProcessor {

	private final TypeProcessor out;

	public TypedJarProcessor( TypeProcessor out ) {
		super();
		this.out = out;
	}

	@Override
	public TypeFilter filter() {
		return TypeFilter.ALL;
	}

	@Override
	public void process( Type type, TypeRef superclass, TypeRef[] interfaces, Usages usages ) {
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

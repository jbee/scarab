package se.jbee.io.classfile;

import static se.jbee.io.classfile.TypeRef.typeRef;

public final class FieldRef {

	public static FieldRef fieldRefFromConstants(String declaringClass, String name, String type) {
		return new FieldRef(typeRef(declaringClass), typeRef(type), name);
	}

	public final TypeRef declaringClass;
	public final TypeRef type;
	public final String name;

	private FieldRef(TypeRef declaringClass, TypeRef type, String name) {
		super();
		this.declaringClass = declaringClass;
		this.type = type;
		this.name = name;
	}

	@Override
	public String toString() {
		return declaringClass+"#"+type+" "+name;
	}
}

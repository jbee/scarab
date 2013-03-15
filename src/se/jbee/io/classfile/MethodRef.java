package se.jbee.io.classfile;

import static se.jbee.io.classfile.TypeRef.typeRef;
import static se.jbee.io.classfile.TypeRef.typeRefsFromDescriptor;



/**
 * How type looks
 * <pre>
 * 	(Ljava/lang/Object;)V
 *	()Z
 *	(Ljava/lang/String;)V
 *	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/io/PrintStream;
 *  </pre>
 *
 * @author jan
 */
public final class MethodRef {

	public static MethodRef methodRefFromConstants(String declaringClass, String name, String type) {
		int endOfParameters = type.indexOf(')');
		return new MethodRef(typeRef(declaringClass), typeRef(type.substring(endOfParameters+1)), name, typeRefsFromDescriptor(type.substring(1, endOfParameters)));
	}

	// interface method yes/no
	public final TypeRef declaringClass;
	public final TypeRef returnType;
	public final String name;
	public final TypeRef[] parameterTypes;

	public MethodRef(TypeRef declaringClass, TypeRef returnType, String name, TypeRef[] parameterTypes) {
		super();
		this.declaringClass = declaringClass;
		this.returnType = returnType;
		this.name = name;
		this.parameterTypes = parameterTypes;
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append(declaringClass+"#"+returnType+" "+name+"(");
		Object[] values = parameterTypes;
		Append.commaSeparated(b, values);
		b.append(')');
		return b.toString();
	}
}

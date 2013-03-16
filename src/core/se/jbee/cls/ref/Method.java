package se.jbee.cls.ref;

import se.jbee.cls.Append;

public final class Method {

	public static Method method( Type declaringClass, boolean inInterface, Type returnType,
			String name, Type... parameterTypes ) {
		return new Method( declaringClass, inInterface, returnType, name, parameterTypes );
	}

	public final Type declaringClass;
	public final boolean inInterface;
	public final Type returnType;
	public final String name;
	public final Type[] parameterTypes;

	private Method( Type declaringClass, boolean inInterface, Type returnType, String name,
			Type[] parameterTypes ) {
		super();
		this.declaringClass = declaringClass;
		this.returnType = returnType;
		this.name = name;
		this.parameterTypes = parameterTypes;
		this.inInterface = inInterface;
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append( declaringClass + "#" + returnType + " " + name + "(" );
		Object[] values = parameterTypes;
		Append.commaSeparated( b, values );
		b.append( ')' );
		return b.toString();
	}
}

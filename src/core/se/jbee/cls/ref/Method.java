package se.jbee.cls.ref;

import java.util.Arrays;

import se.jbee.cls.Append;

public final class Method {

	public static Method method( Class declaringClass, boolean inInterface, Class returnType,
			String name, Class... parameterTypes ) {
		return new Method( declaringClass, inInterface, returnType, name, parameterTypes );
	}

	public final Class declaringClass;
	public final boolean inInterface;
	public final Class returnType;
	public final String name;
	public final Class[] parameterTypes;

	private Method( Class declaringClass, boolean inInterface, Class returnType, String name,
			Class[] parameterTypes ) {
		super();
		this.declaringClass = declaringClass;
		this.returnType = returnType;
		this.name = name;
		this.parameterTypes = parameterTypes;
		this.inInterface = inInterface;
	}

	@Override
	public int hashCode() {
		return ( declaringClass.hashCode() ^ returnType.hashCode() ) + parameterTypes.length;
	}

	@Override
	public boolean equals( Object obj ) {
		return obj instanceof Method && equalTo( (Method) obj );
	}

	public boolean equalTo( Method other ) {
		return declaringClass.equalTo( other.declaringClass ) && name.equals( other.name )
				&& returnType.equalTo( other.returnType )
				&& Arrays.equals( parameterTypes, other.parameterTypes );
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
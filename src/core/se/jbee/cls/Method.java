package se.jbee.cls;

import java.util.Arrays;

public final class Method {

	public static Method method( Class declaringClass, Modifiers modifiers, Class returnType,
			String name, Class... parameterTypes ) {
		return new Method( declaringClass, modifiers, returnType, name, parameterTypes );
	}

	public final Modifiers modifiers;
	public final Class declaringClass;
	public final Class returnType;
	public final String name;
	public final Class[] parameterTypes;

	private Method( Class declaringClass, Modifiers modifiers, Class returnType, String name,
			Class[] parameterTypes ) {
		super();
		this.modifiers = modifiers;
		this.declaringClass = declaringClass;
		this.returnType = returnType;
		this.name = name;
		this.parameterTypes = parameterTypes;
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

	public Method declaredBy( Class declaringClass ) {
		return this.declaringClass.equalTo( declaringClass )
			? this
			: new Method( declaringClass, modifiers, returnType, name, parameterTypes );
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append( declaringClass + "#" + returnType + " " + name + "(" );
		Object[] values = parameterTypes;
		commaSeparated( b, values );
		b.append( ')' );
		return b.toString();
	}

	private static void commaSeparated( StringBuilder b, Object... values ) {
		if ( values.length > 0 ) {
			for ( int i = 0; i < values.length; i++ ) {
				b.append( values[i] ).append( ", " );
			}
			b.setLength( b.length() - 2 );
		}
	}
}

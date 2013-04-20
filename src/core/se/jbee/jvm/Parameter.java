package se.jbee.jvm;

public final class Parameter {

	public static Parameter parameter( Method method, int index ) {
		return new Parameter( method, index );
	}

	public final Method method;
	public final int index;

	private Parameter( Method method, int index ) {
		super();
		this.method = method;
		this.index = index;
	}

	@Override
	public boolean equals( Object obj ) {
		return obj instanceof Parameter && equalTo( (Parameter) obj );
	}

	public boolean equalTo( Parameter other ) {
		return index == other.index && method.equalTo( other.method );
	}

	@Override
	public String toString() {
		return method.parameterTypes[index].toString();
	}

	@Override
	public int hashCode() {
		return method.hashCode() ^ index;
	}

	public Class type() {
		return method.parameterTypes[index];
	}
}

package se.jbee.cls.reflect;

import se.jbee.cls.Code;
import se.jbee.cls.Method;

public final class MethodDeclaration {

	public final Method method;
	public final Code code;
	public final MethodReferences references;

	public MethodDeclaration( Method method, Code code, MethodReferences references ) {
		super();
		this.method = method;
		this.code = code;
		this.references = references;
	}

	@Override
	public boolean equals( Object obj ) {
		return obj instanceof MethodDeclaration && equalTo( (MethodDeclaration) obj );
	}

	private boolean equalTo( MethodDeclaration other ) {
		return method.equalTo( other.method );
	}

	@Override
	public int hashCode() {
		return method.hashCode();
	}

	@Override
	public String toString() {
		return method.toString();
	}
}

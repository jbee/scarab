package se.jbee.cls.reflect;

import se.jbee.cls.Method;

public final class MethodDeclaration {

	public final Method method;
	public final int maxStack;
	public final int maxLocals;
	public final int byteCount;
	public final int exceptionsCount;
	public final References references;

	public MethodDeclaration( Method method, int maxStack, int maxLocals, int byteCount,
			int exceptionsCount, References references ) {
		super();
		if ( references == null ) {
			throw new NullPointerException();
		}
		this.method = method;
		this.maxStack = maxStack;
		this.maxLocals = maxLocals;
		this.byteCount = byteCount;
		this.exceptionsCount = exceptionsCount;
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

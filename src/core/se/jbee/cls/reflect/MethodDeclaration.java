package se.jbee.cls.reflect;

import se.jbee.cls.Method;

public final class MethodDeclaration {

	public final Method method;
	public final int maxStack = 0;
	public final int maxLocals = 0;
	public final int byteCount = 0;
	public final int exceptionsCount = 0;
	public final References references;

	public MethodDeclaration( Method method, References references ) {
		super();
		this.method = method;
		this.references = references;
	}

}

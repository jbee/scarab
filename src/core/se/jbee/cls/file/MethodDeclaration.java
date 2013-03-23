package se.jbee.cls.file;

import se.jbee.cls.Class;

public final class MethodDeclaration {

	public static MethodDeclaration methodDeclaration( String declaration ) {
		return new MethodDeclaration( declaration );
	}

	private final String declaration;
	private final int endOfParameters;

	private MethodDeclaration( String declaration ) {
		super();
		this.declaration = declaration;
		this.endOfParameters = declaration.indexOf( ')' );
	}

	public Class returnType() {
		return Classfile.cls( declaration.substring( endOfParameters + 1 ) );
	}

	public Class[] parameterTypes() {
		return Classfile.classes( declaration.substring( 1, endOfParameters ) );
	}

	@Override
	public String toString() {
		return declaration;
	}
}

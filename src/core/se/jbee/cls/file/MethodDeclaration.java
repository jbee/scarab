package se.jbee.cls.file;

import static se.jbee.cls.file.ClassDeclaration.classDeclaration;
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
		return classDeclaration( declaration.substring( endOfParameters + 1 ) ).cls();
	}

	public Class[] parameterTypes() {
		return classDeclaration( declaration.substring( 1, endOfParameters ) ).classes();
	}

	@Override
	public String toString() {
		return declaration;
	}
}

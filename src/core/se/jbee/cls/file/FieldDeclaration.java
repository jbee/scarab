package se.jbee.cls.file;

import static se.jbee.cls.file.ClassDeclaration.classDeclaration;
import se.jbee.cls.Class;

public final class FieldDeclaration {

	public static FieldDeclaration fieldDeclaration( String declaration ) {
		return new FieldDeclaration( declaration );
	}

	private final String declaration;

	private FieldDeclaration( String declaration ) {
		super();
		this.declaration = declaration;
	}

	public Class type() {
		return classDeclaration( declaration ).cls();
	}

	@Override
	public String toString() {
		return declaration;
	}
}

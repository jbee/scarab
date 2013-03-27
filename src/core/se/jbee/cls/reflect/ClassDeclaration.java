package se.jbee.cls.reflect;

import se.jbee.cls.Archive;
import se.jbee.cls.Class;

public final class ClassDeclaration {

	//OPEN maybe have all declaration related code in a sub-package ? 

	public static ClassDeclaration classDeclaration( Archive archive, Class cls, Class superclass,
			Class[] interfaces, Declarations declarations, References references ) {
		return new ClassDeclaration( archive, cls, superclass, interfaces, declarations, references );
	}

	public final Archive archive;
	public final Class cls;
	public final Class superclass;
	public final Class[] interfaces;
	public final Declarations declarations;
	public final References references;

	private ClassDeclaration( Archive archive, Class cls, Class superclass, Class[] interfaces,
			Declarations declarations, References references ) {
		super();
		this.archive = archive;
		this.cls = cls;
		this.superclass = superclass;
		this.interfaces = interfaces;
		this.declarations = declarations;
		this.references = references;
	}

	@Override
	public int hashCode() {
		return cls.hashCode();
	}

	@Override
	public boolean equals( Object obj ) {
		return obj instanceof ClassDeclaration && equalTo( (ClassDeclaration) obj );
	}

	public boolean equalTo( ClassDeclaration other ) {
		return cls.equalTo( other.cls );
	}

	@Override
	public String toString() {
		return cls.toString();
	}
}

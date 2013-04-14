package se.jbee.jvm.reflect;

import se.jbee.jvm.Annotated;
import se.jbee.jvm.Annotation;
import se.jbee.jvm.Archive;
import se.jbee.jvm.ArrayItems;
import se.jbee.jvm.Class;
import se.jbee.jvm.Items;

public final class ClassDeclaration
		implements Annotated {

	public static ClassDeclaration classDeclaration( Archive archive, Class cls, Class superclass,
			Class[] interfaces, Declarations declarations, ClassReferences references ) {
		return new ClassDeclaration( archive, cls, superclass, interfaces, declarations, references );
	}

	public final Archive archive;
	public final Class cls;
	public final Class superclass;
	public final Class[] interfaces;
	public final Declarations declarations;
	public final ClassReferences references;
	public final Annotation[] annotations = null;//TODO add

	private ClassDeclaration( Archive archive, Class cls, Class superclass, Class[] interfaces,
			Declarations declarations, ClassReferences references ) {
		super();
		this.archive = archive;
		this.cls = cls;
		this.superclass = superclass;
		this.interfaces = interfaces;
		this.declarations = declarations;
		this.references = references;
	}

	@Override
	public Items<Annotation> annotations() {
		return ArrayItems.items( annotations );
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

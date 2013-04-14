package se.jbee.jvm.reflect;

import se.jbee.jvm.Annotation;
import se.jbee.jvm.Field;

public final class FieldDeclaration {

	public final Field field;
	public final Annotation[] annotations = null;//TODO add

	public FieldDeclaration( Field field ) {
		super();
		this.field = field;
	}

}

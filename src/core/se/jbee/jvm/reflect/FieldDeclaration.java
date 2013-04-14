package se.jbee.jvm.reflect;

import se.jbee.jvm.Annotated;
import se.jbee.jvm.Annotation;
import se.jbee.jvm.ArrayItems;
import se.jbee.jvm.Field;
import se.jbee.jvm.Items;

public final class FieldDeclaration
		implements Annotated {

	public final Field field;
	public final Annotation[] annotations = null;//TODO add

	public FieldDeclaration( Field field ) {
		super();
		this.field = field;
	}

	@Override
	public Items<Annotation> annotations() {
		return ArrayItems.items( annotations );
	}
}

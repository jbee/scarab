package se.jbee.jvm.reflect;

import se.jbee.jvm.Annotated;
import se.jbee.jvm.Annotation;
import se.jbee.jvm.ArrayItems;
import se.jbee.jvm.Code;
import se.jbee.jvm.Items;
import se.jbee.jvm.Method;

public final class MethodDeclaration
		implements Annotated {

	public final Method method;
	public final Code code;
	public final MethodReferences references;
	public final Annotation[] annotations = null;//TODO add
	public final Annotation[][] parameterAnnotations = null;

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

	@Override
	public Items<Annotation> annotations() {
		return ArrayItems.items( annotations );
	}
}

package se.jbee.jvm.reflect;

import se.jbee.jvm.Items;

public interface Declarations {

	Items<MethodDeclaration> declaredMethods();

	Items<FieldDeclaration> declaredFields();
}

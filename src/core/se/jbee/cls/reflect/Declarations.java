package se.jbee.cls.reflect;

import se.jbee.cls.Items;

public interface Declarations {

	Items<MethodDeclaration> declaredMethods();

	Items<FieldDeclaration> declaredFields();
}

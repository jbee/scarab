package se.jbee.cls.reflect;

import se.jbee.cls.Field;
import se.jbee.cls.Items;
import se.jbee.cls.Method;


public interface Declarations {

	Items<Method> declaredMethods();

	Items<Field> declaredFields();
}

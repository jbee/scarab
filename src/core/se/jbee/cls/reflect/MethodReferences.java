package se.jbee.cls.reflect;

import se.jbee.cls.Class;
import se.jbee.cls.Field;
import se.jbee.cls.Items;
import se.jbee.cls.Method;

public interface MethodReferences {

	/**
	 * @return All methods called in the body of this in the sequence they are called.
	 */
	Items<Method> calledMethods();

	/**
	 * @return All fields accessed in the body of this method in the sequence they are accessed.
	 */
	Items<Field> accessedFields();

	Items<Class> constructedClasses();
}

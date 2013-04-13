package se.jbee.jvm.reflect;

import se.jbee.jvm.Class;
import se.jbee.jvm.Field;
import se.jbee.jvm.Items;
import se.jbee.jvm.Method;

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

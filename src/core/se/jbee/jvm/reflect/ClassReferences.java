package se.jbee.jvm.reflect;

import se.jbee.jvm.Class;
import se.jbee.jvm.Field;
import se.jbee.jvm.Items;
import se.jbee.jvm.Method;


public interface ClassReferences {

	/**
	 * @return class method-calls.
	 */
	Items<Method> calledMethods();

	/**
	 * @return virtual (interface) method-calls.
	 */
	Items<Method> calledInterfaceMethods();

	/**
	 * @return field-accesses. This will include constants as long as they are not of a primitive or
	 *         {@link String} type. Those will be included in the constant pool as separate entries
	 *         (even when defined in another class).
	 */
	Items<Field> accessedFields();

	/**
	 * @return classes of the accessed fields and/or called methods.
	 */
	Items<Class> referencedClasses();
}

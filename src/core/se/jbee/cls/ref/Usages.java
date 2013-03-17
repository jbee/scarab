package se.jbee.cls.ref;

import se.jbee.cls.Items;

public interface Usages {

	/**
	 * @return class method-calls.
	 */
	Items<Method> methods();

	/**
	 * @return virtual (interface) method-calls.
	 */
	Items<Method> interfaceMethods();

	/**
	 * @return field-accesses. This will include constants as long as they are not of a primitive or
	 *         {@link String} type. Those will be included in the constant pool as separate entries
	 *         (even when defined in another class).
	 */
	Items<Field> fields();

	/**
	 * @return classes of the accessed fields and/or called methods.
	 */
	Items<Class> classes();
}

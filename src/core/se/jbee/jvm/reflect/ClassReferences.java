package se.jbee.jvm.reflect;

import se.jbee.jvm.Class;
import se.jbee.jvm.Field;
import se.jbee.jvm.Items;
import se.jbee.jvm.Method;

/**
 * The references from one {@link Class} to others.
 * 
 * These are directly derived from the constant pool entries.
 * 
 * @author Jan Bernitt (jan@jbee.se)
 */
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
	 *         {@link String} type. Those can be included in the constant pool as separate constant
	 *         entries (even when defined in another class).
	 */
	Items<Field> accessedFields();

	/**
	 * @return classes of the accessed fields and/or called methods.
	 */
	Items<Class> referencedClasses();
}

package se.jbee.jar;

public interface Usages {

	/**
	 * @return class method-calls.
	 */
	Items<MethodRef> methods();

	/**
	 * @return virtual (interface) method-calls.
	 */
	Items<MethodRef> interfaceMethods();

	/**
	 * @return field-accesses. This will include constants as long as they are not of a primitive or
	 *         {@link String} type. Those will be included in the constant pool as separate entries
	 *         (even when defined in another class).
	 */
	Items<FieldRef> fields();

	/**
	 * @return types of the accessed fields and/or called methods.
	 */
	Items<TypeRef> types();
}

package se.jbee.io.classfile;

public interface References {

	/**
	 * @return class method-calls.
	 */
	Iterable<MethodRef> methods();

	/**
	 * @return virtual (interface) method-calls.
	 */
	Iterable<MethodRef> interfaceMethods();

	/**
	 * @return field-accesses. This will include constants as long as they are not of a primitive or
	 *         {@link String} type. Those will be included in the constant pool as separate entries
	 *         (even when defined in another class).
	 */
	Iterable<FieldRef> fields();

	/**
	 * @return types of the accessed fields and/or called methods.
	 */
	Iterable<TypeRef> types();
}

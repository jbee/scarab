package se.jbee.cls.file;

import static se.jbee.cls.file.ClassDescriptor.classDescriptor;
import se.jbee.cls.Class;

public final class FieldDescriptor {

	public static FieldDescriptor fieldDescriptor( String descriptor ) {
		return new FieldDescriptor( descriptor );
	}

	private final String descriptor;

	private FieldDescriptor( String declaration ) {
		super();
		this.descriptor = declaration;
	}

	public Class type() {
		return classDescriptor( descriptor ).cls();
	}

	@Override
	public String toString() {
		return descriptor;
	}
}

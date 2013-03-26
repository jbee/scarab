package se.jbee.cls.file;

import static se.jbee.cls.file.ClassDescriptor.classDescriptor;
import se.jbee.cls.Class;

public final class MethodDescriptor {

	public static MethodDescriptor methodDescriptor( String descriptor ) {
		return new MethodDescriptor( descriptor );
	}

	private final String descriptor;
	private final int endOfParameters;

	private MethodDescriptor( String descriptor ) {
		super();
		this.descriptor = descriptor;
		this.endOfParameters = descriptor.indexOf( ')' );
	}

	public Class returnType() {
		return classDescriptor( descriptor.substring( endOfParameters + 1 ) ).cls();
	}

	public Class[] parameterTypes() {
		return classDescriptor( descriptor.substring( 1, endOfParameters ) ).classes();
	}

	@Override
	public String toString() {
		return descriptor;
	}
}

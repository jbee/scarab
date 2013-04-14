package se.jbee.jvm;

public interface Member { // maybe make this a VO?

	Class declaringClass();

	String name();

	Modifiers modifiers();
}

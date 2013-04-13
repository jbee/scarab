package se.jbee.jvm;

public interface Member {

	Class declaringClass();

	String name();

	Modifiers modifiers();
}

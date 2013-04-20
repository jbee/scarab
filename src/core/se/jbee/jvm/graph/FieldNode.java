package se.jbee.jvm.graph;

import se.jbee.jvm.Class;
import se.jbee.jvm.Field;

public final class FieldNode
		implements Node<Field> {

	private Field key;
	public final int serial;
	public final ClassNode declaringClass;
	public final ClassNode type;
	public final Edges<Class, ClassNode> accessedBy = new Edges<Class, ClassNode>();

	FieldNode( ClassGraph graph, Field field, int serial ) {
		super();
		this.key = field;
		this.serial = serial;
		this.declaringClass = graph.cls( field.declaringClass );
		this.type = graph.cls( field.type );
	}

	@Override
	public Field id() {
		return key;
	}

	@Override
	public int serial() {
		return serial;
	}

	void declare( Field field ) {
		this.key = field;
	}

	public boolean isInstanceField() {
		return !key.modifiers.isStatic();
	}

	public boolean isClassField() {
		return key.modifiers.isStatic() && !key.modifiers.isFinal();
	}

	public boolean isConstantField() {
		return key.modifiers.isStatic() && key.modifiers.isFinal();
	}

	public boolean isEnumConstant() {
		return isConstantField() && declaringClass.subclasses.contains( Class.ENUM );
	}

	@Override
	public String toString() {
		return key.toString();
	}

}

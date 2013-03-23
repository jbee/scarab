package se.jbee.cls.sca.graph;

import se.jbee.cls.ref.Class;
import se.jbee.cls.ref.Field;

public class FieldNode
		implements Node<Field> {

	private Field key;
	public final ClassNode declaringClass;
	public final ClassNode type;
	public final Edges<Class, ClassNode> accessedBy = new Edges<Class, ClassNode>();

	FieldNode( ClassGraph graph, Field field ) {
		super();
		this.key = field;
		this.declaringClass = graph.cls( field.declaringClass );
		this.type = graph.cls( field.type );
	}

	@Override
	public Field id() {
		return key;
	}

	public void declaredAs( Field field ) {
		this.key = field;
	}

	@Override
	public String toString() {
		return key.toString();
	}

}

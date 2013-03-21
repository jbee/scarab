package se.jbee.cls.sca.graph;

import se.jbee.cls.ref.Class;
import se.jbee.cls.ref.Field;

public class FieldNode
		implements Node<Field> {

	public Field field;
	public final ClassNode declaringClass;
	public final ClassNode type;
	public final Edges<Class, ClassNode> accessedBy = new Edges<Class, ClassNode>();

	FieldNode( ClassGraph graph, Field field ) {
		super();
		this.field = field;
		this.declaringClass = graph.cls( field.declaringClass );
		this.type = graph.cls( field.type );
	}

	@Override
	public Field id() {
		return field;
	}

	public void declaredAs( Field field ) {
		this.field = field;
	}

}

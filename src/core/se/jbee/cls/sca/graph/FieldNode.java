package se.jbee.cls.sca.graph;

import se.jbee.cls.ref.Field;
import se.jbee.cls.ref.Type;

public class FieldNode
		implements Node<Field> {

	public final Field field;
	public final ClassNode declaringClass;
	public final ClassNode type;
	public final Edges<Type, ClassNode> accessedBy = new Edges<Type, ClassNode>();

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

}

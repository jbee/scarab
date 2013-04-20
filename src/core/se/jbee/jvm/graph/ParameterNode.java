package se.jbee.jvm.graph;

import se.jbee.jvm.Annotation;
import se.jbee.jvm.Parameter;
import se.jbee.jvm.reflect.MethodDeclaration;

public final class ParameterNode
		implements Node<Parameter> {

	private final ClassGraph graph;
	private final Parameter key;
	public final int serial;

	public final ClassNode type;

	public final Edges<Annotation, AnnotationNode> annotations = new Edges<Annotation, AnnotationNode>();

	ParameterNode( ClassGraph graph, Parameter key, int serial ) {
		super();
		this.graph = graph;
		this.key = key;
		this.serial = serial;
		this.type = graph.cls( key.type() );
	}

	@Override
	public Parameter id() {
		return key;
	}

	@Override
	public int serial() {
		return serial;
	}

	void declare( MethodDeclaration method ) {

	}

	public MethodNode method() {
		return graph.method( key.method );
	}

}

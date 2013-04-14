package se.jbee.jvm.graph;

import se.jbee.jvm.Annotation;

public final class AnnotationNode
		implements Node<Annotation> {

	private final ClassGraph graph;
	private final Annotation key;
	public final int serial;
	public final ClassNode cls;

	//TODO add classes, methods, fields and annotation where this annotation is used 

	AnnotationNode( ClassGraph graph, Annotation annotation, int serial ) {
		super();
		this.graph = graph;
		this.key = annotation;
		this.serial = serial;
		this.cls = graph.cls( annotation.type );
	}

	@Override
	public Annotation id() {
		return key;
	}

	@Override
	public int serial() {
		return serial;
	}
}

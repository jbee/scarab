package se.jbee.jvm.graph;

import se.jbee.jvm.Annotation;
import se.jbee.jvm.Class;

/**
 * Represents a {@link Class} that is an {@link Annotation}.
 * 
 * @author Jan Bernitt (jan@jbee.se)
 */
public final class AnnotationNode
		implements Node<Annotation> {

	private final ClassGraph graph;
	private final Annotation key;
	public final int serial;
	public final ClassNode cls;

	public final Edges<Class, ClassNode> annotatedClasses = new Edges<Class, ClassNode>();

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

	@Override
	public String toString() {
		return key.toString();
	}
}

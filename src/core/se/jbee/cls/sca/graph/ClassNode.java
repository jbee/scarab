package se.jbee.cls.sca.graph;

import se.jbee.cls.ref.Class;
import se.jbee.cls.ref.Field;
import se.jbee.cls.ref.Method;
import se.jbee.cls.ref.Type;

public final class ClassNode
		implements Node {

	private final Graph graph;
	private final Type type;
	private Class cls;
	private ClassNode superclass;
	private final Edges<ClassNode> superinterfaces = new Edges<ClassNode>();
	private final Edges<ClassNode> subclasses = new Edges<ClassNode>();
	private final Edges<ClassNode> references = new Edges<ClassNode>();
	private final Edges<ClassNode> referencedBy = new Edges<ClassNode>();
	private final Edges<ClassNode> calls = new Edges<ClassNode>();
	private final Edges<ClassNode> calledBy = new Edges<ClassNode>();
	private final Edges<ClassNode> accesses = new Edges<ClassNode>();
	private final Edges<ClassNode> accessedBy = new Edges<ClassNode>();

	ClassNode( Graph graph, Type type ) {
		super();
		this.graph = graph;
		this.type = type;
	}

	void complete( Class cls ) {
		this.cls = cls;
	}

	public void calls( Method method ) {

	}

	public void accesses( Field field ) {

	}

	public void references( Type type ) {

	}

	@Override
	public String id() {
		return cls.type.name;
	}

	@Override
	public boolean equals( Object obj ) {
		return obj instanceof ClassNode && equalTo( (ClassNode) obj );
	}

	public boolean equalTo( ClassNode other ) {
		return cls.equalTo( other.cls );
	}

	@Override
	public int hashCode() {
		return cls.hashCode();
	}

	@Override
	public String toString() {
		return cls.toString();
	}
}

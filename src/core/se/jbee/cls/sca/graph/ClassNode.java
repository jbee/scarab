package se.jbee.cls.sca.graph;

import se.jbee.cls.ref.Class;
import se.jbee.cls.ref.Field;
import se.jbee.cls.ref.Method;
import se.jbee.cls.ref.Type;

public final class ClassNode
		implements Node {

	private final Graph graph;
	public final Type type;
	private Class cls;
	private ClassNode superclass;
	private final Edges<ClassNode> subclasses = new Edges<ClassNode>();
	private final Edges<ClassNode> interfaces = new Edges<ClassNode>();
	private final Edges<ClassNode> implementations = new Edges<ClassNode>();
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
		ClassNode sc = graph.cls( cls.superclass );
		this.superclass = sc;
		sc.subclasses.add( this );
		for ( Type t : cls.interfaces ) {
			ClassNode other = graph.cls( t );
			other.implementations.add( this );
			interfaces.add( other );
		}
	}

	public ClassNode superclass() {
		return superclass;
	}

	public void calls( Method method ) {
		ClassNode other = graph.cls( method.declaringClass );
		other.calledBy.add( this );
		calls.add( other );
		references( method.returnType );
		for ( Type p : method.parameterTypes ) {
			references( p );
		}
	}

	public void accesses( Field field ) {
		references( field.type );
		ClassNode other = graph.cls( field.declaringClass );
		other.accessedBy.add( this );
		accesses.add( other );
	}

	public void references( Type type ) {
		ClassNode other = graph.cls( type );
		other.referencedBy.add( this );
		references.add( other );
	}

	@Override
	public String id() {
		return type.name;
	}

	@Override
	public boolean equals( Object obj ) {
		return obj instanceof ClassNode && equalTo( (ClassNode) obj );
	}

	public boolean equalTo( ClassNode other ) {
		return type.equalTo( other.type );
	}

	@Override
	public int hashCode() {
		return type.hashCode();
	}

	@Override
	public String toString() {
		return type.toString();
	}
}

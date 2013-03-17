package se.jbee.cls.sca.graph;

import se.jbee.cls.ref.Class;
import se.jbee.cls.ref.Field;
import se.jbee.cls.ref.Method;
import se.jbee.cls.ref.Type;

public final class ClassNode
		implements Node<Type> {

	private final ClassGraph graph;
	public final Type type;
	private Class cls;
	private ClassNode superclass;
	public final Edges<Type, ClassNode> subclasses = new Edges<Type, ClassNode>();
	public final Edges<Type, ClassNode> interfaces = new Edges<Type, ClassNode>();
	public final Edges<Type, ClassNode> implementations = new Edges<Type, ClassNode>();
	public final Edges<Type, ClassNode> references = new Edges<Type, ClassNode>();
	public final Edges<Type, ClassNode> referencedBy = new Edges<Type, ClassNode>();
	public final Edges<Type, ClassNode> calls = new Edges<Type, ClassNode>();
	public final Edges<Type, ClassNode> calledBy = new Edges<Type, ClassNode>();
	public final Edges<Type, ClassNode> accesses = new Edges<Type, ClassNode>();
	public final Edges<Type, ClassNode> accessedBy = new Edges<Type, ClassNode>();

	ClassNode( ClassGraph graph, Type type ) {
		super();
		this.graph = graph;
		this.type = type;
		graph.pkg( type.pkg() ).types.add( this );
	}

	void is( Class cls ) {
		if ( !cls.type.equalTo( type ) ) {
			throw new IllegalArgumentException();
		}
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
		references( method.declaringClass );
		references( method.returnType );
		references( method.parameterTypes );
	}

	public void accesses( Field field ) {
		references( field.type );
		references( field.declaringClass );
		ClassNode other = graph.cls( field.declaringClass );
		other.accessedBy.add( this );
		accesses.add( other );
	}

	public void references( Type... types ) {
		for ( Type t : types ) {
			references( t );
		}
	}

	public void references( Type type ) {
		ClassNode other = graph.cls( type );
		other.referencedBy.add( this );
		references.add( other );
		packageReferences( this.type, type );
	}

	private void packageReferences( Type type, Type other ) {
		PackageNode pkg = graph.pkg( type.pkg() );
		PackageNode otherPkg = graph.pkg( other.pkg() );
		pkg.references( otherPkg );
	}

	@Override
	public Type id() {
		return type;
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

package se.jbee.cls.sca.graph;

import se.jbee.cls.ref.Class;
import se.jbee.cls.ref.ClassSignature;
import se.jbee.cls.ref.Field;
import se.jbee.cls.ref.Method;

public final class ClassNode
		implements Node<Class> {

	private final ClassGraph graph;
	public final Class cls;
	private ClassNode superclass;
	//TODO add parameter refs
	public final Edges<Method, MethodNode> calls = new Edges<Method, MethodNode>();
	public final Edges<Field, FieldNode> accesses = new Edges<Field, FieldNode>();
	public final Edges<Class, ClassNode> subclasses = new Edges<Class, ClassNode>();
	public final Edges<Class, ClassNode> interfaces = new Edges<Class, ClassNode>();
	public final Edges<Class, ClassNode> implementations = new Edges<Class, ClassNode>();
	public final Edges<Class, ClassNode> references = new Edges<Class, ClassNode>();
	public final Edges<Class, ClassNode> referencedBy = new Edges<Class, ClassNode>();
	public final Edges<Class, ClassNode> callsTypes = new Edges<Class, ClassNode>();
	public final Edges<Class, ClassNode> calledBy = new Edges<Class, ClassNode>();
	public final Edges<Class, ClassNode> accessesTypes = new Edges<Class, ClassNode>();
	public final Edges<Class, ClassNode> accessedBy = new Edges<Class, ClassNode>();

	ClassNode( ClassGraph graph, Class type ) {
		super();
		this.graph = graph;
		this.cls = type;
		graph.pkg( type.pkg() ).types.add( this );
	}

	void has( ClassSignature signature ) {
		if ( !signature.cls.equalTo( cls ) ) {
			throw new IllegalArgumentException();
		}
		ClassNode sc = graph.cls( signature.superclass );
		this.superclass = sc;
		sc.subclasses.add( this );
		for ( Class t : signature.interfaces ) {
			ClassNode other = graph.cls( t );
			other.implementations.add( this );
			interfaces.add( other );
		}
	}

	public ClassNode superclass() {
		return superclass;
	}

	public void calls( Method method ) {
		// type level
		ClassNode other = graph.cls( method.declaringClass );
		other.calledBy.add( this );
		callsTypes.add( other );
		references( method.declaringClass );
		references( method.returnType );
		references( method.parameterTypes );
		// method level
		MethodNode m = graph.method( method );
		m.calledBy.add( this );
		calls.add( m );
	}

	public void accesses( Field field ) {
		// type level
		references( field.type );
		references( field.declaringClass );
		ClassNode other = graph.cls( field.declaringClass );
		other.accessedBy.add( this );
		accessesTypes.add( other );
		// field level
		FieldNode f = graph.field( field );
		f.accessedBy.add( this );
	}

	public void references( Class... types ) {
		for ( Class t : types ) {
			references( t );
		}
	}

	public void references( Class type ) {
		ClassNode other = graph.cls( type );
		other.referencedBy.add( this );
		references.add( other );
		packageReferences( this.cls, type );
	}

	private void packageReferences( Class type, Class other ) {
		PackageNode pkg = graph.pkg( type.pkg() );
		PackageNode otherPkg = graph.pkg( other.pkg() );
		pkg.references( otherPkg );
	}

	@Override
	public Class id() {
		return cls;
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

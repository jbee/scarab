package se.jbee.cls.sca.graph;

import se.jbee.cls.ref.Class;
import se.jbee.cls.ref.Field;
import se.jbee.cls.ref.Method;
import se.jbee.cls.ref.References;
import se.jbee.cls.ref.Type;

public final class ClassNode
		implements Node<Class> {

	private final ClassGraph graph;
	public Class cls;
	private ClassNode superclass;
	public final Edges<Field, FieldNode> fields = new Edges<Field, FieldNode>();
	public final Edges<Method, MethodNode> methods = new Edges<Method, MethodNode>();

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

	ClassNode( ClassGraph graph, Class cls ) {
		super();
		this.graph = graph;
		this.cls = cls;
		graph.pkg( cls.pkg() ).classes.add( this );
	}

	void declaredAs( Type type ) {
		if ( !type.cls.equalTo( cls ) ) {
			throw new IllegalArgumentException();
		}
		this.cls = type.cls;
		ClassNode sc = graph.cls( type.superclass );
		this.superclass = sc;
		sc.subclasses.add( this );
		for ( Class t : type.interfaces ) {
			ClassNode other = graph.cls( t );
			other.implementations.add( this );
			interfaces.add( other );
		}
		final References refs = type.references;
		for ( Method m : refs.calledMethods() ) {
			calls( m );
		}
		for ( Method m : refs.calledInterfaceMethods() ) {
			calls( m );
		}
		for ( Field f : refs.accessedFields() ) {
			accesses( f );
		}
		for ( Class t : refs.referencedClasses() ) {
			references( t );
		}
		for ( Field f : type.declarations.declaredFields() ) {
			declares( f );
		}
		for ( Method m : type.declarations.declaredMethods() ) {
			declares( m );
		}
	}

	public ClassNode superclass() {
		return superclass;
	}

	private void declares( Method method ) {
		MethodNode m = graph.method( method );
		m.declaredAs( method );
		methods.add( m );
	}

	private void declares( Field field ) {
		FieldNode f = graph.field( field );
		f.declaredAs( field );
		fields.add( f );
	}

	private void calls( Method method ) {
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

	private void accesses( Field field ) {
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

	private void references( Class... types ) {
		for ( Class t : types ) {
			references( t );
		}
	}

	private void references( Class type ) {
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

	public MethodNode method( String name ) {
		for ( MethodNode m : methods ) {
			if ( m.method.name.equals( name ) ) {
				return m;
			}
		}
		throw new NoSuchMethodError( name );
	}
}

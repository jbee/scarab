package se.jbee.cls.graph;

import se.jbee.cls.Archive;
import se.jbee.cls.Class;
import se.jbee.cls.Field;
import se.jbee.cls.Method;
import se.jbee.cls.Modifiers;
import se.jbee.cls.References;
import se.jbee.cls.Type;

public final class ClassNode
		implements Node<Class> {

	private final ClassGraph graph;
	private Class key;
	private ClassNode superclass;
	private ArchiveNode archive;
	private Modifiers modifiers;
	public final PackageNode pkg;
	public final Edges<Field, FieldNode> fields = new Edges<Field, FieldNode>();
	public final Edges<Method, MethodNode> methods = new Edges<Method, MethodNode>();
	public final Edges<Method, MethodNode> calls = new Edges<Method, MethodNode>();
	public final Edges<Field, FieldNode> accesses = new Edges<Field, FieldNode>();
	//TODO inner classes
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
		this.key = cls;
		this.pkg = graph.pkg( cls.pkg() );
		this.pkg.classes.add( this );
		this.archive = graph.archive( Archive.RUNTIME );
		this.modifiers = Modifiers.UNKNOWN;
	}

	void declaredAs( Type type ) {
		if ( !type.cls.equalTo( key ) ) {
			throw new IllegalArgumentException();
		}
		this.key = type.cls;
		this.modifiers = type.modifiers;
		this.archive = graph.archive( type.archive );
		this.archive.contains( this );
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
			declaredAs( f );
		}
		for ( Method m : type.declarations.declaredMethods() ) {
			declaredAs( m );
		}
	}

	public Modifiers modifiers() {
		return modifiers;
	}

	public ArchiveNode archive() {
		return archive;
	}

	public ClassNode superclass() {
		return superclass;
	}

	public ClassNode declaringClass( Method method ) {
		MethodNode node = methods.node( method.declaredBy( key ) );
		if ( node != null ) {
			return this;
		}
		return key.isObject() || superclass.key.isObject()
			? null
			: superclass.declaringClass( method );
	}

	private void declaredAs( Method method ) {
		MethodNode m = graph.method( method );
		m.declaredAs( method );
		methods.add( m );
	}

	private void declaredAs( Field field ) {
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
		packageReferences( this.key, type );
	}

	private void packageReferences( Class type, Class other ) {
		PackageNode pkg = graph.pkg( type.pkg() );
		PackageNode otherPkg = graph.pkg( other.pkg() );
		pkg.references( otherPkg );
	}

	@Override
	public Class id() {
		return key;
	}

	@Override
	public boolean equals( Object obj ) {
		return obj instanceof ClassNode && equalTo( (ClassNode) obj );
	}

	public boolean equalTo( ClassNode other ) {
		return key.equalTo( other.key );
	}

	@Override
	public int hashCode() {
		return key.hashCode();
	}

	@Override
	public String toString() {
		return key.toString();
	}

	public MethodNode method( String name ) {
		for ( MethodNode m : methods ) {
			if ( m.id().name.equals( name ) ) {
				return m;
			}
		}
		throw new NoSuchMethodError( name );
	}
}

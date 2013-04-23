package se.jbee.jvm.graph;

import se.jbee.jvm.Annotation;
import se.jbee.jvm.Class;
import se.jbee.jvm.Field;
import se.jbee.jvm.Method;
import se.jbee.jvm.Parameter;
import se.jbee.jvm.reflect.MethodDeclaration;

public final class MethodNode
		implements Node<Method> {

	private Method key;
	public final int serial;
	public final ClassNode declaringClass;
	public final ClassNode returnType;
	public final Edges<Annotation, AnnotationNode> annotations = new Edges<Annotation, AnnotationNode>();
	public final Edges<Class, ClassNode> parameterTypes = new Edges<Class, ClassNode>();
	public final Edges<Class, ClassNode> calledBy = new Edges<Class, ClassNode>();
	public final Edges<Method, MethodNode> calls = new Edges<Method, MethodNode>();
	public final Edges<Field, FieldNode> accesses = new Edges<Field, FieldNode>();

	public final OverrideNode overrides;
	private final ParameterNode[] parameters;
	private final ClassGraph graph;

	MethodNode( ClassGraph graph, Method method, int serial ) {
		super();
		this.graph = graph;
		this.key = method;
		this.serial = serial;
		this.declaringClass = graph.cls( method.declaringClass );
		this.returnType = graph.cls( method.returnType );
		this.overrides = graph.override( method );
		this.overrides.declaredBy( this );
		this.parameters = new ParameterNode[method.parameterTypes.length];
		int i = 0;
		for ( Parameter p : method.parameters() ) {
			ClassNode cls = graph.cls( p.type() );
			parameterTypes.add( cls );
			parameters[i++] = graph.parameter( p );
		}
	}

	@Override
	public Method id() {
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

	void declare( MethodDeclaration method ) {
		this.key = method.method;
		for ( Annotation a : method.annotations() ) {
			AnnotationNode annotation = graph.annotation( a );
			annotations.put( a, annotation );
			annotation.annotatedMethods.add( this );
		}
		for ( Method m : method.references.calledMethods() ) {
			calls.add( graph.method( m ) );
		}
		for ( Field f : method.references.accessedFields() ) {
			accesses.add( graph.field( f ) );
		}
	}

	public ParameterNode parameter( int index ) {
		return parameters[index];
	}

	public boolean isOverridden() {
		return overrides.isOverridden( this );
	}

	public boolean isInstanceMethod() {
		return !key.modifiers.isStatic() && !isInitMethod();
	}

	public boolean isStaticMethod() {
		return key.modifiers.isStatic() && !isInitMethod();
	}

	public boolean isInitMethod() {
		return key.isConstructor();
	}

}

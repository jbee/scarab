package se.jbee.jvm.graph;

import se.jbee.jvm.Class;
import se.jbee.jvm.Field;
import se.jbee.jvm.Method;
import se.jbee.jvm.reflect.MethodDeclaration;

public final class MethodNode
		implements Node<Method> {

	private Method key;
	public final int serial;
	public final ClassNode declaringClass;
	public final ClassNode returnType;
	public final Edges<Class, ClassNode> parameterTypes = new Edges<Class, ClassNode>();
	public final Edges<Class, ClassNode> calledBy = new Edges<Class, ClassNode>();
	public final Edges<Method, MethodNode> calls = new Edges<Method, MethodNode>();
	public final Edges<Field, FieldNode> accesses = new Edges<Field, FieldNode>();

	public final OverrideNode overrides;
	private final ClassNode[] parameters;
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
		this.parameters = new ClassNode[method.parameterTypes.length];
		int i = 0;
		for ( Class p : method.parameterTypes ) {
			ClassNode cls = graph.cls( p );
			parameterTypes.add( cls );
			parameters[i++] = cls;
		}
	}

	@Override
	public Method id() {
		return key;
	}

	@Override
	public String toString() {
		return key.toString();
	}

	void declare( MethodDeclaration method ) {
		this.key = method.method;
		for ( Method m : method.references.calledMethods() ) {
			calls.add( graph.method( m ) );
		}
		for ( Field f : method.references.accessedFields() ) {
			accesses.add( graph.field( f ) );
		}
	}

	public ClassNode parameter( int index ) {
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

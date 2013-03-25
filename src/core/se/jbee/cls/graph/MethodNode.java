package se.jbee.cls.graph;

import se.jbee.cls.Class;
import se.jbee.cls.Method;

public final class MethodNode
		implements Node<Method> {

	private Method key;
	public final int serial;
	public final ClassNode declaringClass;
	public final ClassNode returnType;
	public final Edges<Class, ClassNode> parameterTypes = new Edges<Class, ClassNode>();
	public final Edges<Class, ClassNode> calledBy = new Edges<Class, ClassNode>();
	public final OverrideNode overrides;
	private final ClassNode[] parameters;

	MethodNode( ClassGraph graph, Method method, int serial ) {
		super();
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

	void declaredAs( Method method ) {
		this.key = method;
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

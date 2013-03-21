package se.jbee.cls.sca.graph;

import se.jbee.cls.ref.Class;
import se.jbee.cls.ref.Method;

public class MethodNode
		implements Node<Method> {

	public Method method;
	public final ClassNode declaringClass;
	public final ClassNode returnType;
	public final Edges<Class, ClassNode> parameterTypes = new Edges<Class, ClassNode>();
	public final Edges<Class, ClassNode> calledBy = new Edges<Class, ClassNode>();
	private final ClassNode[] parameters;

	MethodNode( ClassGraph graph, Method method ) {
		super();
		this.method = method;
		this.declaringClass = graph.cls( method.declaringClass );
		this.returnType = graph.cls( method.returnType );
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
		return method;
	}

	public void declaredAs( Method method ) {
		this.method = method;
	}

	public ClassNode parameter( int index ) {
		return parameters[index];
	}

}

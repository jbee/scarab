package se.jbee.cls.sca.graph;

import se.jbee.cls.ref.Method;
import se.jbee.cls.ref.Class;

public class MethodNode
		implements Node<Method> {

	public final Method method;
	public final ClassNode returnType;
	public final Edges<Class, ClassNode> parameterTypes = new Edges<Class, ClassNode>();
	public final Edges<Class, ClassNode> calledBy = new Edges<Class, ClassNode>();

	MethodNode( ClassGraph graph, Method method ) {
		super();
		this.method = method;
		this.returnType = graph.cls( method.returnType );
		for ( Class p : method.parameterTypes ) {
			parameterTypes.add( graph.cls( p ) );
		}
	}

	@Override
	public Method id() {
		return method;
	}

}

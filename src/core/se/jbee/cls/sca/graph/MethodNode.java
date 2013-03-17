package se.jbee.cls.sca.graph;

import se.jbee.cls.ref.Method;
import se.jbee.cls.ref.Type;

public class MethodNode
		implements Node<Method> {

	public final Method method;
	public final ClassNode returnType;
	public final Edges<Type, ClassNode> parameterTypes = new Edges<Type, ClassNode>();
	public final Edges<Type, ClassNode> calledBy = new Edges<Type, ClassNode>();

	MethodNode( ClassGraph graph, Method method ) {
		super();
		this.method = method;
		this.returnType = graph.cls( method.returnType );
		for ( Type p : method.parameterTypes ) {
			parameterTypes.add( graph.cls( p ) );
		}
	}

	@Override
	public Method id() {
		return method;
	}

}

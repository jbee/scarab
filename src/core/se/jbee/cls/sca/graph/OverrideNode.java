package se.jbee.cls.sca.graph;

import se.jbee.cls.Class;
import se.jbee.cls.Method;

/**
 * This type of node represents a {@link Method} without taking its declaring {@link Class} into
 * account. Those are instead referenced by the {@link #implementedBy} {@link Edges} so that it can
 * be said that a method is overridden when there is a method with the same signature in another
 * class that is one f the super-classes or interfaces of a {@link Class}.
 * 
 * @author Jan Bernitt (jan@jbee.se)
 */
public final class OverrideNode
		implements Node<Method> {

	private final ClassGraph graph;
	private final Method key;

	public final Edges<Class, ClassNode> implementedBy = new Edges<Class, ClassNode>();
	public final Edges<Method, MethodNode> implementations = new Edges<Method, MethodNode>();

	OverrideNode( ClassGraph graph, Method method ) {
		super();
		this.graph = graph;
		this.key = method;
	}

	void declaredBy( MethodNode node ) {
		implementedBy.add( node.declaringClass );
		implementations.add( node );
	}

	@Override
	public Method id() {
		return key;
	}

	public boolean isOverridden( Method method ) {
		return isOverridden( graph.method( method ) );
	}

	public boolean isOverridden( MethodNode method ) {
		final Class cls = method.declaringClass.id();
		if ( cls.isObject() || cls.isNone() ) {
			return false;
		}
		if ( isImplementation( method.declaringClass ) ) {
			return true;
		}
		ClassNode superclass = method.declaringClass.superclass();
		while ( superclass != null ) {
			if ( implementedBy.contains( superclass.id() ) || isImplementation( superclass ) ) {
				return true;
			}
			superclass = superclass.superclass();
		}
		return false;
	}

	private boolean isImplementation( ClassNode cls ) {
		for ( ClassNode i : cls.interfaces ) {
			if ( implementedBy.contains( i.id() ) ) {
				return true;
			}
		}
		return false;
	}

}

package se.jbee.cls.sca.graph;

import se.jbee.cls.ref.Class;
import se.jbee.cls.ref.Field;
import se.jbee.cls.ref.Method;
import se.jbee.cls.ref.Package;
import se.jbee.cls.ref.Type;
import se.jbee.cls.sca.JarProcessor;

public final class ClassGraph
		implements JarProcessor {

	private final Edges<Class, ClassNode> classes = new Edges<Class, ClassNode>();
	private final Edges<Package, PackageNode> packages = new Edges<Package, PackageNode>();
	private final Edges<Method, MethodNode> methods = new Edges<Method, MethodNode>();
	private final Edges<Field, FieldNode> fields = new Edges<Field, FieldNode>();

	@Override
	public void process( Type type ) {
		cls( type.cls ).declaredAs( type );
	}

	public ClassNode cls( Class type ) {
		ClassNode node = classes.node( type );
		if ( node == null ) {
			node = new ClassNode( this, type );
			classes.add( node );
		}
		return node;
	}

	public PackageNode pkg( Package pkg ) {
		PackageNode node = packages.node( pkg );
		if ( node == null ) {
			node = new PackageNode( this, pkg );
			packages.add( node );
		}
		return node;
	}

	public MethodNode method( Method method ) {
		MethodNode node = methods.node( method );
		if ( node == null ) {
			node = new MethodNode( this, method );
			methods.add( node );
		}
		return node;
	}

	public FieldNode field( Field field ) {
		FieldNode node = fields.node( field );
		if ( node == null ) {
			node = new FieldNode( this, field );
			fields.add( node );
		}
		return node;
	}
}

package se.jbee.cls.graph;

import se.jbee.cls.Archive;
import se.jbee.cls.Class;
import se.jbee.cls.ClassProcessor;
import se.jbee.cls.Field;
import se.jbee.cls.Method;
import se.jbee.cls.Package;
import se.jbee.cls.Type;

public final class ClassGraph
		implements ClassProcessor {

	public final Edges<Class, ClassNode> classes = new Edges<Class, ClassNode>();
	public final Edges<Package, PackageNode> packages = new Edges<Package, PackageNode>();
	public final Edges<Method, MethodNode> methods = new Edges<Method, MethodNode>();
	public final Edges<Field, FieldNode> fields = new Edges<Field, FieldNode>();
	public final Edges<Method, OverrideNode> overrides = new Edges<Method, OverrideNode>();
	public final Edges<Archive, ArchiveNode> archives = new Edges<Archive, ArchiveNode>();

	//TODO give each created node a serial number unique within this graph

	@Override
	public void process( Type type ) {
		cls( type.cls ).declaredAs( type );
	}

	public ArchiveNode archive( Archive archive ) {
		ArchiveNode node = archives.node( archive );
		if ( node == null ) {
			node = new ArchiveNode( this, archive );
			archives.add( node );
		}
		return node;
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

	public OverrideNode override( Method method ) {
		method = method.declaredBy( Class.NONE );
		OverrideNode node = overrides.node( method );
		if ( node == null ) {
			node = new OverrideNode( this, method );
			overrides.add( node );
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

package se.jbee.cls.graph;

import se.jbee.cls.Class;
import se.jbee.cls.Package;

public final class PackageNode
		implements Node<Package> {

	@SuppressWarnings ( "unused" )
	private final ClassGraph graph;
	private final Package key;
	public final PackageNode parent;
	public final Edges<Class, ClassNode> classes = new Edges<Class, ClassNode>();
	public final Edges<Package, PackageNode> subPackages = new Edges<Package, PackageNode>();
	public final Edges<Package, PackageNode> references = new Edges<Package, PackageNode>();
	public final Edges<Package, PackageNode> referencedBy = new Edges<Package, PackageNode>();
	public final Edges<Package, PackageNode> connects = new Edges<Package, PackageNode>();
	public final Edges<Package, PackageNode> connectedFrom = new Edges<Package, PackageNode>();

	PackageNode( ClassGraph graph, Package pkg ) {
		super();
		this.graph = graph;
		this.key = pkg;
		this.parent = pkg.hasParent()
			? graph.pkg( pkg.parent() )
			: null;
		if ( parent != null ) {
			parent.subPackages.add( this );
		}
	}

	public boolean references( Package other ) {
		return references.contains( other );
	}

	public boolean contains( Class type ) {
		return classes.contains( type );
	}

	public boolean contains( Package pkg ) {
		return subPackages.contains( pkg );
	}

	@Override
	public Package id() {
		return key;
	}

	void references( PackageNode other ) {
		references.add( other );
		other.referencedBy.add( this );
		connects( other );
	}

	private void connects( PackageNode other ) {
		if ( key.levels() > 2 && other.parent != null ) {
			parent.connects.add( other.parent );
			other.parent.connectedFrom.add( parent );
			parent.connects( other.parent );
		}
	}

	public boolean connectedTo( Package pgk ) {
		return connects.contains( pgk );
	}

	@Override
	public int hashCode() {
		return key.hashCode();
	}

	@Override
	public boolean equals( Object obj ) {
		return obj instanceof PackageNode && equalTo( (PackageNode) obj );
	}

	public boolean equalTo( PackageNode other ) {
		return key.equalTo( other.key );
	}

	@Override
	public String toString() {
		return key.toString();
	}

	public ClassNode cls( Class cls ) {
		return classes.node( cls );
	}

	public String name() {
		return key.name;
	}
}

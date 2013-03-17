package se.jbee.cls.sca.graph;

import se.jbee.cls.ref.Package;

public class PackageNode
		implements Node<Package> {

	private final ClassGraph graph;
	public final Package pkg;
	public final PackageNode parent;
	public final Edges<Package, PackageNode> subPackages = new Edges<Package, PackageNode>();
	public final Edges<Package, PackageNode> references = new Edges<Package, PackageNode>();
	public final Edges<Package, PackageNode> referencedBy = new Edges<Package, PackageNode>();
	public final Edges<Package, PackageNode> connects = new Edges<Package, PackageNode>();
	public final Edges<Package, PackageNode> connectedFrom = new Edges<Package, PackageNode>();

	PackageNode( ClassGraph graph, Package pkg ) {
		super();
		this.graph = graph;
		this.pkg = pkg;
		this.parent = pkg.hasParent()
			? graph.pkg( pkg.parent() )
			: null;
		if ( parent != null ) {
			parent.subPackages.add( this );
		}
	}

	@Override
	public Package id() {
		return pkg;
	}

	public void references( PackageNode other ) {
		references.add( other );
		other.referencedBy.add( this );
		connects( other );
	}

	private void connects( PackageNode other ) {
		if ( pkg.levels() > 2 && other.parent != null ) {
			parent.connects.add( other.parent );
			other.parent.connectedFrom.add( parent );
			parent.connects( other.parent );
		}
	}

	@Override
	public int hashCode() {
		return pkg.hashCode();
	}

	@Override
	public boolean equals( Object obj ) {
		return obj instanceof PackageNode && equalTo( (PackageNode) obj );
	}

	public boolean equalTo( PackageNode other ) {
		return pkg.equalTo( other.pkg );
	}

	@Override
	public String toString() {
		return pkg.toString();
	}
}

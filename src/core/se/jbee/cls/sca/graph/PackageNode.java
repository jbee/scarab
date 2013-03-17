package se.jbee.cls.sca.graph;

import se.jbee.cls.ref.Package;
import se.jbee.cls.ref.Class;

public class PackageNode
		implements Node<Package> {

	private final ClassGraph graph;
	public final Package pkg;
	public final PackageNode parent;
	public final Edges<Class, ClassNode> types = new Edges<Class, ClassNode>();
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

	public boolean references( Package other ) {
		return references.contains( other );
	}

	public boolean contains( Class type ) {
		return types.contains( type );
	}

	public boolean contains( Package pkg ) {
		return subPackages.contains( pkg );
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

	public boolean connectedTo( Package pgk ) {
		return connects.contains( pgk );
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

package se.jbee.jvm.graph;

import se.jbee.jvm.Archive;
import se.jbee.jvm.Class;
import se.jbee.jvm.Package;

public final class PackageNode
		implements Node<Package> {

	@SuppressWarnings ( "unused" )
	private final ClassGraph graph;
	private final Package key;
	public final int serial;
	public final PackageNode parent;
	public final Edges<Class, ClassNode> classes = new Edges<Class, ClassNode>();
	public final Edges<Package, PackageNode> subPackages = new Edges<Package, PackageNode>();
	public final Edges<Package, PackageNode> references = new Edges<Package, PackageNode>();
	public final Edges<Package, PackageNode> referencedBy = new Edges<Package, PackageNode>();
	/**
	 * The {@link #references} of this package and all its sub-packages.
	 */
	public final Edges<Package, PackageNode> connects = new Edges<Package, PackageNode>();
	/**
	 * The {@link #referencedBy} of this package and all its sub-packages.
	 */
	public final Edges<Package, PackageNode> connectedFrom = new Edges<Package, PackageNode>();
	/**
	 * The <i>external</i> packages this package {@link #references}.
	 */
	public final Edges<Package, PackageNode> dependencies = new Edges<Package, PackageNode>();
	/**
	 * The <i>external</i> packages this package is {@link #referencedBy}.
	 */
	public final Edges<Package, PackageNode> dependents = new Edges<Package, PackageNode>();
	/**
	 * The archives that contain classes with this package (or any of its sub-packages)
	 */
	public final Edges<Archive, ArchiveNode> origins = new Edges<Archive, ArchiveNode>();
	
	PackageNode( ClassGraph graph, Package pkg, int serial ) {
		super();
		this.graph = graph;
		this.key = pkg;
		this.serial = serial;
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

	@Override
	public int serial() {
		return serial;
	}

	void references( PackageNode other ) {
		references.add( other );
		other.referencedBy.add( this );
		boolean thisIsBase = graph.basePackages.isSubpackage( key );
		boolean otherIsBase = graph.basePackages.isSubpackage( other.key );
		if ( thisIsBase ) {
			if ( !otherIsBase ) {
				dependencies.add( other );
				other.dependents.add( this );
			}
		} else {
			if ( otherIsBase ) {
				dependents.add( other );
				other.dependencies.add( this );
			}
		}
		connects( other );
	}
	
	void origins(ArchiveNode archive) {
		origins.add(archive);
		if (parent != null) {
			parent.origins.add(archive);
		}
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
		return key.canonicalName();
	}

}

package se.jbee.jvm.graph;

import se.jbee.jvm.Archive;
import se.jbee.jvm.Class;
import se.jbee.jvm.Package;

public final class ArchiveNode
		implements Node<Archive> {

	@SuppressWarnings ( "unused" )
	private final ClassGraph graph;
	private final Archive key;

	public final Edges<Package, PackageNode> packages = new Edges<Package, PackageNode>();
	public final Edges<Class, ClassNode> classes = new Edges<Class, ClassNode>();

	//TODO also add links to other archives like on package and class level
	// hide edges and compute them lazy when asked for it

	ArchiveNode( ClassGraph graph, Archive key ) {
		super();
		this.graph = graph;
		this.key = key;
	}

	@Override
	public Archive id() {
		return key;
	}

	void contains( ClassNode cls ) {
		classes.add( cls );
		packages.add( cls.pkg );
	}

	@Override
	public String toString() {
		return id().toString();
	}
}

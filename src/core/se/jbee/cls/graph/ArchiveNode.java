package se.jbee.cls.graph;

import se.jbee.cls.Archive;
import se.jbee.cls.Class;
import se.jbee.cls.Package;

public final class ArchiveNode
		implements Node<Archive> {

	private final ClassGraph graph;
	private final Archive key;

	public final Edges<Package, PackageNode> packages = new Edges<Package, PackageNode>();
	public final Edges<Class, ClassNode> classes = new Edges<Class, ClassNode>();

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

}

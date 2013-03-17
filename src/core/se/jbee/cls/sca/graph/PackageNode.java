package se.jbee.cls.sca.graph;

import se.jbee.cls.ref.Package;

public class PackageNode
		implements Node {

	private final Graph graph;
	private final Package pkg;
	private final Edges<PackageNode> subPackages = new Edges<PackageNode>();
	private final Edges<PackageNode> references = new Edges<PackageNode>();
	private final Edges<PackageNode> referencedBy = new Edges<PackageNode>();

	PackageNode( Graph graph, Package pkg ) {
		super();
		this.graph = graph;
		this.pkg = pkg;
	}

	@Override
	public String id() {
		return pkg.name;
	}
}

package se.jbee.jvm.graph;

import se.jbee.jvm.Archive;
import se.jbee.jvm.Class;
import se.jbee.jvm.Package;

public final class ArchiveNode
		implements Node<Archive> {

	@SuppressWarnings ( "unused" )
	private final ClassGraph graph;
	private final Archive key;
	public final int serial;

	/**
	 * The packages included in this archive
	 */
	public final Edges<Package, PackageNode> packages = new Edges<Package, PackageNode>();
	
	/**
	 * The classes included in this archive
	 */
	public final Edges<Class, ClassNode> classes = new Edges<Class, ClassNode>();

	private final Edges<Archive, ArchiveNode> dependencies = new Edges<Archive, ArchiveNode>();

	ArchiveNode( ClassGraph graph, Archive key, int serial ) {
		super();
		this.graph = graph;
		this.key = key;
		this.serial = serial;
	}

	@Override
	public Archive id() {
		return key;
	}

	@Override
	public int serial() {
		return serial;
	}

	/**
	 * States that this archive is the origin of the class, the class is included in it. 
	 */
	void includes( ClassNode cls ) {
		classes.add( cls );
		packages.add( cls.pkg );
		cls.pkg.origins(this);
	}
	
	/**
	 * @return All archives referenced (required) by classes within this archive
	 *         (including this archive itself)
	 */
	public Edges<Archive, ArchiveNode> dependencies() {
		if (dependencies.isEmpty()) {
			dependencies.add(this); // make sure not empty
			for (ClassNode c : classes) {
				for (ClassNode a : c.references) {
					dependencies.add(a.archive);
				}
			}
		}
		return dependencies;
	}
	

	@Override
	public String toString() {
		return id().toString();
	}
}

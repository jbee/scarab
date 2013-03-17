package se.jbee.cls.sca.graph;

import java.util.IdentityHashMap;

import se.jbee.cls.ref.Class;
import se.jbee.cls.ref.Field;
import se.jbee.cls.ref.Method;
import se.jbee.cls.ref.Package;
import se.jbee.cls.ref.Type;
import se.jbee.cls.ref.Usages;
import se.jbee.cls.sca.JarProcessor;

public final class Graph
		implements JarProcessor {

	private final IdentityHashMap<String, ClassNode> classes = new IdentityHashMap<String, ClassNode>();
	private final IdentityHashMap<String, PackageNode> packages = new IdentityHashMap<String, PackageNode>();

	@Override
	public void process( Class cls, Usages usages ) {
		ClassNode node = cls( cls.type );
		node.complete( cls );
		for ( Method m : usages.methods() ) {
			node.calls( m );
		}
		for ( Method m : usages.interfaceMethods() ) {
			node.calls( m );
		}
		for ( Field f : usages.fields() ) {
			node.accesses( f );
		}
		for ( Type t : usages.types() ) {
			node.references( t );
		}
	}

	public ClassNode cls( Type type ) {
		final String key = type.name;
		ClassNode node = classes.get( key );
		if ( node == null ) {
			node = new ClassNode( this, type );
			classes.put( key, node );
		}
		return node;
	}

	public PackageNode pkg( Package pkg ) {
		final String key = pkg.name;
		PackageNode node = packages.get( key );
		if ( node == null ) {
			node = new PackageNode( this, pkg );
			packages.put( key, node );
		}
		return node;
	}
}

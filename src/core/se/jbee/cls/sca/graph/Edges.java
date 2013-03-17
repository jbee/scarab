package se.jbee.cls.sca.graph;

import java.util.IdentityHashMap;

public final class Edges<T extends Node> {

	private final IdentityHashMap<String, T> nodes = new IdentityHashMap<String, T>();

	public boolean contains( T node ) {
		return nodes.containsKey( node.id() );
	}

	void add( T node ) {
		nodes.put( node.id(), node );
	}

	@Override
	public String toString() {
		return nodes.toString();
	}
}

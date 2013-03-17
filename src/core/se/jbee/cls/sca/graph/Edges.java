package se.jbee.cls.sca.graph;

import java.util.IdentityHashMap;

public final class Edges<T extends Node> {

	private final IdentityHashMap<String, T> nodes = new IdentityHashMap<String, T>();

	public boolean contains( T other ) {
		return nodes.containsKey( other.id() );
	}
}

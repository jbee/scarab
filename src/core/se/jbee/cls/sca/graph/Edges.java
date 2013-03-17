package se.jbee.cls.sca.graph;

import java.util.HashMap;
import java.util.Set;

public final class Edges<K, T extends Node<K>> {

	private final HashMap<K, T> nodes = new HashMap<K, T>();

	public boolean contains( K key ) {
		return nodes.containsKey( key );
	}

	public Set<K> nodeIds() {
		return nodes.keySet();
	}

	void add( T node ) {
		nodes.put( node.id(), node );
	}

	@Override
	public String toString() {
		return nodes.values().toString();
	}

	public int size() {
		return nodes.size();
	}

	public T node( K key ) {
		return nodes.get( key );
	}
}

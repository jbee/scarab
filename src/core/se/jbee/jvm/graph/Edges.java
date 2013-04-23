package se.jbee.jvm.graph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public final class Edges<K, T extends Node<K>>
		implements Iterable<T> {

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

	void put( K key, T node ) {
		nodes.put( key, node );
	}

	@Override
	public String toString() {
		return nodes.values().toString();
	}

	public int count() {
		return nodes.size();
	}

	public T node( K key ) {
		return nodes.get( key );
	}

	@Override
	public Iterator<T> iterator() {
		return nodes.values().iterator();
	}

	public boolean isEmpty() {
		return nodes.isEmpty();
	}
}

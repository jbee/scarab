package se.jbee.jvm.graph;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public final class Edges<K, T extends Node<K>>
		implements Iterable<T> {

	private final Hashtable<K, T> nodes = new Hashtable<K, T>();

	public boolean contains( K key ) {
		return nodes.containsKey( key );
	}

	public Set<K> nodeIds() {
		return nodes.keySet();
	}

	void add( T node ) {
		nodes.put( node.id(), node );
	}
	
	public void addAll(Edges<K, T> others) {
		nodes.putAll(others.nodes);
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
	
	public T nodeBySimpleName( String name ) {
		for (Entry<K,T> e : nodes.entrySet()) {
			String key = e.getKey().toString();
			int i = key.lastIndexOf('.');
			if (name.equals(key.substring(i+1))) {
				return e.getValue();
			}
		}
		return null;
	}

	@Override
	public Iterator<T> iterator() {
		return nodes.values().iterator();
	}

	public boolean isEmpty() {
		return nodes.isEmpty();
	}

}

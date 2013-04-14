package se.jbee.jvm.graph;

public interface Node<T> {

	T id();

	int serial();
	//TODO abstract node for common toString/equals/hashCode
}

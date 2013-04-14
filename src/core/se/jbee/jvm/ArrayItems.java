package se.jbee.jvm;

import java.util.Arrays;
import java.util.Iterator;

public final class ArrayItems<T>
		implements Items<T> {

	public static <T> Items<T> items( T[] elements ) {
		return new ArrayItems<T>( elements );
	}

	private final T[] elements;

	private ArrayItems( T[] elements ) {
		super();
		this.elements = elements;
	}

	@Override
	public Iterator<T> iterator() {
		return Arrays.asList( elements ).iterator();
	}

	@Override
	public int count() {
		return elements.length;
	}

}

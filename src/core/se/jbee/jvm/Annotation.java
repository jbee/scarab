package se.jbee.jvm;

import java.util.Arrays;
import java.util.Iterator;

/**
 * 
 * <pre>
 * RuntimeInvisibleAnnotations, RuntimeInvisibleParameterAnnotations, 
 * RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
 * </pre>
 * 
 * @author Jan Bernitt (jan@jbee.se)
 * 
 */
public final class Annotation
		implements Items<Annotation.Element> {

	public static Annotation annotation( Class type, Element... elements ) {
		return new Annotation( type, elements );
	}

	/**
	 * <pre>
	 * @Nullable
	 * </pre>
	 */
	public final Class type;
	public final Element[] elements;

	private Annotation( Class type, Element[] elements ) {
		super();
		this.type = type;
		this.elements = elements;
	}

	@Override
	public boolean equals( Object obj ) {
		return obj instanceof Annotation && equalTo( (Annotation) obj );
	}

	@Override
	public int hashCode() {
		return type.hashCode();
	}

	public boolean equalTo( Annotation other ) {
		return type.equalTo( other.type );
	}

	@Override
	public String toString() {
		return type.toString() + ( elements.length == 0
			? ""
			: Arrays.toString( elements ) );
	}

	/**
	 * One of the key value pairs that each 'method' in an annotation class reflects.
	 * 
	 * <pre>
	 * @SuppressWarnings(<b>value="value"</b>)
	 * </pre>
	 * 
	 * @author Jan Bernitt (jan@jbee.se)
	 */
	public static final class Element {

		public static Element element( String name, ElementKind kind, Class type, Object value ) {
			return new Element( name, kind, type, value );
		}

		public final String name;
		public final ElementKind kind;
		public final Class type;
		public final Object value;

		private Element( String name, ElementKind kind, Class type, Object value ) {
			super();
			this.name = name;
			this.kind = kind;
			this.type = type;
			this.value = value;
		}

		@Override
		public boolean equals( Object obj ) {
			return obj instanceof Element && equalTo( (Element) obj );
		}

		public boolean equalTo( Element other ) {
			return name.equals( other.name );
		}

		@Override
		public int hashCode() {
			return name.hashCode();
		}

		@Override
		public String toString() {
			return type + " " + name;
		}
	}

	/**
	 * What is called 'tag' in the JVM spec.
	 * 
	 * @author Jan Bernitt (jan@jbee.se)
	 */
	public static enum ElementKind {
		PRIMITIVE,
		STRING,
		ENUM,
		CLASS,
		ANNOTATION,
	}

	@Override
	public Iterator<Element> iterator() {
		return Arrays.asList( elements ).iterator();
	}

	@Override
	public int count() {
		return elements.length;
	}
}

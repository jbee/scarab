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
	public static class Element
			implements Annotated {

		private static final Class[] NO_CLASSES = new Class[0];
		private static final Annotation[] NO_ANNOTATIONS = new Annotation[0];

		public static Element element( String name, ElementKind kind, Class type ) {
			return new Element( name, kind, type, 0, NO_CLASSES, NO_ANNOTATIONS );
		}

		public final String name;
		public final ElementKind kind;
		public final Class type;
		public final int arrayDimensions;
		/**
		 * The class values in case this is an element of kind class (or array of that).
		 */
		public final Class[] classes;
		/**
		 * The annotation values in case this is an element of kind annotation (or array of that).
		 */
		public final Annotation[] annotations;

		private Element( String name, ElementKind kind, Class type, int arrayDimensions,
				Class[] classes, Annotation[] annotations ) {
			super();
			this.name = name.intern();
			this.kind = kind;
			this.type = type;
			this.arrayDimensions = arrayDimensions;
			this.classes = classes;
			this.annotations = annotations;
		}

		public Element classes( Class... classes ) {
			return new Element( name, kind, type, classes.length == 1
				? 0
				: 1, classes, NO_ANNOTATIONS );
		}

		public Element annotations( Annotation... annotations ) {
			return new Element( name, kind, type, annotations.length == 1
				? 0
				: 1, NO_CLASSES, annotations );
		}

		@Override
		public Items<Annotation> annotations() {
			return ArrayItems.items( annotations );
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

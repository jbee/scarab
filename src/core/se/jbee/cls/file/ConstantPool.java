package se.jbee.cls.file;

import static se.jbee.cls.file.ClassDescriptor.classDescriptor;
import static se.jbee.cls.file.MethodDescriptor.methodDescriptor;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import se.jbee.cls.Class;
import se.jbee.cls.Field;
import se.jbee.cls.Items;
import se.jbee.cls.Method;
import se.jbee.cls.Modifiers;
import se.jbee.cls.reflect.References;

public final class ConstantPool
		implements References {

	private static final int TAG_COUNT = ConstantTag.values().length;
	/**
	 * The tags as defined by the JVM spec.
	 */
	private static final ConstantTag[] JVM_TAGS = new ConstantTag[13];

	static {
		JVM_TAGS[1] = ConstantTag.UTF8;
		JVM_TAGS[3] = ConstantTag.INTEGER;
		JVM_TAGS[4] = ConstantTag.FLOAT;
		JVM_TAGS[5] = ConstantTag.LONG;
		JVM_TAGS[6] = ConstantTag.DOUBLE;
		JVM_TAGS[7] = ConstantTag.CLASS;
		JVM_TAGS[8] = ConstantTag.STRING;
		JVM_TAGS[9] = ConstantTag.FIELD_REF;
		JVM_TAGS[10] = ConstantTag.METHOD_REF;
		JVM_TAGS[11] = ConstantTag.INTERFACE_METHOD_REF;
		JVM_TAGS[12] = ConstantTag.NAME_AND_TYPE;
	}

	private static final ConstantPool SHARED = new ConstantPool( 1024 );

	public static ConstantPool read( ClassInputStream in )
			throws IOException {
		final int length = in.uint16bit();
		ConstantPool cp = length < SHARED.length
			? new ConstantPool( length, SHARED.tags, SHARED.utf8, SHARED.indexes, SHARED.tagIndexes )
			: new ConstantPool( length );
		cp.init( in );
		return cp;
	}

	public static enum ConstantTag {
		UTF8, //= 1
		INTEGER, //= 3
		FLOAT, //= 4
		LONG, //= 5
		DOUBLE, //= 6
		CLASS, //= 7
		STRING, //= 8
		FIELD_REF, //= 9
		METHOD_REF, //= 10
		INTERFACE_METHOD_REF, //= 11
		NAME_AND_TYPE // = 12
		;

		public boolean isMethod() {
			return this == METHOD_REF || this == INTERFACE_METHOD_REF;
		}

	}

	private final int length;
	private final ConstantTag[] tags;
	private final String[] utf8;
	private final int[][] indexes;
	private final int[] tagCounts;
	private final int[][] tagIndexes;

	private ConstantPool( int length, ConstantTag[] tags, String[] utf8, int[][] indexes,
			int[][] tagIndexes ) {
		super();
		this.length = length;
		this.tags = tags;
		this.utf8 = utf8;
		this.indexes = indexes;
		this.tagIndexes = tagIndexes;
		this.tagCounts = new int[TAG_COUNT];
	}

	private ConstantPool( int length ) {
		this( length, new ConstantTag[length], new String[length], new int[length][2],
				new int[length][TAG_COUNT] );
	}

	private void init( ClassInputStream in )
			throws IOException {
		for ( int i = 1; i < length; ) {
			boolean occupy2 = false;
			final ConstantTag tag = JVM_TAGS[in.uint8bit()];
			final int tagIndex = tag.ordinal();
			switch ( tag ) {
				case CLASS:
					indexes[i][0] = in.uint16bit(); // name index
					break;
				case FIELD_REF:
				case METHOD_REF:
				case INTERFACE_METHOD_REF:
				case NAME_AND_TYPE: // (here are both utf8-indexes)
					indexes[i][0] = in.uint16bit(); // class index
					indexes[i][1] = in.uint16bit(); // name+type index
					break;
				case STRING:
					indexes[i][0] = in.uint16bit(); // utf8-index
					break;
				case INTEGER:
					in.int32bit(); // trash
					break;
				case FLOAT:
					in.real32bit(); // trash
					break;
				case LONG:
					in.int64bit(); // trash
					occupy2 = true;
					break;
				case DOUBLE:
					in.real64bit(); // trash
					occupy2 = true;
					break;
				case UTF8:
					utf8[i] = in.uft();
					break;
				default:
					throw new UnsupportedOperationException( "This tag is not known: " + tag );
			}
			tagIndexes[tagCounts[tagIndex]][tag.ordinal()] = i;
			tagCounts[tagIndex]++;
			tags[i++] = tag;
			if ( occupy2 ) { // double and long constants are two entries
				tags[i++] = tag;
			}
		}
	}

	public int count( ConstantTag tag ) {
		return tagCounts[tag.ordinal()];
	}

	int index( ConstantTag tag, int nr ) {
		return tagIndexes[nr][tag.ordinal()];
	}

	public int length() {
		return length;
	}

	public ConstantTag tag( int index ) {
		return tags[index];
	}

	public int index0( int index ) {
		return indexes[index][0];
	}

	public int index1( int index ) {
		return indexes[index][1];
	}

	public String utf0( int index ) {
		return utf8[index0( index )];
	}

	public String utf1( int index ) {
		return utf8[index1( index )];
	}

	public String utf( int index ) {
		return utf8[index];
	}

	public Class cls( int index ) {
		if ( tags[index] != ConstantTag.CLASS ) {
			throw new NoSuchElementException( "" );
		}
		return classDescriptor( utf0( index ) ).cls();
	}

	public Method method( int index ) {
		if ( !tags[index].isMethod() ) {
			throw new NoSuchElementException( "" );
		}
		final int i1 = index1( index );
		String declaringClass = utf0( index0( index ) );
		String name = utf0( i1 );
		MethodDescriptor declaration = methodDescriptor( utf1( i1 ) );
		final boolean interfaceMethod = tags[index] == ConstantTag.INTERFACE_METHOD_REF;
		final Modifiers declaringModifiers = interfaceMethod
			? Modifiers.UNKNOWN_INTERFACE
			: Modifiers.UNKNOWN_CLASS;
		return Method.method( classDescriptor( declaringClass ).cls( declaringModifiers ),
				Modifiers.UNKNOWN_METHOD, declaration.returnType(), name,
				declaration.parameterTypes() );
	}

	public Field field( int index ) {
		if ( tags[index] != ConstantTag.FIELD_REF ) {
			throw new NoSuchElementException( "" );
		}
		final int i1 = index1( index );
		String declaringClass = utf0( index0( index ) );
		String name = utf0( i1 );
		FieldDescriptor declaration = FieldDescriptor.fieldDescriptor( utf1( i1 ) );
		return Field.field( classDescriptor( declaringClass ).cls(), Modifiers.UNKNOWN_FIELD,
				declaration.type(), name );
	}

	@Override
	public Items<Method> calledMethods() {
		return new MethodIterator( this, ConstantTag.METHOD_REF );
	}

	@Override
	public Items<Method> calledInterfaceMethods() {
		return new MethodIterator( this, ConstantTag.INTERFACE_METHOD_REF );
	}

	@Override
	public Items<Field> accessedFields() {
		return new FieldIterator( this );
	}

	@Override
	public Items<Class> referencedClasses() {
		return new TypeIterator( this );
	}

	private static final class TypeIterator
			extends ConstantPoolIterator<Class> {

		public TypeIterator( ConstantPool cp ) {
			super( cp, ConstantTag.CLASS );
		}

		@Override
		Class reference( ConstantPool cp, int index ) {
			return cp.cls( index );
		}
	}

	private static final class MethodIterator
			extends ConstantPoolIterator<Method> {

		MethodIterator( ConstantPool cp, ConstantTag tag ) {
			super( cp, tag );
		}

		@Override
		Method reference( ConstantPool cp, int index ) {
			return cp.method( index );
		}
	}

	private static final class FieldIterator
			extends ConstantPoolIterator<Field> {

		FieldIterator( ConstantPool cp ) {
			super( cp, ConstantTag.FIELD_REF );
		}

		@Override
		Field reference( ConstantPool cp, int index ) {
			return cp.field( index );
		}
	}

	private static abstract class ConstantPoolIterator<T>
			implements Iterator<T>, Items<T> {

		private final ConstantPool cp;
		private final ConstantTag tag;
		private int index = 0;

		ConstantPoolIterator( ConstantPool cp, ConstantTag tag ) {
			super();
			this.cp = cp;
			this.tag = tag;
		}

		@Override
		public final int count() {
			return cp.count( tag );
		}

		@Override
		public final boolean hasNext() {
			return index < count();
		}

		@Override
		public final T next() {
			if ( !hasNext() ) {
				throw new NoSuchElementException();
			}
			return reference( cp, cp.index( tag, index++ ) );
		}

		abstract T reference( ConstantPool cp, int index );

		@Override
		public void remove() {
			throw new UnsupportedOperationException( "only read access!" );
		}

		@Override
		public Iterator<T> iterator() {
			if ( index > 0 ) {
				throw new IllegalStateException( "Not supported!" );
			}
			return this;
		}

	}

}

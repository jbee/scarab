package se.jbee.cls.file;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import se.jbee.cls.Items;
import se.jbee.cls.ref.Field;
import se.jbee.cls.ref.Method;
import se.jbee.cls.ref.Type;
import se.jbee.cls.ref.Usages;

public final class ConstantPool
		implements Usages {

	private static final ConstantTag[] TAGS = ConstantTag.values();

	private static final ConstantPool SHARED = new ConstantPool( 1024 );

	public static ConstantPool read( ClassInputStream in )
			throws IOException {
		final int length = in.uint16bit();
		ConstantPool cp = length < SHARED.length
			? new ConstantPool( length, SHARED.tags, SHARED.utf8, SHARED.indexes )
			: new ConstantPool( length );
		cp.init( in );
		return cp;
	}

	public static enum ConstantTag {
		_0,
		UTF8, //= 1
		_2,
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
			return this == METHOD_REF || this == ConstantTag.INTERFACE_METHOD_REF;
		}
	}

	private final int length;
	private final ConstantTag[] tags;
	private final String[] utf8;
	private final int[][] indexes;
	private final int[] tagCounts;

	private ConstantPool( int length, ConstantTag[] tags, String[] utf8, int[][] indexes ) {
		super();
		this.length = length;
		this.tags = tags;
		this.utf8 = utf8;
		this.indexes = indexes;
		this.tagCounts = new int[TAGS.length];
	}

	private ConstantPool( int length ) {
		this( length, new ConstantTag[length], new String[length], new int[length][2] );
	}

	private void init( ClassInputStream in )
			throws IOException {
		for ( int i = 1; i < length; ) {
			boolean occupy2 = false;
			final ConstantTag tag = TAGS[in.uint8bit()];
			tagCounts[tag.ordinal()]++;
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
			tags[i++] = tag;
			if ( occupy2 ) { // double and long constants are two entries
				tags[i++] = tag;
			}
		}
	}

	public int count( ConstantTag tag ) {
		return tagCounts[tag.ordinal()];
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

	public String name0( int index ) {
		return utf8[index0( index )];
	}

	public String name1( int index ) {
		return utf8[index1( index )];
	}

	public Type type( int index ) {
		if ( tags[index] != ConstantTag.CLASS ) {
			throw new NoSuchElementException( "" );
		}
		return Classfile.type( name0( index ) );
	}

	public Method method( int index ) {
		if ( !tags[index].isMethod() ) {
			throw new NoSuchElementException( "" );
		}
		final int i1 = index1( index );
		String declaringClass = name0( index0( index ) );
		String name = name0( i1 );
		String type = name1( i1 );
		int endOfParameters = type.indexOf( ')' );
		return Method.method( Classfile.type( declaringClass ),
				tags[index] == ConstantTag.INTERFACE_METHOD_REF,
				Classfile.type( type.substring( endOfParameters + 1 ) ), name,
				Classfile.types( type.substring( 1, endOfParameters ) ) );
	}

	public Field field( int index ) {
		if ( tags[index] != ConstantTag.FIELD_REF ) {
			throw new NoSuchElementException( "" );
		}
		final int i1 = index1( index );
		String declaringClass = name0( index0( index ) );
		String name = name0( i1 );
		String type = name1( i1 );
		return Field.field( Classfile.type( declaringClass ), Classfile.type( type ), name );
	}

	@Override
	public Items<Method> methods() {
		return new MethodIterator( this, ConstantTag.METHOD_REF );
	}

	@Override
	public Items<Method> interfaceMethods() {
		return new MethodIterator( this, ConstantTag.INTERFACE_METHOD_REF );
	}

	@Override
	public Items<Field> fields() {
		return new FieldIterator( this );
	}

	@Override
	public Items<Type> types() {
		return new TypeIterator( this );
	}

	private static final class TypeIterator
			extends ConstantPoolIterator<Type> {

		public TypeIterator( ConstantPool cp ) {
			super( cp, ConstantTag.CLASS );
		}

		@Override
		Type reference( ConstantPool cp, int index ) {
			return cp.type( index );
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
			nextIndex();
		}

		@Override
		public int count() {
			return cp.count( tag );
		}

		@Override
		public boolean hasNext() {
			return index < cp.length() && cp.tag( index ) == tag;
		}

		@Override
		public T next() {
			if ( !hasNext() ) {
				throw new NoSuchElementException();
			}
			T m = reference( cp, index++ );
			nextIndex();
			return m;
		}

		abstract T reference( ConstantPool cp, int index );

		private void nextIndex() {
			if ( index == 0 && count() == 0 ) {
				return;
			}
			while ( index < cp.length() && cp.tag( index ) != tag ) {
				index++;
			}
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException( "only read access!" );
		}

		@Override
		public Iterator<T> iterator() {
			return this;
		}

	}

}

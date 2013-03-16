package se.jbee.jar.bytecode;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import se.jbee.jar.FieldRef;
import se.jbee.jar.Items;
import se.jbee.jar.MethodRef;
import se.jbee.jar.TypeRef;
import se.jbee.jar.Usages;

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

	public String name( int index ) {
		return utf8[index0( index )];
	}

	public String type( int index ) {
		return utf8[index1( index )];
	}

	@Override
	public Items<MethodRef> methods() {
		return new MethodRefIterator( this, ConstantTag.METHOD_REF );
	}

	@Override
	public Items<MethodRef> interfaceMethods() {
		return new MethodRefIterator( this, ConstantTag.INTERFACE_METHOD_REF );
	}

	@Override
	public Items<FieldRef> fields() {
		return new FieldRefIterator( this );
	}

	@Override
	public Items<TypeRef> types() {
		return new TypeRefIterator( this );
	}

	private static final class TypeRefIterator
			extends RefIterator<TypeRef> {

		public TypeRefIterator( ConstantPool cp ) {
			super( cp, ConstantTag.CLASS );
		}

		@Override
		TypeRef reference( ConstantPool cp, int index ) {
			return TypeRef.typeRef( cp.name( index ) );
		}
	}

	private static final class MethodRefIterator
			extends RefIterator<MethodRef> {

		MethodRefIterator( ConstantPool cp, ConstantTag tag ) {
			super( cp, tag );
		}

		@Override
		MethodRef reference( ConstantPool cp, int index ) {
			final int i1 = cp.index1( index );
			String declaringClass = cp.name( cp.index0( index ) );
			String name = cp.name( i1 );
			String declaration = cp.type( i1 );
			return MethodRef.methodRefFromConstants( declaringClass, name, declaration );
		}
	}

	private static final class FieldRefIterator
			extends RefIterator<FieldRef> {

		FieldRefIterator( ConstantPool cp ) {
			super( cp, ConstantTag.FIELD_REF );
		}

		@Override
		FieldRef reference( ConstantPool cp, int index ) {
			final int i1 = cp.index1( index );
			String declaringClass = cp.name( cp.index0( index ) );
			String name = cp.name( i1 );
			String declaration = cp.type( i1 );
			return FieldRef.fieldRefFromConstants( declaringClass, name, declaration );
		}
	}

	private static abstract class RefIterator<T>
			implements Iterator<T>, Items<T> {

		private final ConstantPool cp;
		private final ConstantTag tag;
		private int index = 0;

		RefIterator( ConstantPool cp, ConstantTag tag ) {
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

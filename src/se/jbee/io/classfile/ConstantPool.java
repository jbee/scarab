package se.jbee.io.classfile;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class ConstantPool implements References {

	private static final ConstantTag[] TAGS = ConstantTag.values();

	public static enum ConstantTag {
		_0,
		UTF8, //= 1
		_2,
		INTEGER, //= 3
		FLOAT, //= 4
		LONG, //= 5
		DOUBLE, //= 6
		CLASS, //= 7
		STRING,//= 8
		FIELD_REF, //= 9
		METHOD_REF, //= 10
		INTERFACE_METHOD_REF, //= 11
		NAME_AND_TYPE // = 12
	}

	private final ConstantTag[] tags;
	private final String[] utf8;
	private final int[][] indexes;

	public ConstantPool(int length) {
		this.utf8 = new String[length];
		this.indexes = new int[utf8.length][2];
		this.tags = new ConstantTag[utf8.length];
	}

	private void init(ClassInputStream in) throws IOException {
		for (int i = 1; i < utf8.length;) {
			boolean occupy2 = false;
			final ConstantTag tag = TAGS[in.uint8bit()];
			switch (tag) {
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
				throw new UnsupportedOperationException("This tag is not known: "+tag);
			}
			tags[i++] = tag;
			if (occupy2) { // double and long constants are two entries
				tags[i++] = tag;
			}
		}
	}

	public static ConstantPool read(ClassInputStream in) throws IOException {
		ConstantPool cp = new ConstantPool(in.uint16bit());
		cp.init(in);
		return cp;
	}

	public int length() {
		return utf8.length;
	}

	public ConstantTag tag(int index) {
		return tags[index];
	}

	public int index0(int index) {
		return indexes[index][0];
	}
	public int index1(int index) {
		return indexes[index][1];
	}

	public String name(int index) {
		return utf8[index0(index)];
	}

	public String type(int index) {
		return utf8[index1(index)];
	}

	@Override
	public Iterable<MethodRef> methods() {
		return new MethodRefIterator(this, ConstantTag.METHOD_REF);
	}

	@Override
	public Iterable<MethodRef> interfaceMethods() {
		return new MethodRefIterator(this, ConstantTag.INTERFACE_METHOD_REF);
	}

	@Override
	public Iterable<FieldRef> fields() {
		return new FieldRefIterator(this);
	}

	@Override
	public Iterable<TypeRef> types() {
		return new TypeRefIterator(this);
	}

	private static final class TypeRefIterator extends RefIterator<TypeRef> {

		public TypeRefIterator(ConstantPool cp) {
			super(cp, ConstantTag.CLASS);
		}

		@Override
		TypeRef reference(ConstantPool cp, int index) {
			return TypeRef.typeRef(cp.name(index));
		}
	}

	private static final class MethodRefIterator extends RefIterator<MethodRef> {

		MethodRefIterator(ConstantPool cp, ConstantTag tag) {
			super(cp, tag);
		}

		@Override
		MethodRef reference(ConstantPool cp, int index) {
			final int i1 = cp.index1(index);
			String declaringClass = cp.name(cp.index0(index));
			String name = cp.name(i1);
			String declaration = cp.type(i1);
			return MethodRef.methodRefFromConstants(declaringClass, name, declaration);
		}
	}

	private static final class FieldRefIterator extends RefIterator<FieldRef> {

		FieldRefIterator(ConstantPool cp) {
			super(cp, ConstantTag.FIELD_REF);
		}

		@Override
		FieldRef reference(ConstantPool cp, int index) {
			final int i1 = cp.index1(index);
			String declaringClass = cp.name(cp.index0(index));
			String name = cp.name(i1);
			String declaration = cp.type(i1);
			return FieldRef.fieldRefFromConstants(declaringClass, name, declaration);
		}
	}

	private static abstract class RefIterator<T> implements Iterator<T>, Iterable<T> {

		private final ConstantPool cp;
		private final ConstantTag tag;
		private int index = 0;

		RefIterator(ConstantPool cp, ConstantTag tag) {
			super();
			this.cp = cp;
			this.tag = tag;
			nextIndex();
		}

		@Override
		public boolean hasNext() {
			return index < cp.length() && cp.tag(index) == tag;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			T m = reference(cp, index++);
			nextIndex();
			return m;
		}

		abstract T reference(ConstantPool cp, int index);


		private void nextIndex() {
			while (index < cp.length() && cp.tag(index) != tag) {
				index++;
			}
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("only read access!");
		}

		@Override
		public Iterator<T> iterator() {
			return this;
		}

	}

}

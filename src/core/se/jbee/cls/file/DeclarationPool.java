package se.jbee.cls.file;

import static se.jbee.cls.Modifiers.fieldModifiers;
import static se.jbee.cls.Modifiers.methodModifiers;
import static se.jbee.cls.file.MethodDescriptor.methodDescriptor;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import se.jbee.cls.Class;
import se.jbee.cls.Field;
import se.jbee.cls.Items;
import se.jbee.cls.Method;
import se.jbee.cls.reflect.Declarations;
import se.jbee.cls.reflect.FieldDeclaration;
import se.jbee.cls.reflect.MethodDeclaration;

public final class DeclarationPool
		implements Declarations {

	public static final int[][] SHARED_FIELD_INDEXES = new int[256][3];
	public static final int[][] SHARED_METHOD_INDEXES = new int[512][3];
	public static final byte[] SHARED_BYTECODE = new byte[2048];

	public static DeclarationPool read( ClassInputStream in, Class declaringClass, ConstantPool cp )
			throws IOException {
		DeclarationPool mp = new DeclarationPool( cp, declaringClass );
		mp.init( in );
		return mp;
	}

	private final ConstantPool cp;
	private final Class declaringClass;
	private int methodCount;
	private int[][] methodsMND;
	private int fieldCount;
	private int[][] fieldsMND;

	private DeclarationPool( ConstantPool cp, Class declaringClass ) {
		super();
		this.cp = cp;
		this.declaringClass = declaringClass;
	}

	private void init( ClassInputStream in )
			throws IOException {
		fieldCount = in.uint16bit();
		fieldsMND = SHARED_FIELD_INDEXES.length <= fieldCount
			? SHARED_FIELD_INDEXES
			: new int[fieldCount][3];
		for ( int i = 0; i < fieldCount; i++ ) {
			fieldsMND[i][0] = in.uint16bit();
			fieldsMND[i][1] = in.uint16bit();
			fieldsMND[i][2] = in.uint16bit();
			readAttributes( in, cp );
		}
		methodCount = in.uint16bit();
		methodsMND = SHARED_METHOD_INDEXES.length <= methodCount
			? SHARED_METHOD_INDEXES
			: new int[methodCount][3];
		for ( int i = 0; i < methodCount; i++ ) {
			methodsMND[i][0] = in.uint16bit();
			methodsMND[i][1] = in.uint16bit();
			methodsMND[i][2] = in.uint16bit();
			readAttributes( in, cp );
		}
	}

	private static void readAttributes( ClassInputStream in, ConstantPool cp )
			throws IOException {
		int attributeCount = in.uint16bit();
		for ( int a = 0; a < attributeCount; a++ ) {
			readAttribute( cp, in );
		}
	}

	private static void readAttribute( ConstantPool cp, ClassInputStream in )
			throws IOException {
		String name = cp.utf( in.uint16bit() );
		int length = in.int32bit();
		if ( "Code".equals( name ) ) {
			int maxStack = in.uint16bit();
			int maxLocals = in.uint16bit();
			int codeLength = in.int32bit();
			byte[] code = codeLength <= SHARED_BYTECODE.length
				? SHARED_BYTECODE
				: new byte[codeLength];
			in.bytecode( code, codeLength );
			Bytecode bytecode = new Bytecode( cp, code, codeLength );
			bytecode.read();
			int exceptionsCount = in.uint16bit();
			for ( int i = 0; i < exceptionsCount; i++ ) {
				readException( cp, in );
			}
			readAttributes( in, cp );
		} else {
			in.skipBytes( length );
		}
	}

	private static void readException( ConstantPool cp, ClassInputStream in )
			throws IOException {
		int startPC = in.uint16bit();
		int endPC = in.uint16bit();
		int handlerPC = in.uint16bit();
		int catchType = in.uint16bit(); // UTF8 in cp
	}

	public Method method( int index ) {
		MethodDescriptor d = methodDescriptor( cp.utf( methodsMND[index][2] ) );
		return Method.method( declaringClass, methodModifiers( methodsMND[index][0] ),
				d.returnType(), cp.utf( methodsMND[index][1] ), d.parameterTypes() );
	}

	public Field field( int index ) {
		FieldDescriptor d = FieldDescriptor.fieldDescriptor( cp.utf( fieldsMND[index][2] ) );
		return Field.field( declaringClass, fieldModifiers( fieldsMND[index][0] ), d.type(),
				cp.utf( fieldsMND[index][1] ) );
	}

	@Override
	public Items<MethodDeclaration> declaredMethods() {
		return new MethodIterator( this, methodCount );
	}

	@Override
	public Items<FieldDeclaration> declaredFields() {
		return new FieldIterator( this, fieldCount );
	}

	private static final class FieldIterator
			extends DeclarationPoolIterator<FieldDeclaration> {

		FieldIterator( DeclarationPool dp, int count ) {
			super( dp, count );
		}

		@Override
		FieldDeclaration declaration( DeclarationPool dp, int index ) {
			return new FieldDeclaration( dp.field( index ) );
		}

	}

	private static final class MethodIterator
			extends DeclarationPoolIterator<MethodDeclaration> {

		MethodIterator( DeclarationPool dp, int count ) {
			super( dp, count );
		}

		@Override
		MethodDeclaration declaration( DeclarationPool dp, int index ) {
			return new MethodDeclaration( dp.method( index ) );
		}

	}

	private static abstract class DeclarationPoolIterator<T>
			implements Iterator<T>, Items<T> {

		private final DeclarationPool dp;
		private final int count;
		private int index = 0;

		DeclarationPoolIterator( DeclarationPool dp, int count ) {
			super();
			this.dp = dp;
			this.count = count;
		}

		@Override
		public final Iterator<T> iterator() {
			return this;
		}

		@Override
		public final int count() {
			return count;
		}

		@Override
		public final boolean hasNext() {
			return index < count;
		}

		@Override
		public final T next() {
			if ( !hasNext() ) {
				throw new NoSuchElementException();
			}
			return declaration( dp, index++ );
		}

		abstract T declaration( DeclarationPool dc, int index );

		@Override
		public final void remove() {
			throw new UnsupportedOperationException( "read only!" );
		}

	}
}

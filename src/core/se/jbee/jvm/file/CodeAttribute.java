package se.jbee.jvm.file;

import static java.util.Arrays.copyOf;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

import se.jbee.jvm.Class;
import se.jbee.jvm.Code;
import se.jbee.jvm.Field;
import se.jbee.jvm.Items;
import se.jbee.jvm.Method;
import se.jbee.jvm.file.Bytecode.Opcode;
import se.jbee.jvm.reflect.MethodReferences;

public final class CodeAttribute {

	public static MethodReferences emptyMethod( ConstantPool cp ) {
		return new OpcodeReferences( new Opcode[0], new int[0], cp );
	}

	public static CodeAttribute read( ConstantPool cp, ClassInputStream in )
			throws IOException {
		int maxStack = in.uint16bit(); // maxStack
		int maxLocals = in.uint16bit(); // maxLocals
		int byteCount = in.int32bit();
		byte[] code = byteCount <= SHARED_BYTECODE.length
			? SHARED_BYTECODE
			: new byte[byteCount];
		in.bytecode( code, byteCount );
		int exceptionsCount = in.uint16bit();
		Code c = Code.code( maxStack, maxLocals, byteCount, exceptionsCount );
		for ( int i = 0; i < exceptionsCount; i++ ) {
			readException( cp, in );
		}
		return new CodeAttribute( cp, new Bytecode( ByteBuffer.wrap( code, 0, byteCount ) ), c );
	}

	private static final byte[] SHARED_BYTECODE = new byte[2048];

	private static final int[] cpIndexBuffer = new int[4096];
	private static final Opcode[] opcodeBuffer = new Opcode[4096];

	private final ConstantPool cp;
	private final Code code;
	private final Bytecode bytecode;

	public CodeAttribute( ConstantPool cp, Bytecode bytecode, Code code ) {
		super();
		this.cp = cp;
		this.bytecode = bytecode;
		this.code = code;
	}

	private static void readException( ConstantPool cp, ClassInputStream in )
			throws IOException {
		int startPC = in.uint16bit();
		int endPC = in.uint16bit();
		int handlerPC = in.uint16bit();
		int catchType = in.uint16bit(); // UTF8 in cp
	}

	public Code code() {
		return code;
	}

	public MethodReferences references() {
		int c = 0;
		while ( bytecode.hasBytes() ) {
			Opcode opcode = bytecode.opcode();
			if ( opcode.cpIndex ) {
				opcodeBuffer[c] = opcode;
				cpIndexBuffer[c++] = bytecode.index();
			}
			skip( opcode );
		}
		return new OpcodeReferences( copyOf( opcodeBuffer, c ), copyOf( cpIndexBuffer, c ), cp );
	}

	public void skip( Opcode opcode ) {
		int bytes = opcode.skipBytes;
		if ( bytes >= 0 ) {
			bytecode.skipBytes( bytes );
		} else if ( opcode == Opcode.wide ) {
			Opcode wide = bytecode.opcode();
			bytecode.skipBytes( wide == Opcode.iinc
				? 5
				: 3 );
		} else {
			bytecode.alignToEven32BitPosition();
			bytecode.skipBytes( 4 ); // 4 bytes default value
			if ( opcode == Opcode.tableswitch ) {
				int low = bytecode.int32();
				int high = bytecode.int32();
				if (low < 0 || low > high) {
					bytecode.skipToEnd(); // FIXME could be 2 things: wrong bytecode or bug in bytecode processing before so this isn't really a tableswitch or padding is wrong -> skip over rest of code
					return;
				}
				bytecode.skipBytes( ( high - low + 1 ) << 2 ); // x 2 bytes 
			} else if ( opcode == Opcode.lookupswitch ) {
				int pairs = bytecode.int32();
				bytecode.skipBytes( pairs << 3 ); // x 2 x 4 bytes / pair
			} else {
				throw new UnsupportedOperationException( "Unknown opcode: " + opcode );
			}
		}
	}

	private static final class OpcodeReferences
			implements MethodReferences {

		static final EnumSet<Opcode> METHOD_CALLS = EnumSet.of( Opcode.invokedynamic,
				Opcode.invokespecial, Opcode.invokestatic, Opcode.invokevirtual,
				Opcode.invokeinterface );

		static final EnumSet<Opcode> FIELD_ACCESS = EnumSet.of( Opcode.putfield, Opcode.putstatic,
				Opcode.getfield, Opcode.getstatic );

		private final Opcode[] opcodes;
		private final int[] cpIndexes;
		private final ConstantPool cp;

		OpcodeReferences( Opcode[] opcodes, int[] cpIndexes, ConstantPool cp ) {
			super();
			this.opcodes = opcodes;
			this.cpIndexes = cpIndexes;
			this.cp = cp;
		}

		@Override
		public Items<Method> calledMethods() {
			return new OpcodeMethodIterator( METHOD_CALLS, this );
		}

		@Override
		public Items<Field> accessedFields() {
			return new OpcodeFieldIterator( FIELD_ACCESS, this );
		}

		@Override
		public Items<Class> constructedClasses() {
			return new OpcodeClassIterator( EnumSet.of( Opcode.new_ ), this );
		}

		public Opcode opcode( int index ) {
			return opcodes[index];
		}

		public Method method( int index ) {
			return cp.method( cpIndexes[index] );
		}

		public Field field( int index ) {
			return cp.field( cpIndexes[index] );
		}

		public Class cls( int index ) {
			return cp.cls( cpIndexes[index] );
		}

		public int count() {
			return opcodes.length;
		}

	}

	private static final class OpcodeMethodIterator
			extends OpcodeReferencesIterator<Method> {

		OpcodeMethodIterator( EnumSet<Opcode> opcodes, OpcodeReferences references ) {
			super( opcodes, references );
		}

		@Override
		Method referenced( OpcodeReferences references, int index ) {
			return references.method( index );
		}

	}

	private static final class OpcodeFieldIterator
			extends OpcodeReferencesIterator<Field> {

		OpcodeFieldIterator( EnumSet<Opcode> opcodes, OpcodeReferences references ) {
			super( opcodes, references );
		}

		@Override
		Field referenced( OpcodeReferences references, int index ) {
			return references.field( index );
		}

	}

	private static final class OpcodeClassIterator
			extends OpcodeReferencesIterator<Class> {

		OpcodeClassIterator( EnumSet<Opcode> opcodes, OpcodeReferences references ) {
			super( opcodes, references );
		}

		@Override
		Class referenced( OpcodeReferences references, int index ) {
			return references.cls( index );
		}

	}

	private static abstract class OpcodeReferencesIterator<T>
			implements Iterator<T>, Items<T> {

		private final EnumSet<Opcode> opcodes;
		private final OpcodeReferences references;
		private final int count;

		private int index;
		private int done;

		OpcodeReferencesIterator( EnumSet<Opcode> opcodes, OpcodeReferences references ) {
			super();
			this.opcodes = opcodes;
			this.references = references;
			this.count = initCount();
		}

		private int initCount() {
			int c = 0;
			for ( int i = 0; i < references.count(); i++ ) {
				if ( opcodes.contains( references.opcode( i ) ) ) {
					c++;
				}
			}
			return c;
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
			return done < count;
		}

		@Override
		public final T next() {
			if ( !hasNext() ) {
				throw new NoSuchElementException();
			}
			while ( !opcodes.contains( references.opcode( index ) ) ) {
				index++;
			}
			done++;
			return referenced( references, index++ );
		}

		abstract T referenced( OpcodeReferences references, int index );

		@Override
		public final void remove() {
			throw new UnsupportedOperationException( "read only!" );
		}
	}

}

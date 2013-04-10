package se.jbee.cls.file;

import static java.util.Arrays.copyOf;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.NoSuchElementException;

import se.jbee.cls.Class;
import se.jbee.cls.Field;
import se.jbee.cls.Items;
import se.jbee.cls.Method;
import se.jbee.cls.file.Bytecode.Opcode;
import se.jbee.cls.reflect.References;

public final class CodeAttribute {

	public static References emptyMethod( ConstantPool cp ) {
		return new OpcodeReferences( new Opcode[0], new int[0], cp );
	}

	private static final int[] cpIndexBuffer = new int[512];
	private static final Opcode[] opcodeBuffer = new Opcode[512];

	private final Bytecode code;
	private final ConstantPool cp;

	public CodeAttribute( ConstantPool cp, Bytecode code ) {
		super();
		this.cp = cp;
		this.code = code;
	}

	public References read() {
		int c = 0;
		while ( code.hasBytes() ) {
			Opcode opcode = code.opcode();
			if ( opcode.cpIndex ) {
				opcodeBuffer[c] = opcode;
				cpIndexBuffer[c++] = code.index();
			}
			skip( opcode );
		}
		return new OpcodeReferences( copyOf( opcodeBuffer, c ), copyOf( cpIndexBuffer, c ), cp );
	}

	public void skip( Opcode opcode ) {
		int bytes = opcode.skipBytes;
		if ( bytes >= 0 ) {
			code.skipBytes( bytes );
		} else if ( opcode == Opcode.wide ) {
			Opcode wide = code.opcode();
			code.skipBytes( wide == Opcode.iinc
				? 5
				: 3 );
		} else {
			code.alignToEven32BitPosition();
			code.skipBytes( 4 ); // 4 bytes default value
			if ( opcode == Opcode.tableswitch ) {
				int low = code.int32();
				int high = code.int32();
				code.skipBytes( ( high - low + 1 ) << 2 ); // x 2 bytes 
			} else if ( opcode == Opcode.lookupswitch ) {
				int pairs = code.int32();
				code.skipBytes( pairs << 3 ); // x 2 x 4 bytes / pair
			} else {
				throw new UnsupportedOperationException( "Unknown opcode: " + opcode );
			}
		}
	}

	private static final class OpcodeReferences
			implements References {

		static final EnumSet<Opcode> METHOD_CALLS = EnumSet.of( Opcode.invokedynamic,
				Opcode.invokespecial, Opcode.invokestatic, Opcode.invokevirtual );

		static final EnumSet<Opcode> INTERFACE_METHOD_CALLS = EnumSet.of( Opcode.invokeinterface );

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
		public Items<Method> calledInterfaceMethods() {
			return new OpcodeMethodIterator( INTERFACE_METHOD_CALLS, this );
		}

		@Override
		public Items<Field> accessedFields() {
			return new OpcodeFieldIterator( FIELD_ACCESS, this );
		}

		@Override
		public Items<Class> referencedClasses() {
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
			return referenced( references, index );
		}

		abstract T referenced( OpcodeReferences references, int index );

		@Override
		public final void remove() {
			throw new UnsupportedOperationException( "read only!" );
		}
	}
}

package se.jbee.cls.file;

import static java.util.Arrays.copyOf;
import se.jbee.cls.Class;
import se.jbee.cls.Field;
import se.jbee.cls.Items;
import se.jbee.cls.Method;
import se.jbee.cls.file.Bytecode.Opcode;
import se.jbee.cls.reflect.References;

public final class CodeAttribute {

	private static final int[] indexBuffer = new int[512];
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
				indexBuffer[c++] = code.index();
			}
			skip( opcode );
		}
		return new OpcodeReferences( copyOf( opcodeBuffer, c ), copyOf( indexBuffer, c ), cp );
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

		private final Opcode[] opcodes;
		private final int[] indexes;
		private final ConstantPool cp;

		OpcodeReferences( Opcode[] opcodes, int[] indexes, ConstantPool cp ) {
			super();
			this.opcodes = opcodes;
			this.indexes = indexes;
			this.cp = cp;
		}

		@Override
		public Items<Method> calledMethods() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Items<Method> calledInterfaceMethods() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Items<Field> accessedFields() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Items<Class> referencedClasses() {
			// TODO Auto-generated method stub
			return null;
		}

	}
}

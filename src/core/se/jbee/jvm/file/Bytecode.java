package se.jbee.jvm.file;

import static se.jbee.jvm.file.Bytecode.OpcodeFlag.ARRAY;
import static se.jbee.jvm.file.Bytecode.OpcodeFlag.CLASS;
import static se.jbee.jvm.file.Bytecode.OpcodeFlag.FIELD;
import static se.jbee.jvm.file.Bytecode.OpcodeFlag.INDEX;
import static se.jbee.jvm.file.Bytecode.OpcodeFlag.INSTANCE_OF;
import static se.jbee.jvm.file.Bytecode.OpcodeFlag.METHOD;
import static se.jbee.jvm.file.Bytecode.OpcodeFlag.NEW;
import static se.jbee.jvm.file.Bytecode.OpcodeFlag.STATIC;
import static se.jbee.jvm.file.Bytecode.OpcodeFlag.THROW;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.EnumSet;

public final class Bytecode {

	static final Opcode[] OPCODES = decimalOrderCodes();

	private static Opcode[] decimalOrderCodes() {
		Opcode[] res = new Opcode[256];
		for ( Opcode opcode : Opcode.values() ) {
			res[opcode.opcodeDecimal] = opcode;
		}
		return res;
	}

	static enum OpcodeFlag {
		INDEX,
		CLASS,
		FIELD,
		METHOD,
		STATIC,
		NEW,
		ARRAY,
		INSTANCE_OF,
		CAST,
		THROW
	}

	static enum Opcode {

		aaload( 50, 0 ),
		aastore( 83, 0 ),
		aconst_null( 1, 0 ),
		aload( 25, 1 ),
		aload_0( 42, 0 ),
		aload_1( 43, 0 ),
		aload_2( 44, 0 ),
		aload_3( 45, 0 ),
		anewarray( 189, 2, CLASS, INDEX ),
		areturn( 176, 0 ),
		arraylength( 190, 0 ),
		astore( 58, 1 ),
		astore_0( 75, 0 ),
		astore_1( 76, 0 ),
		astore_2( 77, 0 ),
		astore_3( 78, 0 ),
		athrow( 191, 0, THROW ),
		baload( 51, 0 ),
		bastore( 84, 0 ),
		bipush( 16, 1 ),
		caload( 52, 0 ),
		castore( 85, 0 ),
		checkcast( 192, 2, CLASS, INDEX ),
		dadd( 99, 0 ),
		daload( 49, 0 ),
		dastore( 82, 0 ),
		dcmpg( 152, 0 ),
		dcmpl( 151, 0 ),
		dconst_0( 14, 0 ),
		dconst_1( 15, 0 ),
		ddiv( 111, 0 ),
		dload( 24, 1 ),
		dload_0( 38, 0 ),
		dload_1( 39, 0 ),
		dload_2( 40, 0 ),
		dload_3( 41, 0 ),
		dmul( 107, 0 ),
		dneg( 119, 0 ),
		drem( 115, 0 ),
		dreturn( 175, 0 ),
		dstore( 57, 1 ),
		dstore_0( 71, 0 ),
		dstore_1( 72, 0 ),
		dstore_2( 73, 0 ),
		dstore_3( 74, 0 ),
		dsub( 103, 0 ),
		dup( 89, 0 ),
		dup_x1( 90, 0 ),
		dup_x2( 91, 0 ),
		dup2( 92, 0 ),
		dup2_x1( 93, 0 ),
		dup2_x2( 94, 0 ),
		fadd( 98, 0 ),
		faload( 48, 0 ),
		fastore( 81, 0 ),
		fcmpg( 150, 0 ),
		fcmpl( 149, 0 ),
		fconst_0( 11, 0 ),
		fconst_1( 12, 0 ),
		fconst_2( 13, 0 ),
		fdiv( 110, 0 ),
		fload( 23, 1 ),
		fload_0( 34, 0 ),
		fload_1( 35, 0 ),
		fload_2( 36, 0 ),
		fload_3( 37, 0 ),
		fmul( 106, 0 ),
		fneg( 118, 0 ),
		frem( 114, 0 ),
		freturn( 174, 0 ),
		fstore( 56, 1 ),
		fstore_0( 67, 0 ),
		fstore_1( 68, 0 ),
		fstore_2( 69, 0 ),
		fstore_3( 70, 0 ),
		fsub( 102, 0 ),
		getfield( 180, 2, FIELD, INDEX ),
		getstatic( 178, 2, FIELD, INDEX, STATIC ),
		goto_( 167, 2 ),
		goto_w( 200, 4 ),
		i2l( 133, 0 ),
		i2f( 134, 0 ),
		i2d( 135, 0 ),
		l2i( 136, 0 ),
		l2f( 137, 0 ),
		l2d( 138, 0 ),
		f2i( 139, 0 ),
		f2l( 140, 0 ),
		f2d( 141, 0 ),
		d2i( 142, 0 ),
		d2l( 143, 0 ),
		d2f( 144, 0 ),
		i2b( 145, 0 ),
		i2c( 146, 0 ),
		i2s( 147, 0 ),
		iadd( 96, 0 ),
		iaload( 46, 0 ),
		iand( 126, 0 ),
		iastore( 79, 0 ),
		iconst_m1( 2, 0 ),
		iconst_0( 3, 0 ),
		iconst_1( 4, 0 ),
		iconst_2( 5, 0 ),
		iconst_3( 6, 0 ),
		iconst_4( 7, 0 ),
		iconst_5( 8, 0 ),
		idiv( 108, 0 ),
		if_acmpeq( 165, 2 ),
		if_acmpne( 166, 2 ),
		if_icmpeq( 159, 2 ),
		if_icmpne( 160, 2 ),
		if_icmplt( 161, 2 ),
		if_icmpge( 162, 2 ),
		if_icmpgt( 163, 2 ),
		if_icmple( 164, 2 ),
		ifeq( 153, 2 ),
		ifne( 154, 2 ),
		iflt( 155, 2 ),
		ifge( 156, 2 ),
		ifgt( 157, 2 ),
		ifle( 158, 2 ),
		ifnonnull( 199, 2 ),
		ifnull( 198, 2 ),
		iinc( 132, 2 ),
		iload( 21, 1 ),
		iload_0( 26, 0 ),
		iload_1( 27, 0 ),
		iload_2( 28, 0 ),
		iload_3( 29, 0 ),
		imul( 104, 0 ),
		ineg( 116, 0 ),
		instanceof_( 193, 2, CLASS, INDEX, INSTANCE_OF ),
		invokedynamic( 186, 4, METHOD, INDEX ),
		invokeinterface( 185, 4, METHOD, INDEX ),
		invokespecial( 183, 2, METHOD, INDEX ),
		invokestatic( 184, 2, METHOD, INDEX, STATIC ),
		invokevirtual( 182, 2, METHOD, INDEX ),
		ior( 128, 0 ),
		irem( 112, 0 ),
		ireturn( 172, 0 ),
		ishl( 120, 0 ),
		ishr( 122, 0 ),
		istore( 54, 1 ),
		istore_0( 59, 0 ),
		istore_1( 60, 0 ),
		istore_2( 61, 0 ),
		istore_3( 62, 0 ),
		isub( 100, 0 ),
		iushr( 124, 0 ),
		ixor( 130, 0 ),
		jsr( 168, 2 ),
		jsr_w( 201, 4 ),
		ladd( 97, 0 ),
		laload( 47, 0 ),
		land( 127, 0 ),
		lastore( 80, 0 ),
		lcmp( 148, 0 ),
		lconst_0( 9, 0 ),
		lconst_1( 10, 0 ),
		ldc( 18, 1 ),
		ldc_w( 19, 2 ),
		ldc2_w( 20, 2 ),
		ldiv( 109, 0 ),
		lload( 22, 1 ),
		lload_0( 30, 0 ),
		lload_1( 31, 0 ),
		lload_2( 32, 0 ),
		lload_3( 33, 0 ),
		lmul( 105, 0 ),
		lneg( 117, 0 ),
		lookupswitch( 171, -1 ), //4+
		lor( 129, 0 ),
		lrem( 113, 0 ),
		lreturn( 173, 0 ),
		lshl( 121, 0 ),
		lshr( 123, 0 ),
		lstore( 55, 1 ),
		lstore_0( 63, 0 ),
		lstore_1( 64, 0 ),
		lstore_2( 65, 0 ),
		lstore_3( 66, 0 ),
		lsub( 101, 0 ),
		lushr( 125, 0 ),
		lxor( 131, 0 ),
		monitorenter( 194, 0 ),
		monitorexit( 195, 0 ),
		multianewarray( 197, 3, CLASS, INDEX, NEW, ARRAY ),
		new_( 187, 2, CLASS, INDEX, NEW ), // followed by invokespecial for the <init> method
		newarray( 188, 1 ),
		nop( 0, 0 ),
		pop( 87, 0 ),
		pop2( 88, 0 ),
		putfield( 181, 2, FIELD, INDEX ),
		putstatic( 179, 2, FIELD, INDEX, STATIC ),
		ret( 169, 1 ),
		return_( 177, 0 ),
		saload( 53, 0 ),
		sastore( 86, 0 ),
		sipush( 17, 2 ),
		swap( 95, 0 ),
		tableswitch( 170, -1 ), // 4+
		wide( 196, -1 ), // 3/5
		breakpoint( 202, 0 ),
		impdep1( 254, 0 ),
		impdep2( 255, 0 );

		/**
		 * The opcode in decimal
		 */
		public final int opcodeDecimal;
		/**
		 * The count of bytes that are following the opcode byte
		 */
		public final int argBytes;
		/**
		 * The count of bytes that needs to be skipped
		 */
		public final int skipBytes;

		/**
		 * True, in case the opcode uses a index into the CP. For such a index always 2 bytes of the
		 * {@link #argBytes} are used.
		 */
		public final boolean cpIndex;

		private final EnumSet<OpcodeFlag> flags;

		private Opcode( int opcodeDecimal, int argBytes ) {
			this( opcodeDecimal, argBytes, (OpcodeFlag[]) null );
		}

		private Opcode( int opcodeDecimal, int argBytes, OpcodeFlag... flags ) {
			this.opcodeDecimal = opcodeDecimal;
			this.argBytes = argBytes;
			this.flags = flags == null
				? EnumSet.noneOf( OpcodeFlag.class )
				: EnumSet.of( flags[0], flags );
			this.cpIndex = this.flags.contains( INDEX );
			this.skipBytes = argBytes - ( this.cpIndex
				? 2
				: 0 );
		}

		public boolean has( OpcodeFlag flag ) {
			return flags.contains( flag );
		}

		public boolean cpClass() {
			return has( CLASS );
		}

		public boolean cpMethod() {
			return has( METHOD );
		}

		public boolean cpField() {
			return has( FIELD );
		}
	}

	private final ByteBuffer code;

	public Bytecode( ByteBuffer code ) {
		super();
		this.code = code;
		this.code.order( ByteOrder.BIG_ENDIAN );
	}

	public Opcode opcode() {
		return OPCODES[uint8()];
	}

	private int uint8() {
		byte v = code.get();
		return v < 0
			? v & 0xFF
			: v;
	}

	public int index() {
		return ( uint8() << 8 ) + uint8();
	}

	public void skipBytes( int count ) {
		code.position( code.position() + count );
	}

	public boolean hasBytes() {
		return code.hasRemaining();
	}

	public int position() {
		return code.position();
	}

	public void alignToEven32BitPosition() {
		int pad = code.position() & 3;
		if ( pad > 0 ) {
			skipBytes( 4 - pad ); // 0-3 bytes padding
		}
	}

	public int int32() {
		return code.getInt();
	}

	public void skipToEnd() {
		code.position(code.limit());
	}

}

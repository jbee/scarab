package se.jbee.cls;

public final class Code {

	public static Code code( int maxStack, int maxLocals, int byteCount, int exceptionsCount ) {
		return new Code( maxStack, maxLocals, byteCount, exceptionsCount );
	}

	public final int maxStack;
	public final int maxLocals;
	public final int byteCount;
	public final int exceptionsCount;

	private Code( int maxStack, int maxLocals, int byteCount, int exceptionsCount ) {
		super();
		this.maxStack = maxStack;
		this.maxLocals = maxLocals;
		this.byteCount = byteCount;
		this.exceptionsCount = exceptionsCount;
	}

}

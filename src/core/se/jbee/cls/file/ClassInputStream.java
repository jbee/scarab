package se.jbee.cls.file;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class ClassInputStream {

	private final DataInputStream in;

	public ClassInputStream( InputStream in ) {
		super();
		this.in = in instanceof DataInputStream
			? (DataInputStream) in
			: new DataInputStream( in );
	}

	public int uint8bit()
			throws IOException {
		return in.readUnsignedByte();
	}

	public int uint16bit()
			throws IOException {
		return in.readUnsignedShort();
	}

	public int int32bit()
			throws IOException {
		return in.readInt();
	}

	public long int64bit()
			throws IOException {
		return in.readLong();
	}

	public String uft()
			throws IOException {
		return in.readUTF();
	}

	public float real32bit()
			throws IOException {
		return in.readFloat();
	}

	public double real64bit()
			throws IOException {
		return in.readDouble();
	}

	public void skipBytes( int n )
			throws IOException {
		in.skipBytes( n );
	}

}

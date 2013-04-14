package se.jbee.jvm.file;

public class AttributeInputStream {

	private final ClassInputStream in;
	private final int length;

	private AttributeInputStream( ClassInputStream in, int length ) {
		super();
		this.in = in;
		this.length = length;
	}

}

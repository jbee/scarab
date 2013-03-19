package se.jbee.cls.file;

import static se.jbee.cls.file.MethodDeclaration.methodDeclaration;

import java.io.IOException;

import se.jbee.cls.ref.Class;
import se.jbee.cls.ref.Method;
import se.jbee.cls.ref.Modifiers;

public final class MethodPool {

	public static MethodPool read( ClassInputStream in, Class declaringClass, ConstantPool cp )
			throws IOException {
		int length = in.uint16bit();
		MethodPool mp = new MethodPool( cp, length, declaringClass, new int[length][3] );
		mp.init( in );
		return mp;
	}

	private final ConstantPool cp;
	private final int length;
	private final Class declaringClass;
	private final int[][] modifiersNameIndexDeclarationIndex;

	private MethodPool( ConstantPool cp, int length, Class declaringClass,
			int[][] modifiersNameIndexDeclarationIndex ) {
		super();
		this.cp = cp;
		this.length = length;
		this.declaringClass = declaringClass;
		this.modifiersNameIndexDeclarationIndex = modifiersNameIndexDeclarationIndex;
	}

	private void init( ClassInputStream in )
			throws IOException {
		for ( int i = 0; i < length; i++ ) {
			modifiersNameIndexDeclarationIndex[i][0] = in.uint16bit();
			modifiersNameIndexDeclarationIndex[i][1] = in.uint16bit();
			modifiersNameIndexDeclarationIndex[i][1] = in.uint16bit();
			//readAttributes( in, cp );
		}
	}

	public Method method( int index ) {
		MethodDeclaration d = methodDeclaration( cp.utf( modifiersNameIndexDeclarationIndex[index][2] ) );
		return Method.method( declaringClass,
				Modifiers.methodModifiers( modifiersNameIndexDeclarationIndex[index][0] ),
				d.returnType(), cp.utf( modifiersNameIndexDeclarationIndex[index][1] ),
				d.parameterTypes() );
	}
}

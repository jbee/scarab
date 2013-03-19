package se.jbee.cls.file;

import static se.jbee.cls.file.MethodDeclaration.methodDeclaration;
import se.jbee.cls.ref.Class;
import se.jbee.cls.ref.Method;
import se.jbee.cls.ref.Modifiers;

public final class MethodPool {

	private final ConstantPool cp;
	private final Class declaringClass;
	private final int[][] modifiersNameIndexDeclarationIndex;

	private MethodPool( ConstantPool cp, Class declaringClass,
			int[][] modifiersNameIndexDeclarationIndex ) {
		super();
		this.cp = cp;
		this.declaringClass = declaringClass;
		this.modifiersNameIndexDeclarationIndex = modifiersNameIndexDeclarationIndex;
	}

	public Method method( int index ) {
		MethodDeclaration d = methodDeclaration( cp.utf( modifiersNameIndexDeclarationIndex[index][2] ) );
		return Method.method( declaringClass,
				Modifiers.methodModifiers( modifiersNameIndexDeclarationIndex[index][0] ),
				d.returnType(), cp.utf( modifiersNameIndexDeclarationIndex[index][1] ),
				d.parameterTypes() );
	}
}

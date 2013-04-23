package se.jbee.jvm.file;

import static se.jbee.jvm.file.ClassDescriptor.classDescriptor;

import java.io.IOException;

import se.jbee.jvm.Annotation;
import se.jbee.jvm.Annotation.Element;
import se.jbee.jvm.Annotation.ElementKind;
import se.jbee.jvm.Class;
import se.jbee.jvm.Field;
import se.jbee.jvm.Modifiers;

public class AnnotationAttribute {

	public static Annotation[] readAnnotations( ConstantPool cp, ClassInputStream in )
			throws IOException {
		int num = in.uint16bit();
		Annotation[] annotations = new Annotation[num];
		for ( int i = 0; i < num; i++ ) {
			annotations[i] = readAnnotation( cp, in );
		}
		return annotations;
	}

	private static Annotation readAnnotation( ConstantPool cp, ClassInputStream in )
			throws IOException {
		String type = cp.utf( in.uint16bit() );
		int num = in.uint16bit();
		Element[] elements = new Element[num];
		for ( int i = 0; i < num; i++ ) {
			String name = cp.utf( in.uint16bit() );
			elements[i] = readElementValue( name, cp, in );
		}
		return Annotation.annotation( classDescriptor( type ).cls( Modifiers.UNKNOWN_ANNOTATION ),
				elements );
	}

	private static Element readElementValue( String name, ConstantPool cp, ClassInputStream in )
			throws IOException {
		int tag = in.uint8bit();
		if ( 'e' == tag ) { // enum
			Class type = classDescriptor( cp.utf( in.uint16bit() ) ).cls( Modifiers.UNKNOWN_ENUM );
			String constantName = cp.utf( in.uint16bit() );
			Field enumConstant = Field.field( type, Modifiers.ENUM_CONSTANT, type, constantName );
			return Element.element( name, ElementKind.ENUM, type, enumConstant );
		} else if ( '@' == tag ) {
			Annotation annotation = readAnnotation( cp, in );
			return Element.element( name, ElementKind.ANNOTATION, annotation.type, annotation );
		} else if ( '[' == tag ) {
			int num = in.uint16bit();
			if ( num == 0 ) {
				return Element.element( name, ElementKind.ARRAY, Class.OBJECT, new Object[0] );
			}
			Element[] elements = new Element[num];
			for ( int i = 0; i < num; i++ ) {
				elements[i] = readElementValue( name, cp, in );
			}
			Object[] values = new Object[elements.length];
			for ( int i = 0; i < values.length; i++ ) {
				values[i] = elements[i].value;
			}
			return Element.element( name, elements[0].kind, elements[0].type, values );
		} else if ( 's' == tag ) {
			String value = cp.utf( in.uint16bit() );
			return Element.element( name, ElementKind.STRING,
					Class.unknownClass( "java/lang/String" ), value );
		} else if ( 'c' == tag ) {
			return Element.element( name, ElementKind.CLASS, Class.CLASS,
					classDescriptor( cp.utf( in.uint16bit() ) ).cls() );
		} else {
			int valueIndex = in.uint16bit();
			return Element.element( name, ElementKind.PRIMITIVE,
					classDescriptor( Character.toString( (char) tag ) ).cls(), null );
		}
	}
}

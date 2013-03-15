/*
 * Copyright (c) 2013 IST - International Software Technology. All rights reserved.
 */
package se.jbee.io.classfile;


public interface ClassProcessor {

	boolean process(Type type);

	void processInterface(Type type, TypeRef[] extendsInterfaces, References references);

	void processClass(Type type, TypeRef extendsClass, TypeRef[] implementsInterfaces, References references);

	void processEnum(Type type, TypeRef[] implementsInterfaces, References references);

	void processAnnotation(Type type, References references);
}

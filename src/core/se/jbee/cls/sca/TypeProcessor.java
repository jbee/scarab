package se.jbee.cls.sca;

import se.jbee.cls.ref.Class;
import se.jbee.cls.ref.Type;
import se.jbee.cls.ref.Usages;

public interface TypeProcessor {

	void processClass( Class cls, Type superclass, Type[] interfaces, Usages usages );

	void processEnum( Class cls, Type[] interfaces, Usages usages );

	void processInterface( Class cls, Type[] superinterfaces, Usages usages );

	void processAnnotation( Class cls, Usages usages );
}

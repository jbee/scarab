package se.jbee.cls.sca;

import se.jbee.cls.ref.Class;
import se.jbee.cls.ref.Type;
import se.jbee.cls.ref.Usages;

public interface JarProcessor {

	TypeFilter filter();

	void process( Class type, Type superclass, Type[] interfaces, Usages usages );
}

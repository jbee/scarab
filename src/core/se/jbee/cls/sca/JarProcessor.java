package se.jbee.cls.sca;

import se.jbee.cls.ref.Class;
import se.jbee.cls.ref.Usages;

public interface JarProcessor {

	void process( Class cls, Usages usages );
}

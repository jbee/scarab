package se.jbee.cls.sca;

import se.jbee.cls.ref.Type;
import se.jbee.cls.ref.Usages;

public interface JarProcessor {

	void process( Type type, Usages usages );
}

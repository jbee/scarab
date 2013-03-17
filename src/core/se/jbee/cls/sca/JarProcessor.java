package se.jbee.cls.sca;

import se.jbee.cls.ref.ClassSignature;
import se.jbee.cls.ref.Usages;

public interface JarProcessor {

	void process( ClassSignature signature, Usages usages );
}

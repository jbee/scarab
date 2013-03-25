package se.jbee.cls.metric;

import se.jbee.cls.graph.ClassNode;

public interface ClassMeasure {

	Ratio measure( ClassNode cls );
}

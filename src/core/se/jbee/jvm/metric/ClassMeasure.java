package se.jbee.jvm.metric;

import se.jbee.jvm.graph.ClassNode;

public interface ClassMeasure {

	Ratio measure( ClassNode cls );
}

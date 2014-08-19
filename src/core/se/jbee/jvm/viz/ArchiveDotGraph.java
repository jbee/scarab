package se.jbee.jvm.viz;

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

import se.jbee.jvm.Archive;
import se.jbee.jvm.graph.ArchiveNode;
import se.jbee.jvm.graph.ClassGraph;
import se.jbee.jvm.io.JarScanner;

public class ArchiveDotGraph {

	public static void main(String[] args) throws IOException {
		write(JarScanner.scan(args), System.out);
	}
	
	public static void write(ClassGraph graph, PrintStream out) {
		out.append(" digraph packages {\n");
		out.append("\tgraph [bgcolor=\"#999999\"]\n");
		out.append("\tedge [arrowhead=normal,arrowtail=dot,colorscheme=paired12]\n");
		out.append("\tnode [shape=folder,width=2,colorscheme=paired12,style=filled,color=\"#ffffff\"]\n");
		archiveConnections(graph, out);
		out.append(" }\n");
	}

	public static void archiveConnections(ClassGraph graph, PrintStream out) {
		Set<Archive> done = new HashSet<Archive>();
		done.add(Archive.RUNTIME);
		int i = 0;
		for (String pattern : new String[] { "-core-", "-api-", "-web-s" }) {
			for (ArchiveNode n : graph.archives) {
				Archive id = n.id();
				if (!done.contains(id) && id.filename().contains(pattern)) {
					done.add(id);
					writeArchiveDependencies(out, n, i++);
				}
			}
		}
		for (ArchiveNode n : graph.archives) {
			Archive id = n.id();
			if (!done.contains(id)) {
				writeArchiveDependencies(out, n, i++);
			}
		}
	}

	private static void writeArchiveDependencies(PrintStream out, ArchiveNode n, int i) {
		String a = clean(n.id().filename());
		int color = (i%12) +1;
		for (ArchiveNode d : n.dependencies()) {
			if (!Archive.RUNTIME.equalTo(d.id())) {
				String b = clean(d.id().filename());
				writeConnection(a, b, "[color="+color+"]", out);
			}
		}
		out.append("\t\"").append(a).append("\" [style=filled, fillcolor="+color+"]\n");
	}

	private static String clean(String label) {
		return label.substring(label.lastIndexOf('/')+1).replace("ist-school-", "").replace("-trunk-SNAPSHOT", "").replace(".jar", "").replaceAll("-[0-9.]+$", "");
	}

	private static void writeConnection(String a, String b, String options, PrintStream out) {
		if (!a.equals(b)) {
			out.append(" \t\"").append(a).append('"').append(" -> ").append('"').append(b).append("\" ").append(options).append(";\n");
		}
	}
}

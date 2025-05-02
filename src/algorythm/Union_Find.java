package algorythm;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import graph.Vertex;

public class Union_Find {

	private Map<Vertex, Vertex> parentMap = new HashMap<>();

	public Union_Find(Set<Vertex> arrayList) {

		for (Vertex vertex : arrayList) {
			parentMap.put(vertex, vertex);
		}

	}

	public Vertex find(Vertex vertex) {
		if (parentMap.get(vertex) != vertex) {
			parentMap.put(vertex, find(parentMap.get(vertex)));
		}
		return parentMap.get(vertex);
	}

	public void union(Vertex src, Vertex dest) {
		Vertex srcParent = find(src);
		Vertex destParent = find(dest);
		if (!srcParent.equals(destParent)) {
			parentMap.put(srcParent, destParent);
		}

	}

	public boolean connected(Vertex vertex1, Vertex vertex2) {
		Vertex fatherSrc = find(vertex1);
		Vertex fatherDest = find(vertex2);

		return fatherSrc.equals(fatherDest);
	}
}

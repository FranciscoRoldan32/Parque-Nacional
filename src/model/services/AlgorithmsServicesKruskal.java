package model.services;

import model.entities.Graph;
import model.entities.Edge;
import model.entities.Vertex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AlgorithmsServicesKruskal {
	private Graph _graph;
	private List<Edge> MinimumSpanningTree;

	public AlgorithmsServicesKruskal(Graph graph) {
		this._graph = graph;
		this.MinimumSpanningTree = new ArrayList<>();
	}

	public List<Edge> getMinimumSpanningTreeKruskal() {

		List<Edge> allEdges = new ArrayList<>();
		for (List<Edge> edges : _graph.getListEdge().values()) {
			allEdges.addAll(edges);
		}

		Collections.sort(allEdges, Comparator.comparingInt(Edge::getWeight));

		UnionFind uf = new UnionFind(_graph.getListVertex());

		for (Edge edge : allEdges) {
			Vertex u = edge.getSrc();
			Vertex v = edge.getDest();
			if (uf.find(u) != uf.find(v)) {
				uf.union(u, v);
				this.MinimumSpanningTree.add(edge);
			}
		}
		return this.MinimumSpanningTree;
	}

	public void print() {
		System.out.println("Árbol de expansión mínima (Kruskal):");
		int totalWeight = 0;
		for (Edge e : this.MinimumSpanningTree) {
			System.out.println(e);
			totalWeight += e.getWeight();
		}
		System.out.println("Peso total del MST: " + totalWeight);
	}

}

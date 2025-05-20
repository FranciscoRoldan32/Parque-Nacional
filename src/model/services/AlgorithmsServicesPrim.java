package model.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import model.entities.Edge;
import model.entities.Graph;
import model.entities.Vertex;

public class AlgorithmsServicesPrim {

	private Graph _graph;
	private List<Edge> MinimumSpanningTree;

	public AlgorithmsServicesPrim(Graph graph) {
		this._graph = graph;
		this.MinimumSpanningTree = new ArrayList<>();
	}

	public List<Edge> getMinimumSpanningTreePrim() {

		Set<Vertex> visited = new HashSet<>();
		PriorityQueue<Edge> minHeap = new PriorityQueue<>(Comparator.comparingInt(Edge::getWeight));

		if (_graph.getListVertex().isEmpty())
			return this.MinimumSpanningTree;

		Vertex start = _graph.getListVertex().get(0);
		visited.add(start);
		minHeap.addAll(_graph.getListEdge().get(start));

		while (!minHeap.isEmpty()) {
			Edge edge = minHeap.poll();

			if (visited.contains(edge.getDest()))
				continue;

			visited.add(edge.getDest());
			this.MinimumSpanningTree.add(edge);

			for (Edge next : _graph.getListEdge().get(edge.getDest())) {
				if (!visited.contains(next.getDest())) {
					minHeap.offer(next);
				}
			}
		}

		return this.MinimumSpanningTree;
	}

	public void print() {

		System.out.println("Árbol de expansión mínima:");
		int totalWeight = 0;
		for (Edge e : this.MinimumSpanningTree) {
			System.out.println(e);
			totalWeight += e.getWeight();
		}
		System.out.println("Peso total del MST: " + totalWeight);
	}

	
}

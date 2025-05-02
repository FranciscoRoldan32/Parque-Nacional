package model.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
	private int quantityVertex;
	private List<Vertex> listVertex;

	private Map<Vertex, List<Edge>> adjacencyList;

	public Graph() {
		this.quantityVertex = 0;
		this.listVertex = new ArrayList<>();
		this.adjacencyList = new HashMap<>();
	}

	public Vertex addVertex(String label) {
		for (Vertex existingVertex : listVertex) {
			if (existingVertex.getLabel().equals(label)) {
				throw new IllegalArgumentException("Vertex with label '" + label + "' already exists");
			}
		}
		Vertex vertex = new Vertex(label);
		listVertex.add(vertex);
		adjacencyList.put(vertex, new ArrayList<>());
		quantityVertex++;
		return vertex;
	}

	public void addEdge(Vertex source, Vertex destination, Double weight) {
		if (weight <= 0) {
			throw new IllegalArgumentException("The graph cannot be equal to or less than 0");
		}

		for (Edge edge : adjacencyList.get(source)) {
			if (edge.getDest().equals(destination)) {

				throw new IllegalArgumentException("This Edge has already been added to the graph.");
			}
		}

		if (source.equals(destination)) {
			throw new IllegalArgumentException("The chart will not accept that the Source and Destination are equal");
		}

		if (!listVertex.contains(source) || !listVertex.contains(destination)) {
			throw new IllegalArgumentException("The source or destination vertex does not exist in the graph");
		}

		adjacencyList.get(source).add(new Edge(source, destination, weight));
	}

	public List<Vertex> getVertices() {
		return listVertex;
	}

	public void setVertices(List<Vertex> listVertex) {
		this.listVertex = listVertex;
	}

	public Vertex getVertex(String nombreEpia) {
		for (Vertex list : listVertex) {
			if (list.getLabel() == nombreEpia) {
				return list;
			}
		}
		return null;
	}

	public Map<Vertex, List<Edge>> getAdjacencyList() {
		return adjacencyList;
	}

	public int getNumVertices() {
		return quantityVertex;
	}

	public List<Edge> getAllEdges() {
		List<Edge> allEdges = new ArrayList<>();
		for (Map.Entry<Vertex, List<Edge>> entry : adjacencyList.entrySet()) {
			allEdges.addAll(entry.getValue());
		}
		return allEdges;
	}

	public List<String> getAllTheEdgesInStrings() {
		List<String> representation = new ArrayList<>();

		for (Map.Entry<Vertex, List<Edge>> entry : adjacencyList.entrySet()) {
			List<Edge> edges = entry.getValue();
			for (Edge edge : edges) {
				representation.add(edge.getSrc().getLabel());
				representation.add(edge.getDest().getLabel());
			}
		}
		return representation;
	}

	public List<String> generateAdjacencyMap() {
		List<String> representation = new ArrayList<>();

		for (Map.Entry<Vertex, List<Edge>> entry : adjacencyList.entrySet()) {
			if (!entry.getValue().isEmpty()) {
				StringBuilder line = new StringBuilder();
				line.append(entry.getKey().getLabel()).append(" --> ");
				List<Edge> edges = entry.getValue();
				for (Edge edge : edges) {
					line.append(edge.getDest().getLabel()).append("(").append(edge.getPeso()).append(") ");
				}
				representation.add(line.toString());
			}
		}

		return representation;
	}

	public void print() {
		for (Map.Entry<Vertex, List<Edge>> entry : adjacencyList.entrySet()) {
			if (!entry.getValue().isEmpty()) {
				System.out.print(entry.getKey().getLabel() + " --> ");
				List<Edge> edges = entry.getValue();
				for (Edge edge : edges) {
					System.out.print(edge.getDest().getLabel() + "(" + edge.getPeso() + ") ");
				}
				System.out.println();
			}
		}
	}
}
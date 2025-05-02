package model.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.entities.Edge;
import model.entities.Graph;
import model.entities.Vertex;

public class GraphService {

	Graph _graph;
	int id = 0;

	public GraphService() {
		_graph = new Graph();
	}

	public Vertex addVertex(String label) {

		for (Vertex currentVertex : _graph.getListVertex()) {
			if (currentVertex.getLabel().equals(label)) {
				throw new IllegalArgumentException("Vertice con nombre '" + label + "' ya existe.");
			}
		}

		Vertex newVertex = new Vertex(id, label);
		id++;
		List<Vertex> listVertexs = _graph.getListVertex();
		listVertexs.add(newVertex);
		_graph.setListVertex(listVertexs);

		Map<Vertex, List<Edge>> listEdge = _graph.getListEdge();
		listEdge.put(newVertex, new ArrayList<>());
		_graph.setListEdge(listEdge);

		return newVertex;
	}

	public void addEdge(Vertex source, Vertex destination, int weight) {

		existVertex(source);
		existVertex(destination);

		if (weight <= 0 || weight > 10)
			throw new IllegalArgumentException("El peso debe estar entre 1 y 10.");

		for (Edge edge : _graph.getListEdge().get(source)) {
			if (edge.getDest().equals(destination))
				throw new IllegalArgumentException("La arista "+edge.toString()+" ya ha sido agregada al grafo.");
		}

		if (source.equals(destination))
			throw new IllegalArgumentException("El vertice fuente y el destino no pueden ser iguales");

		if (!_graph.getListVertex().contains(source) || !_graph.getListVertex().contains(destination))
			throw new IllegalArgumentException("El vértice de origen o destino no existe en el grafo");

		_graph.getListEdge().get(source).add(new Edge(source, destination, weight));
		_graph.getListEdge().get(destination).add(new Edge(destination, source, weight));
	}

	private void existVertex(Vertex v) {
		if (v == null)
		    throw new IllegalArgumentException("El vértice no puede ser nulo.");
		
		if (!_graph.getListVertex().contains(v)) {
			throw new IllegalArgumentException("El vertice " + v.getLabel() + " no existe.");
		}
	}

	public List<Vertex> getVertexs() {
		return _graph.getListVertex();
	}

	public void setVertexs(List<Vertex> _listVertex) {
		_graph.setListVertex(_listVertex);
	}

	public Vertex getVertex(int id) {
		for (Vertex vertex : _graph.getListVertex()) {
			if (vertex.getId() == id) {
				return vertex;
			}
		}
		return null;
	}

	public Map<Vertex, List<Edge>> getAdjacencyList() {
		return _graph.getListEdge();
	}

	public List<Edge> getAllEdges() {
		List<Edge> allEdges = new ArrayList<>();
		for (Map.Entry<Vertex, List<Edge>> entry : _graph.getListEdge().entrySet()) {
			allEdges.addAll(entry.getValue());
		}
		return allEdges;
	}

	public List<String> getAllTheEdgesInStrings() {
		List<String> representation = new ArrayList<>();

		for (Map.Entry<Vertex, List<Edge>> entry : _graph.getListEdge().entrySet()) {
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

		for (Map.Entry<Vertex, List<Edge>> entry : _graph.getListEdge().entrySet()) {
			if (!entry.getValue().isEmpty()) {
				StringBuilder line = new StringBuilder();
				line.append(entry.getKey().getLabel()).append(" --> ");
				List<Edge> edges = entry.getValue();
				for (Edge edge : edges) {
					line.append(edge.getDest().getLabel()).append("(").append(edge.getWeight()).append(") ");
				}
				representation.add(line.toString());
			}
		}
		return representation;
	}

	public void printGraph() {
		System.out.println("=== Lista de Vértices ===");
		if (_graph.getListVertex() == null || _graph.getListVertex().isEmpty()) {
			System.out.println("No hay vértices en el grafo.\n");
		} else {
			for (Vertex v : _graph.getListVertex()) {
				System.out.println(v.toString());
			}
		}

		System.out.println("\n=== Lista de Aristas ===");
		if (_graph.getListEdge() == null || _graph.getListEdge().isEmpty()) {
			System.out.println("No hay Aristas en el grafo.\n");
		} else {
			for (Map.Entry<Vertex, List<Edge>> entry : _graph.getListEdge().entrySet()) {
				System.out.print(entry.getKey() + " -> ");
				List<Edge> edges = entry.getValue();
				if (edges == null || edges.isEmpty()) {
					System.out.println("Sin conexiones.");
				} else {
					for (Edge e : edges) {
						System.out.print(e.toString() + " ");
					}
					System.out.println();
				}
			}
		}
	}
}

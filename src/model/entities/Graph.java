package model.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
	private List<Vertex> listVertex;
	private Map<Vertex, List<Edge>> listEdge;

	public Graph() {
		this.listVertex = new ArrayList<>();
		this.listEdge = new HashMap<>();
	}

	public int getCountVertex() {
		return listVertex.size();
	}

	public List<Vertex> getListVertex() {
		return listVertex;
	}

	public void setListVertex(List<Vertex> listVertex) {
		this.listVertex = listVertex;
	}

	public Map<Vertex, List<Edge>> getListEdge() {
		return listEdge;
	}

	public void setListEdge(Map<Vertex, List<Edge>> listEdge) {
		this.listEdge = listEdge;
	}

	@Override
	public String toString() {
		return "Graph [listVertex=" + listVertex + ", listEdge=" + listEdge + "]";
	}

}
package model.entities;

import java.util.Objects;

import model.entities.Vertex;

public class Vertex {
	private final String label;
	private final int id;

	public Vertex(int id,String label) {
		this.id = id;
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
	
	public int getId() {
		return this.id;
	}
	
	@Override
	public int hashCode() {
	    return Objects.hash(label);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)return true;
		
		if (obj == null || getClass() != obj.getClass())
			return false;
		Vertex vertex = (Vertex) obj;
		return label.equals(vertex.label);
	}

	@Override
	public String toString() {
		return "Vertex [id=" + id + ", label=" + label + "]";
	}
}
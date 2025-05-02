package graph;

import java.util.Objects;

import graph.Vertex;

public class Vertex {
    private String label;
    private int index;

    public Vertex(String label) {
        this.label = label;
        
    }

	public String getLabel() {
        return label;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vertex vertex = (Vertex) obj;
        return label.equals(vertex.label);
    }
}
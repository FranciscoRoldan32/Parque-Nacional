package model.services;

import static org.junit.Assert.*;

import org.junit.Test;

import model.entities.Vertex;

public class GraphServiceTest {

	@Test
	public void existeVertice_conVerticeValido_returnTrue() {
		GraphService graph = new GraphService();
		Vertex v = graph.addVertex("vertice1");
		assertTrue(graph.existVertex(v));
	}

	@Test
	public void existeVertice_verticeSinAgregarAlGrafo_returnFalse() {
		GraphService graph = new GraphService();
		Vertex v = new Vertex(0, "Vertice1");
		assertFalse(graph.existVertex(v));
	}

	@Test
	public void existeVertice_verticeNull_returnFalse() {
		GraphService graph = new GraphService();
		assertFalse(graph.existVertex(null));
	}
	
	@Test
	public void existeVertice_conVerticeNull_returnFalse() {
		GraphService graph = new GraphService();
		assertFalse(graph.existVertex(null));
	}

	@Test
	public void existeVertice_conVerticeNull_returnMessageError() {
		GraphService graph = new GraphService();
		assertFalse(graph.existVertex(null));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void existeVertice_conDosVerticeIguales_ThrowsException() {
		GraphService graph = new GraphService();
        graph.addVertex("A");
        graph.addVertex("A");
	}

	@Test(expected = IllegalArgumentException.class)
	public void validateVertex_WithNullVertex_ThrowsException() {
		GraphService graph = new GraphService();
		graph.validateVertex(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void validateVertex_WithNonExistentVertex_ThrowsException() {
		GraphService graph = new GraphService();
		Vertex v = new Vertex(0, "X");
		graph.validateVertex(v);
	}

	@Test
	public void validateVertex_WithValidVertex_DoesNotThrow() {
		GraphService graph = new GraphService();
		Vertex v = graph.addVertex("A");

		try {
			graph.validateVertex(v);
		} catch (Exception e) {
			fail("No se esperaba ninguna excepción.");
		}
	}
}

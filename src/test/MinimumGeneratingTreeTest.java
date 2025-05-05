package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import graph.Edge;
import graph.Graph;
import graph.Vertex;
import java.util.List;
import java.util.ArrayList;

class MinimumGeneratingTreeTest {

    private MinimumGeneratingTree mgt;
    private Graph graph;

    @BeforeEach
    void setUp() {
        mgt = new MinimumGeneratingTree();
        graph = new Graph();
        Vertex vertexA = new Vertex("A");
        Vertex vertexB = new Vertex("B");
        Vertex vertexC = new Vertex("C");

        Edge edgeAB = new Edge(vertexA, vertexB, 4);
        Edge edgeAC = new Edge(vertexA, vertexC, 1);
        Edge edgeBC = new Edge(vertexB, vertexC, 3);

        graph.addVertex(vertexA);
        graph.addVertex(vertexB);
        graph.addVertex(vertexC);
        graph.addEdge(edgeAB);
        graph.addEdge(edgeAC);
        graph.addEdge(edgeBC);
    }

    @Test
    void testMinimumSpanningTree() {
        List<Edge> mst = mgt.minimumSpanningTree(graph);

        assertNotNull(mst);
        assertEquals(2, mst.size()); 

        assertTrue(mst.contains(new Edge(new Vertex("A"), new Vertex("C"), 1)));
        assertTrue(mst.contains(new Edge(new Vertex("B"), new Vertex("C"), 3)));
    }

    @Test
    void testNoMSTIfDisconnectedGraph() {
        Graph disconnectedGraph = new Graph();
        Vertex vertexD = new Vertex("D");
        Vertex vertexE = new Vertex("E");

        disconnectedGraph.addVertex(vertexD);
        disconnectedGraph.addVertex(vertexE);

        List<Edge> mst = mgt.minimumSpanningTree(disconnectedGraph);

        // Verificamos que no haya árbol generador mínimo
        assertNull(mst);
    }
}

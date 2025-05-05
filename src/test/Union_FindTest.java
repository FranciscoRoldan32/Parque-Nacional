package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import graph.Vertex;
import java.util.HashSet;
import java.util.Set;

class UnionFindTest {

    private Union_Find unionFind;
    private Vertex vertex1;
    private Vertex vertex2;
    private Vertex vertex3;

    @BeforeEach
    void setUp() {
        Set<Vertex> vertices = new HashSet<>();
        vertex1 = new Vertex("A");
        vertex2 = new Vertex("B");
        vertex3 = new Vertex("C");
        vertices.add(vertex1);
        vertices.add(vertex2);
        vertices.add(vertex3);

        unionFind = new Union_Find(vertices);
    }

    @Test
    void testUnionFindInitial() {
        assertEquals(vertex1, unionFind.find(vertex1));
        assertEquals(vertex2, unionFind.find(vertex2));
        assertEquals(vertex3, unionFind.find(vertex3));
    }

    @Test
    void testUnion() {
        unionFind.union(vertex1, vertex2);
        assertTrue(unionFind.connected(vertex1, vertex2));
        assertEquals(unionFind.find(vertex1), unionFind.find(vertex2));
    }

    @Test
    void testConnected() {
        unionFind.union(vertex1, vertex2);
        assertTrue(unionFind.connected(vertex1, vertex2));
    }

    @Test
    void testNotConnected() {
        assertFalse(unionFind.connected(vertex1, vertex3));
    }
}

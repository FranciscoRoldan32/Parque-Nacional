package test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import model.entities.Vertex;
import model.services.UnionFind;

public class unionFindTest {
	Vertex a,b,c;

	@Test
    public void testUnionAndFind() {
        a = new Vertex(0, "A");
        b = new Vertex(1, "B");
        c = new Vertex(2, "C");

        List<Vertex> vertices = Arrays.asList(a, b, c);
        UnionFind uf = new UnionFind(vertices);

        assertNotEquals(uf.find(a), uf.find(b));
        uf.union(a, b);
        assertEquals(uf.find(a), uf.find(b));

        assertNotEquals(uf.find(a), uf.find(c));
        uf.union(a, c);
        assertEquals(uf.find(a), uf.find(c));
        assertEquals(uf.find(b), uf.find(c));
    }
}

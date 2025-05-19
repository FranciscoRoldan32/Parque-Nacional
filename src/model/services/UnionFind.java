package model.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.entities.Vertex;

public class UnionFind {
	 private Map<Vertex, Vertex> parent;
     private Map<Vertex, Integer> rank;

     public UnionFind(List<Vertex> vertices) {
         parent = new HashMap<>();
         rank = new HashMap<>();
         for (Vertex v : vertices) {
             parent.put(v, v);
             rank.put(v, 0);
         }
     }

     public Vertex find(Vertex v) {
         Vertex p = parent.get(v);
         if (!p.equals(v)) {
             p = find(p);
             parent.put(v, p);
         }
         return p;
     }

     public void union(Vertex a, Vertex b) {
         Vertex rootA = find(a);
         Vertex rootB = find(b);
         if (rootA.equals(rootB)) return;

         int rankA = rank.get(rootA);
         int rankB = rank.get(rootB);
         if (rankA < rankB) {
             parent.put(rootA, rootB);
         } else if (rankA > rankB) {
             parent.put(rootB, rootA);
         } else {
             parent.put(rootB, rootA);
             rank.put(rootA, rankA + 1);
         }
     }
 }



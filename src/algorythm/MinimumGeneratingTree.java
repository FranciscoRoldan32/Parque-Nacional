package algorythm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import graph.Edge;
import graph.Graph;
import graph.Vertex;

public class MinimumGeneratingTree {
	private ArrayList<Edge> sortedEdges;

    public MinimumGeneratingTree (){
    }

    public List<Edge> minimumSpanningTree(Graph graphOriginal) {
        sortedEdges = new ArrayList<>();
        List<Edge> mst = new ArrayList<>();


        
        for (List<Edge> aristas : graphOriginal.getAdjacencyList().values()) {
            sortedEdges.addAll(aristas);
        }
        Collections.sort(sortedEdges, Comparator.comparingDouble(Edge::getPeso));
        int numEdges = 0;
        
    
       Set<Vertex> allVertices = graphOriginal.getAdjacencyList().keySet();


         Union_Find union_find = new Union_Find(allVertices);



        for (Edge edge : sortedEdges) {
            Vertex src = edge.getSrc();;
            Vertex dest = edge.getDest();
         

            if (!union_find.connected(src, dest)){

          
                mst.add(edge);

              
                numEdges++;

          
                union_find.union(src, dest);

            }
        }

        if (graphOriginal.getNumVertices() - 1 > numEdges) {
            return null;
        }

        return mst;

}
}

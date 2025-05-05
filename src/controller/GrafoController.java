package controller;

import java.util.List;

import javax.swing.SwingUtilities;

import model.entities.Edge;
import model.entities.Vertex;
import model.services.AlgorithmsServices;
import model.services.GraphService;
import views.View_from_park;

public class GrafoController {

	public GrafoController() {
//		SwingUtilities.invokeLater(() -> {
//			new View_from_park().setVisible(true);
//		});
		
		init();
	}
		
	private void init() {
		GraphService graph = new GraphService();
		graph.printGraph();
        Vertex VA = graph.addVertex("A");
        Vertex VB = graph.addVertex("B");
        Vertex VC = graph.addVertex("C");
        Vertex VD = graph.addVertex("D");
        Vertex VE = graph.addVertex("E");
        Vertex VF = graph.addVertex("F");
        Vertex VG = graph.addVertex("G");
        Vertex VH = graph.addVertex("H");
        Vertex VI = graph.addVertex("I");
		graph.printGraph();
		
        graph.addEdge(VA, VB, 4);
        graph.addEdge(VA, VH, 8);
        
        graph.addEdge(VB, VC, 8);
        graph.addEdge(VB, VH, 10);
        
        graph.addEdge(VC, VD, 6);
        graph.addEdge(VC, VF, 4);
        graph.addEdge(VC, VI, 3);
        
        graph.addEdge(VD, VE, 9);
        graph.addEdge(VD, VF, 10);
        
        graph.addEdge(VE, VF, 10);
        
        graph.addEdge(VF, VG, 3);
        
        graph.addEdge(VG, VH, 1);
        graph.addEdge(VG, VI, 5);
        
        graph.addEdge(VH, VI, 6);

		graph.printGraph();
		
        AlgorithmsServices algorithmService = new AlgorithmsServices(graph.getGraph());
        List<Edge> minimumSpanningTree = algorithmService.getMinimumSpanningTreePrim();
        algorithmService.print();

	}
}

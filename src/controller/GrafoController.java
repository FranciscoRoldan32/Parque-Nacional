package controller;

import javax.swing.SwingUtilities;

import model.entities.Vertex;
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
        Vertex v1 = graph.addVertex("A");
        Vertex v2 = graph.addVertex("B");
        Vertex v3 = graph.addVertex("C");
        Vertex v4 = graph.addVertex("D");
        Vertex v5 = graph.addVertex("E");
		graph.printGraph();
		
        graph.addEdge(v1, v2, 4); // A - B
        graph.addEdge(v1, v3, 7); // A - C
        graph.addEdge(v2, v4, 2); // B - D
        graph.addEdge(v3, v4, 9); // C - D
        graph.addEdge(v4, v5, 3); // D - E
        graph.addEdge(v2, v5, 6); // B - E
        
		graph.printGraph();
	}
}

package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


import model.entities.Edge;
import model.entities.Vertex;
import model.services.AlgorithmsServices;
import model.services.GraphService;
import views.View_Edge_Connections;
import views.View_from_park;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;

public class GrafoController {
	private View_from_park view;
	private GraphService graphService = new GraphService();
	private Map<String, Coordinate> landscapes = new HashMap<>();

	

	public GrafoController(View_from_park view) {
		this.view = view;

	   
	    SwingUtilities.invokeLater(() -> {
	        view.setVisible(true);
	    });

	    init(); 
	}
	
	

    public Map<String, Coordinate> getVertices() {
        return landscapes;
    }
    
	private void init() {
		JMapViewer _mapa= view.getMapViewer();
		_mapa.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        if (e.getButton() == MouseEvent.BUTTON1) {
		        	
		            Coordinate markeradd = (Coordinate) _mapa.getPosition(e.getPoint());
		            String nombre = view.getNombreIngresado(); // Tomar el texto del campo
		            
		            if (nombre == null || nombre.isEmpty()) {
		                JOptionPane.showMessageDialog(null, "Por favor, ingrese un nombre antes de hacer clic.");
		                return;
		            }

		            view.aggVertexToMap(nombre, markeradd);
		            landscapes.put(nombre, markeradd);
		            graphService.addVertex(nombre);
		            view.clearLandscapeNameField();
		            JOptionPane.showMessageDialog(null, "Ubicación agregada correctamente. (" + nombre + ")");
		        }
		    }
		});
		view.getBtnFinal().addActionListener((e -> {
			view.getTxtNombre().setEnabled(false);
			view.getBtnGuardar().setEnabled(false);
			view.getMapViewer().removeMouseListener(null);

		}));
//PARTE DE GENERAR EL ALGORITMO 

//		graphService.printGraph();
		
//        AlgorithmsServices algorithmService = new AlgorithmsServices(graphService.getGraph());
//        List<Edge> minimumSpanningTree = algorithmService.getMinimumSpanningTreePrim();
//        algorithmService.print();

	}
}

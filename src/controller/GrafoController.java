package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.JMapViewer;

import model.entities.Vertex;
import model.entities.Edge;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


import model.entities.Edge;
import model.entities.Vertex;
import model.services.AlgorithmsServicesPrim;
import model.services.AlgorithmsServicesKruskal;
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
	private String nombrePendiente = null;
	private List<Vertex> vertexs;
	

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
		JMapViewer _mapa = view.getMapViewer();

		_mapa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 && nombrePendiente != null) {
					Coordinate markeradd = (Coordinate) _mapa.getPosition(e.getPoint());

					view.aggVertexToMap(nombrePendiente, markeradd);
					landscapes.put(nombrePendiente, markeradd);
					graphService.addVertex(nombrePendiente);

					JOptionPane.showMessageDialog(null, "Ubicación agregada correctamente. (" + nombrePendiente + ")");

					view.clearLandscapeNameField();
					nombrePendiente = null;
					view.getBtnGuardar().setEnabled(true);
					view.getTxtNombre().setEnabled(true);
				}
			}
		});

		view.getBtnGuardar().addActionListener(e -> {
			String nombre = view.getNombreIngresado();

			if (nombre == null || nombre.trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Por favor, ingrese un nombre antes de guardar.");
				return;
			}

			nombrePendiente = nombre;
			view.getBtnGuardar().setEnabled(false);
			view.getTxtNombre().setEnabled(false);

			JOptionPane.showMessageDialog(null,
					"Ahora haga clic en el mapa para agregar la ubicación de \"" + nombre + "\".");
		});

		view.getBtnFinal().addActionListener(e -> {
			view.getTxtNombre().setEnabled(false);
			view.getBtnGuardar().setEnabled(false);
			nombrePendiente = null;
			JOptionPane.showMessageDialog(null, "Ingreso finalizado.");
			abrirViewEdgeConnections();
		});
	}
	
	private void abrirViewEdgeConnections() {
	    vertexs = graphService.getVertexs();
	    View_Edge_Connections dialog = new View_Edge_Connections(
	        view, 
	        view.getMapViewer(), 
	        vertexs,
	        landscapes,
	        graphService
	    );
	    dialog.setVisible(true);
	    runAlgorytm();
	}
	
	private void runAlgorytm() {
		view.getBtnPrim().addActionListener(e -> {
			AlgorithmsServicesPrim algorithmService = new AlgorithmsServicesPrim(graphService.getGraph());
			List<Edge> mst = algorithmService.getMinimumSpanningTreePrim();
			
			algorithmService.print();
			drawMST(mst);
			
		});
		
		view.getBtnKruskal().addActionListener(e -> {
			AlgorithmsServicesKruskal algorithmServiceKruskal = new AlgorithmsServicesKruskal(graphService.getGraph());
			List<Edge> mst = algorithmServiceKruskal.getMinimumSpanningTreeKruskal();

			algorithmServiceKruskal.print();
			drawMST(mst);
		});
	}
	
	private void drawMST(List<Edge> mst) {
	    view.getMapViewer().removeAllMapPolygons();

	    for (Edge edge : mst) {
	        Vertex src = edge.getSrc();
	        Vertex dst = edge.getDest();

	        Coordinate c1 = landscapes.get(src.getLabel());
	        Coordinate c2 = landscapes.get(dst.getLabel());

	        List<Coordinate> route = Arrays.asList(c1, c2, c2, c1);
	        view.drawSubgraph(route);
	    }
	}
}


package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Color;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import model.entities.Vertex;
import model.Dto.EdgeDTO;
import model.entities.Edge;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import model.services.AlgorithmsServicesPrim;
import model.services.AlgorithmsServicesKruskal;
import model.services.GraphService;
import views.View_Edge_Connections;
import views.View_from_park;

public class GrafoController {
	private View_from_park view;
	private GraphService graphService = new GraphService();
	private Map<String, Coordinate> landscapes = new HashMap<>();
	private String auxName = null;
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
		JMapViewer _map = view.getMapViewer();

		_map.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 && auxName != null) {
					Coordinate markeradd = (Coordinate) _map.getPosition(e.getPoint());

					view.aggVertexToMap(auxName, markeradd);
					landscapes.put(auxName, markeradd);
					graphService.addVertex(auxName);

					JOptionPane.showMessageDialog(null, "Ubicación agregada correctamente. (" + auxName + ")");

					view.clearLandscapeNameField();
					auxName = null;
					view.getBtnSave().setEnabled(true);
					view.getTxtName().setEnabled(true);
				}
			}
		});

		view.getBtnSave().addActionListener(e -> {
			String name = view.getNameEntered();

			if (name == null || name.trim().isEmpty()) {
				JOptionPane.showMessageDialog(null, "Por favor, ingrese un nombre antes de guardar.");
				return;
			}

			auxName = name;
			view.getBtnSave().setEnabled(false);
			view.getTxtName().setEnabled(false);

			JOptionPane.showMessageDialog(null,
					"Ahora haga clic en el mapa para agregar la ubicación de \"" + name + "\".");
		});

		view.getBtnEnd().addActionListener(e -> {
			view.getTxtName().setEnabled(false);
			view.getBtnSave().setEnabled(false);
			auxName = null;
			JOptionPane.showMessageDialog(null, "Ingreso finalizado.");
			openViewEdgeConnections();
		});
	}

	private void openViewEdgeConnections() {
		vertexs = graphService.getVertexs();
		List<String> labels = new ArrayList<>();
		for (Vertex v : vertexs) {
			labels.add(v.getLabel());
		}

		View_Edge_Connections dialog = new View_Edge_Connections(view, labels, inputs -> {
			for (EdgeDTO input : inputs) {
				Vertex src = findVertexByLabel(input.getOrigen());
				Vertex dst = findVertexByLabel(input.getDestino());
				graphService.addEdge(src, dst, input.getPeso());
			}
		});

		dialog.setVisible(true);
		drawGraph();
		runAlgorytm();
	}

	private void runAlgorytm() {
		view.getBtnPrim().addActionListener(e -> {
			view.getBtnPrim().setEnabled(false);
			view.getBtnKruskal().setEnabled(false);

			long start = System.nanoTime();
			AlgorithmsServicesPrim algorithmService = new AlgorithmsServicesPrim(graphService.getGraph());
			List<Edge> mst = algorithmService.getMinimumSpanningTreePrim();
			long end = System.nanoTime();
			double tiempoMs = (end - start) / 1_000_000.0;

			algorithmService.print();
			drawMST(mst);

			JOptionPane.showMessageDialog(null, "Tiempo de ejecución de Prim: " + tiempoMs + " ms");
		});

		view.getBtnKruskal().addActionListener(e -> {
			view.getBtnPrim().setEnabled(false);
			view.getBtnKruskal().setEnabled(false);

			long start = System.nanoTime();
			AlgorithmsServicesKruskal algorithmServiceKruskal = new AlgorithmsServicesKruskal(graphService.getGraph());
			List<Edge> mst = algorithmServiceKruskal.getMinimumSpanningTreeKruskal();
			long end = System.nanoTime();
			double tiempoMs = (end - start) / 1_000_000.0;

			algorithmServiceKruskal.print();
			drawMST(mst);

			JOptionPane.showMessageDialog(null, "Tiempo de ejecución de Kruskal: " + tiempoMs + " ms");
		});
	}

	private void drawMST(List<Edge> mst) {

		for (Edge edge : mst) {
			Vertex src = edge.getSrc();
			Vertex dst = edge.getDest();

			Coordinate c1 = landscapes.get(src.getLabel());
			Coordinate c2 = landscapes.get(dst.getLabel());

			List<Coordinate> route = Arrays.asList(c1, c2, c2, c1);

			Color color;
			int peso = edge.getWeight();
			if (peso <= 3) {
				color = Color.GREEN;
			} else if (peso <= 7) {
				color = Color.YELLOW;
			} else {
				color = Color.RED;
			}

			view.drawSubgraph(route, color);
		}

		view.getMapViewer().revalidate();
		view.getMapViewer().repaint();
	}

	private Vertex findVertexByLabel(String label) {
		for (Vertex v : vertexs) {
			if (v.getLabel().equals(label)) {
				v.toString();
				return v;
			}
		}
		return null;
	}

	private void drawGraph() {
		JMapViewer _map = view.getMapViewer();
		if (_map == null) {
			System.out.println("El mapa es null. ¡No se puede dibujar!");
			return;
		}

		Map<Vertex, List<Edge>> adjList = graphService.getAdjacencyList();
		Set<String> drawnSet = new HashSet<>();

		for (Vertex origin : adjList.keySet()) {
			List<Edge> connections = adjList.get(origin);
			Coordinate coordOrigin = landscapes.get(origin.getLabel());

			for (Edge edge : connections) {
				Vertex dest = edge.getDest();
				Coordinate coorddestination = landscapes.get(dest.getLabel());

				if (coordOrigin == null || coorddestination == null) {
					System.out.println("Coordenada nula para: " + origin.getLabel() + " o " + dest.getLabel());
					continue;
				}

				String key = generatesUniqueKey(origin.getLabel(), dest.getLabel());
				if (!drawnSet.contains(key)) {
					try {
						List<Coordinate> line = new ArrayList<>();

						line.add(coordOrigin);
						line.add(coorddestination);
						line.add(coorddestination);
						line.add(coordOrigin);

						MapPolygonImpl edgeLine = new MapPolygonImpl(line);
						edgeLine.getStyle().setColor(Color.GRAY);
						edgeLine.getStyle().setStroke(new java.awt.BasicStroke(1.0f));

						_map.addMapPolygon(edgeLine);

						System.out.println("Dibujando línea entre " + origin.getLabel() + " y " + dest.getLabel());
						drawnSet.add(key);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}

		_map.revalidate();
		_map.repaint();
	}

	private String generatesUniqueKey(String a, String b) {
		return a.compareTo(b) < 0 ? a + "-" + b : b + "-" + a;
	}

}

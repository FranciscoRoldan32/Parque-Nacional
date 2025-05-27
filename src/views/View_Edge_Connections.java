package views;

import javax.swing.*;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import model.entities.Vertex;
import model.services.GraphService;
import model.entities.Edge;
import java.awt.*;
import java.awt.event.*;	
import java.util.*;
import java.util.List;

public class View_Edge_Connections extends JDialog {
	private JComboBox<String> comboSrc;
	private JComboBox<String> comboDest;
	private JSpinner spinnerWeight;
	private DefaultListModel<String> listaAristasModel;
	private List<Edge> _edges;
	private GraphService graphService;
	private List<Vertex> vertexs;
	private Map<String, Coordinate> landscapes;
	private JButton btnAgg;
	private JButton btnAccept;
	private static final long serialVersionUID = 1L;
	private JMapViewer mapViewer;

	public View_Edge_Connections(JFrame parent, JMapViewer mapViewer, List<Vertex> vertexs,
			Map<String, Coordinate> landscapes, GraphService graphService) {
		super(parent, "Agregar Aristas", true);
		setTitle("Agregar Senderos");
		this.mapViewer = mapViewer;
		this._edges = new ArrayList<>();
		this.vertexs = vertexs;
		this.landscapes = landscapes;
		this.graphService = graphService;

		getContentPane().setLayout(new BorderLayout());
		setSize(400, 300);
		setLocationRelativeTo(parent);

		initComponents(vertexs);
	}

	private void initComponents(List<Vertex> list) {
		JPanel panelSeleccion = new JPanel(new GridLayout(3, 2, 5, 5));

		comboSrc = new JComboBox<>(list.stream().map(Vertex::getLabel).toArray(String[]::new));
		comboDest = new JComboBox<>(list.stream().map(Vertex::getLabel).toArray(String[]::new));
		spinnerWeight = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));

		panelSeleccion.add(new JLabel("Origen:"));
		panelSeleccion.add(comboSrc);
		panelSeleccion.add(new JLabel("Destino:"));
		panelSeleccion.add(comboDest);
		panelSeleccion.add(new JLabel("Es Perjudicial al Ambiente (1-10):"));
		panelSeleccion.add(spinnerWeight);

		getContentPane().add(panelSeleccion, BorderLayout.NORTH);

		listaAristasModel = new DefaultListModel<>();
		JList<String> edgeList = new JList<>(listaAristasModel);
		getContentPane().add(new JScrollPane(edgeList), BorderLayout.CENTER);

		JPanel buttonsPanel = new JPanel();
		btnAgg = new JButton("Agregar Sendero");
		btnAccept = new JButton("Aceptar");
		buttonsPanel.add(btnAgg);
		buttonsPanel.add(btnAccept);

		getContentPane().add(buttonsPanel, BorderLayout.SOUTH);

		btnAgg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String srcLabel = (String) comboSrc.getSelectedItem();
				String destLabel = (String) comboDest.getSelectedItem();
				int weight = (int) spinnerWeight.getValue();

				if (srcLabel.equals(destLabel)) {
					JOptionPane.showMessageDialog(View_Edge_Connections.this,
							"El origen y el destino no pueden ser iguales.");
					return;
				}

				Vertex src = vertexs.stream().filter(v -> v.getLabel().equals(srcLabel)).findFirst().orElse(null);
				Vertex dest = vertexs.stream().filter(v -> v.getLabel().equals(destLabel)).findFirst()
						.orElse(null);

				try {
					graphService.addEdge(src, dest, weight);
					listaAristasModel.addElement(srcLabel + " -> " + destLabel + " (" + weight + ")");
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(View_Edge_Connections.this, ex.getMessage());
				}
			}
		});

		btnAccept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawGraph();
				dispose();
			}
		});
	}

	private void drawGraph() {
		JMapViewer _map = this.mapViewer;
		if (_map == null) {
			System.out.println("El mapa es null. ¡No se puede dibujar!");
			return;
		}
		Map<Vertex, List<Edge>> adjList = graphService.getAdjacencyList();
		Set<String> dibujadas = new HashSet<>();

		System.out.println("Aristas en el grafo:");
		for (Map.Entry<Vertex, List<Edge>> entry : adjList.entrySet()) {
			for (Edge edge : entry.getValue()) {
				System.out.println(edge);
			}
		}

		for (Map.Entry<Vertex, List<Edge>> entry : adjList.entrySet()) {
			Vertex origen = entry.getKey();
			Coordinate coordOrigen = landscapes.get(origen.getLabel());

			for (Edge edge : entry.getValue()) {
				Vertex destino = edge.getDest();
				Coordinate coordDestino = landscapes.get(destino.getLabel());

				if (coordOrigen == null || coordDestino == null) {
					System.out.println("Coordenada nula para: " + origen.getLabel() + " o " + destino.getLabel());
					continue;
				}

				String key = generarClaveUnica(origen.getLabel(), destino.getLabel());
				if (!dibujadas.contains(key)) {
					try {
						List<Coordinate> line = new ArrayList<>();
						line.add(coordOrigen);
						line.add(coordDestino);
						line.add(coordOrigen); 

						MapPolygonImpl edgeLine = new MapPolygonImpl(line);

						try {
							edgeLine.getStyle().setColor(Color.RED);
						} catch (Exception ex) {
						}
						_map.addMapPolygon(edgeLine);

						System.out.println("Dibujando línea entre " + origen.getLabel() + " y " + destino.getLabel());
						dibujadas.add(key);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		}
		
	}

	private String generarClaveUnica(String a, String b) {
		return a.compareTo(b) < 0 ? a + "-" + b : b + "-" + a;
	}

	public List<Edge> getEdges() {
		return _edges;
	}
} 
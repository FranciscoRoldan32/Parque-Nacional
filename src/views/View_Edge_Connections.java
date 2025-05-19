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
		this.mapViewer = mapViewer;
		this._edges = new ArrayList<>();
		this.vertexs = vertexs;
		this.landscapes = landscapes;
		this.graphService = graphService;

		setLayout(new BorderLayout());
		setSize(400, 300);
		setLocationRelativeTo(parent);

		initComponentes(vertexs);
	}

	private void initComponentes(List<Vertex> list) {
		JPanel panelSeleccion = new JPanel(new GridLayout(3, 2, 5, 5));

		comboSrc = new JComboBox<>(list.stream().map(Vertex::getLabel).toArray(String[]::new));
		comboDest = new JComboBox<>(list.stream().map(Vertex::getLabel).toArray(String[]::new));
		spinnerWeight = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));

		panelSeleccion.add(new JLabel("Origen:"));
		panelSeleccion.add(comboSrc);
		panelSeleccion.add(new JLabel("Destino:"));
		panelSeleccion.add(comboDest);
		panelSeleccion.add(new JLabel("Peso (1-10):"));
		panelSeleccion.add(spinnerWeight);

		add(panelSeleccion, BorderLayout.NORTH);

		listaAristasModel = new DefaultListModel<>();
		JList<String> listaAristas = new JList<>(listaAristasModel);
		add(new JScrollPane(listaAristas), BorderLayout.CENTER);

		JPanel panelBotones = new JPanel();
		btnAgg = new JButton("Agregar Arista");
		btnAccept = new JButton("Aceptar");
		panelBotones.add(btnAgg);
		panelBotones.add(btnAccept);

		add(panelBotones, BorderLayout.SOUTH);

		btnAgg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String origenLabel = (String) comboSrc.getSelectedItem();
				String destinoLabel = (String) comboDest.getSelectedItem();
				int peso = (int) spinnerWeight.getValue();

				if (origenLabel.equals(destinoLabel)) {
					JOptionPane.showMessageDialog(View_Edge_Connections.this,
							"El origen y el destino no pueden ser iguales.");
					return;
				}

				Vertex origen = vertexs.stream().filter(v -> v.getLabel().equals(origenLabel)).findFirst().orElse(null);
				Vertex destino = vertexs.stream().filter(v -> v.getLabel().equals(destinoLabel)).findFirst()
						.orElse(null);

				try {
					graphService.addEdge(origen, destino, peso);
					listaAristasModel.addElement(origenLabel + " -> " + destinoLabel + " (" + peso + ")");
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
		System.out.println("Dibujando grafo...");
		JMapViewer _mapa = this.mapViewer;
		System.out.println("_mapa es: " + _mapa);
		if (_mapa == null) {
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

				String clave = generarClaveUnica(origen.getLabel(), destino.getLabel());
				if (!dibujadas.contains(clave)) {
					try {
						List<Coordinate> linea = new ArrayList<>();
						linea.add(coordOrigen);
						linea.add(coordDestino);
						linea.add(coordOrigen); 

						MapPolygonImpl lineaArista = new MapPolygonImpl(linea);

						try {
							lineaArista.getStyle().setColor(Color.RED);
						} catch (Exception ex) {
						}
						_mapa.addMapPolygon(lineaArista);

						System.out.println("Dibujando línea entre " + origen.getLabel() + " y " + destino.getLabel());
						dibujadas.add(clave);
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

	public List<Edge> getAristas() {
		return _edges;
	}
} 
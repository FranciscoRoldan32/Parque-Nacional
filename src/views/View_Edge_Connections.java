package views;

import javax.swing.*;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import model.services.GraphService;
import model.Dto.EdgeDto;
import model.Dto.VertexDto;
import java.awt.*;
import java.awt.event.*;	
import java.util.*;
import java.util.List;

public class View_Edge_Connections extends JDialog {
	private JComboBox<String> comboSrc;
	private JComboBox<String> comboDest;
	private JSpinner spinnerWeight;
	private DefaultListModel<String> listaAristasModel;
	private List<EdgeDto> _edges;
	private List<VertexDto> vertexs;
	private Map<String, Coordinate> landscapes;
	private JButton btnAgg;
	private JButton btnAccept;
	private static final long serialVersionUID = 1L;
	private JMapViewer mapViewer;
	private EdgeDto edgeDto;

	public View_Edge_Connections(JFrame parent, JMapViewer mapViewer, List<VertexDto> vertexs,
			Map<String, Coordinate> landscapes) {
		super(parent, "Agregar Aristas", true);
		setTitle("Agregar Senderos");
		this.mapViewer = mapViewer;
		this._edges = new ArrayList<>();
		this.landscapes = landscapes;
		this.vertexs = vertexs;
		getContentPane().setLayout(new BorderLayout());
		setSize(400, 300);
		setLocationRelativeTo(parent);
		initComponents(vertexs);
	}

	public void initComponents(List<VertexDto> list) {
		this.vertexs = list;
		JPanel panelSeleccion = new JPanel(new GridLayout(3, 2, 5, 5));

		comboSrc = new JComboBox<>(list.stream().map(VertexDto::getLabel).toArray(String[]::new));
		comboDest = new JComboBox<>(list.stream().map(VertexDto::getLabel).toArray(String[]::new));
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

		addListenerBtn();
	}
	
	private void addListenerBtn() {
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

				VertexDto src = vertexs.stream().filter(v -> v.getLabel().equals(srcLabel)).findFirst().orElse(null);
				VertexDto dest = vertexs.stream().filter(v -> v.getLabel().equals(destLabel)).findFirst().orElse(null);

				try {
					edgeDto = new EdgeDto(src, dest, weight);
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
	
	public EdgeDto getEdge() {
		return edgeDto;
	}

	private void drawGraph() {
		JMapViewer _map = this.mapViewer;
		if (_map == null) {
			System.out.println("El mapa es null. ¡No se puede dibujar!");
			return;
		}
		Map<VertexDto, List<EdgeDto>> adjList = graphService.getAdjacencyList();
		Set<String> dibujadas = new HashSet<>();

		System.out.println("Aristas en el grafo:");
		for (Map.Entry<VertexDto, List<EdgeDto>> entry : adjList.entrySet()) {
			for (EdgeDto edge : entry.getValue()) {
				System.out.println(edge);
			}
		}

		for (Map.Entry<VertexDto, List<EdgeDto>> entry : adjList.entrySet()) {
			VertexDto origen = entry.getKey();
			Coordinate coordOrigen = landscapes.get(origen.getLabel());

			for (EdgeDto edge : entry.getValue()) {
				VertexDto destino = edge.getDestDto();
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

	public List<EdgeDto> getEdges() {
		return _edges;
	}
} 
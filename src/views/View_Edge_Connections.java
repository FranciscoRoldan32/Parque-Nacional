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
    private JComboBox<String> comboOrigen;
    private JComboBox<String> comboDestino;
    private JSpinner spinnerPeso;
    private DefaultListModel<String> listaAristasModel;
    private List<Edge> _edges;
    private GraphService graphService;
    private List<Vertex> vertexs;
    private Map<String, Coordinate> landscapes;
    private JButton btnAgg;
    private JButton btnAccept;

    // Cambia el constructor para recibir GraphService
    public View_Edge_Connections(JFrame parent, List<Vertex> vertexs, Map<String, Coordinate> landscapes, GraphService graphService) {
        super(parent, "Agregar Aristas", true);
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

        comboOrigen = new JComboBox<>(list.stream().map(Vertex::getLabel).toArray(String[]::new));
        comboDestino = new JComboBox<>(list.stream().map(Vertex::getLabel).toArray(String[]::new));
        spinnerPeso = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));

        panelSeleccion.add(new JLabel("Origen:"));
        panelSeleccion.add(comboOrigen);
        panelSeleccion.add(new JLabel("Destino:"));
        panelSeleccion.add(comboDestino);
        panelSeleccion.add(new JLabel("Peso (1-10):"));
        panelSeleccion.add(spinnerPeso);

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

        // --- Listener para agregar arista ---
        btnAgg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String origenLabel = (String) comboOrigen.getSelectedItem();
                String destinoLabel = (String) comboDestino.getSelectedItem();
                int peso = (int) spinnerPeso.getValue();

                if (origenLabel.equals(destinoLabel)) {
                    JOptionPane.showMessageDialog(View_Edge_Connections.this, "El origen y el destino no pueden ser iguales.");
                    return;
                }

                Vertex origen = vertexs.stream().filter(v -> v.getLabel().equals(origenLabel)).findFirst().orElse(null);
                Vertex destino = vertexs.stream().filter(v -> v.getLabel().equals(destinoLabel)).findFirst().orElse(null);

                try {
                    graphService.addEdge(origen, destino, peso);
                    listaAristasModel.addElement(origenLabel + " -> " + destinoLabel + " (" + peso + ")");
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(View_Edge_Connections.this, ex.getMessage());
                }
            }
        });

        // --- Listener para aceptar y dibujar ---
        btnAccept.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dibujarGrafoSinDuplicados();
                dispose();
            }
        });
    }

    private void dibujarGrafoSinDuplicados() {
    	System.out.println("Dibujando grafo...");
    	JMapViewer _mapa = ((View_from_park) getParent()).getMapViewer();
        System.out.println("_mapa es: " + _mapa); // <-- Esto imprime la referencia del mapa
        if (_mapa == null) {
            System.out.println("El mapa es null. ¡No se puede dibujar!");
            return;
        }
        Map<Vertex, List<Edge>> adjList = graphService.getAdjacencyList();
        Set<String> dibujadas = new HashSet<>();

        for (Map.Entry<Vertex, List<Edge>> entry : adjList.entrySet()) {
            Vertex origen = entry.getKey();
            Coordinate coordOrigen = landscapes.get(origen.getLabel());

            for (Edge edge : entry.getValue()) {
                Vertex destino = edge.getDest();
                Coordinate coordDestino = landscapes.get(destino.getLabel());
                
                if (coordOrigen != null && coordDestino != null) {
                    String clave = generarClaveUnica(origen.getLabel(), destino.getLabel());
                    if (!dibujadas.contains(clave)) {
                        List<Coordinate> linea = new ArrayList<>();
                        linea.add(coordOrigen);
                        linea.add(coordDestino);

                        MapPolygonImpl lineaArista = new MapPolygonImpl(linea);
                        lineaArista.getStyle().setColor(Color.RED); 
                        _mapa.addMapPolygon(lineaArista);
                        

                        dibujadas.add(clave);
                    }
                }
            }
        }
        _mapa.repaint();
    }

    private String generarClaveUnica(String a, String b) {
        return a.compareTo(b) < 0 ? a + "-" + b : b + "-" + a;
    }

    public List<Edge> getAristas() {
        return _edges;
    }
}
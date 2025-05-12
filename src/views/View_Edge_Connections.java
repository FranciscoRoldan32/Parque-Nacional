package views;

import javax.swing.*;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;

import controller.GrafoController;
import model.entities.Vertex;
import model.services.GraphService;
import model.entities.Edge;
import model.entities.Graph;


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
    private Map<String, Coordinate> landscapes;
    private JMapViewer _mapa;
    
    
    private JButton btnAgg;
    private JButton btnAccept;

    public View_Edge_Connections(JFrame parent, List<Vertex> vertexs,Map<String, Coordinate> landscapes, JMapViewer _mapa) {
        super(parent, "Agregar Aristas", true);
        this._edges = new ArrayList<>();
        this._mapa=_mapa;
        this.landscapes=landscapes;
        

        getContentPane().setLayout(new BorderLayout());
        setSize(400, 300);
        setLocationRelativeTo(parent);
        
        initComponentes(vertexs);
       
    }
        private void initComponentes(List<Vertex> list) {
            // Panel para selección de aristas
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

            getContentPane().add(panelSeleccion, BorderLayout.NORTH);

      
            listaAristasModel = new DefaultListModel<>();
            JList<String> listaAristas = new JList<>(listaAristasModel);
            getContentPane().add(new JScrollPane(listaAristas), BorderLayout.CENTER);

            
            JPanel panelBotones = new JPanel();
            btnAgg = new JButton("Agregar Arista");
            btnAgg.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            		 String origen = (String) comboOrigen.getSelectedItem();
            		    String destino = (String) comboDestino.getSelectedItem();
            		    int peso = (int) spinnerPeso.getValue();

            		    if (origen.equals(destino)) {
            		    	JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(btnAgg), "El vértice origen y destino no pueden ser iguales.");
            		        return;
            		    }

            		    try {
            		        Vertex vOrigen = graphService.getVertex(origen);
            		        Vertex vDestino = graphService.getVertex(destino);

            		        // Agregar al modelo (el grafo) directamente
            		        graphService.addEdge(vOrigen, vDestino, peso);

            		        // Mostrar en la lista visual
            		        listaAristasModel.addElement(origen + " --(" + peso + ")--> " + destino);

            		    } catch (IllegalArgumentException ex) {
            		    	JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(btnAgg), ex.getMessage());
            		    }
            	
            	}
            });
            btnAccept = new JButton("Aceptar");
            btnAccept.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
            		List<Edge> todasLasAristas = graphService.getAllEdges();
            		dibujarAristasEnMapa(todasLasAristas,landscapes);
            		_mapa.revalidate();
                    _mapa.repaint();
            	    dispose(); // cerrar el diálogo
            	    }
            	
            });
            panelBotones.add(btnAgg);
            panelBotones.add(btnAccept);

            getContentPane().add(panelBotones, BorderLayout.SOUTH);
        }
        public void dibujarAristasEnMapa(List<Edge> edges, Map<String, Coordinate> coords) {
            for (Edge edge : edges) {
                Coordinate coordOrigen = coords.get(edge.getSrc().getLabel());
                Coordinate coordDestino = coords.get(edge.getDest().getLabel());

                if (coordOrigen == null || coordDestino == null) {
                    System.out.println("Coordenada no encontrada para: " + edge.getSrc().getLabel() + " o " + edge.getDest().getLabel());
                    return;
                }

                List<Coordinate> route = Arrays.asList(coordOrigen, coordDestino);
                _mapa.addMapPolygon(new MapPolygonImpl(route));
            }

            _mapa.revalidate();
            _mapa.repaint();
        }

    public List<Edge> getAristas() {
        return _edges;
    }
    public void setController(GrafoController controller) {
        this.controller = controller;
    }
    
    public void setGraphService(GraphService graphService) {
        this.graphService = graphService;
    }

  
}
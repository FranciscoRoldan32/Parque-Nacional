package controller_park;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;

import graph.Graph;
import graph.Vertex;
import view_parque.view_from_park;
import coordinates.Coordinates;



public class Controller {
		private view_from_park view;
	    private String landscapeName;
	    private String description;
	    
	    private Graph graph;
	    
	    private JButton btnAddPoint;
	    
	    private List<String> nombresIngresados = new ArrayList<>();
	    private Map<String, Coordinate> lugares = new LinkedHashMap<>();
	    private boolean esperandoCoordenada = false;
	    private String pendingVertexName = null;

	    
	    

	    public Controller(view_from_park view, Graph graph) {
	    	this.view=view;
	    	this.graph=graph;
	    	iniciar();
	
	    }
	    public void iniciar() {
	        
	    	view.setMapClickListener(coord -> {
	            if (esperandoCoordenada && pendingVertexName != null) {
	                lugares.put(pendingVertexName, (Coordinate) coord);
	                graph.addVertex(pendingVertexName);
	                view.aggVertexToMap(pendingVertexName, "", (Coordinate) coord);

	                // Limpiar estado
	                esperandoCoordenada = false;
	                pendingVertexName = null;
	                view.clearLandscapeNameField();
	            }
	        });

	        view.getSaveButton().addActionListener(e -> {
	            String nombre = view.getLandscapeNameField().getText().trim();
	            if (!nombre.isEmpty()) {
	                pendingVertexName = nombre;
	                esperandoCoordenada = true;
	                System.out.println("Ahora hacé clic en el mapa para: " + nombre);
	            } else {
	                System.out.println("Ingrese un nombre antes de guardar.");
	            }
	        });

	        view.getFinalizar().addActionListener(e -> {
	            System.out.println("Puntos registrados:");
	            lugares.forEach((nombre, coord) -> {
	                System.out.println(nombre + " -> " + coord);
	            });
	        });
	    }
	


	   


}

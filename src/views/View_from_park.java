package views;

import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;



import javax.swing.*;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.ModuleLayer.Controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openstreetmap.gui.jmapviewer.Coordinate;

public class View_from_park extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtNombre;
//	private JTextArea txtDescripcion;
	private JMapViewer mapViewer;
	private JButton btnSave,btnPrim,btnFinal,btnKruskal,btnExplicacion;

	

	public View_from_park() {
		setTitle("Parque Nacional Circuito Altas Cumbres");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        

        initMapPanel();
        initFormFields();
        initButtons();
        initLabels();

    }

    private void initMapPanel() {
        mapViewer = new JMapViewer();
        mapViewer.setBounds(10, 10, 600, 540);
        Coordinate coord = new Coordinate(-32.06908122640573, -64.55121562299708);
        mapViewer.setDisplayPosition(coord, 10);
        getContentPane().add(mapViewer);
    }

    private void initFormFields() {
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(620, 75, 80, 25);
        getContentPane().add(lblNombre);

        txtNombre = new JTextField();
        txtNombre.setBounds(689, 75, 250, 25);
        getContentPane().add(txtNombre);
    }

    public void initButtons() {
        btnSave = new JButton("Guardar");
        btnSave.setBounds(689, 127, 100, 30);
        getContentPane().add(btnSave);


        btnFinal = new JButton("Finalizar");
        btnFinal.setBounds(839, 127, 100, 30);
        getContentPane().add(btnFinal);

        btnExplicacion = new JButton("Explicacion");
        btnExplicacion.setBounds(885, 527, 89, 23);
        getContentPane().add(btnExplicacion);
        
        btnKruskal = new JButton("Algoritmo Kruskal");
        btnKruskal.setBounds(635, 355, 156, 65);
        getContentPane().add(btnKruskal);
        
        btnPrim = new JButton("Algoritmo Prim");
        btnPrim.setBounds(818, 355, 156, 65);
        getContentPane().add(btnPrim);
    }

    private void initLabels() {
        JLabel labelTitulo = new JLabel("Ingresar los datos del Punto de Interes");
        labelTitulo.setBounds(620, 10, 250, 54);
        getContentPane().add(labelTitulo);
    }
    public void aggVertexToMap(String vertexName, Coordinate coordinate) {
		addVertexToMap(vertexName,coordinate);
	}
	
	 private void addVertexToMap(String vertexName, Coordinate coordinate) {
	        MapMarkerDot marker = new MapMarkerDot(vertexName, coordinate);
	        marker.getStyle().setBackColor(Color.RED);
	        marker.getStyle().setColor(Color.BLUE);
	        mapViewer.addMapMarker(marker);

	        mapViewer.revalidate();
	        mapViewer.repaint();
	    }
	 
	public void drawSubgraph(List<Coordinate> route) {
		MapPolygonImpl lineaArista = new MapPolygonImpl(route);
		lineaArista.getStyle().setColor(Color.BLUE);
		mapViewer.addMapPolygon(lineaArista);
		mapViewer.revalidate();
		mapViewer.repaint();		
	}
	
    public JMapViewer getMapViewer() {
        return mapViewer;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }
    public String getNombreIngresado() {
        return txtNombre.getText().trim();
    }
 
    public JButton getBtnGuardar() {
        return btnSave;
    }
    
    public JButton getBtnKruskal () {
    	return btnKruskal;
    }
    
    public JButton getBtnPrim() {
    	return btnPrim;
    }
    
    public void clearLandscapeNameField() {
		 txtNombre.setText("");
	 }
    public JButton getBtnFinal() {
    	return btnFinal;
    }
}
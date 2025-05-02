package view_parque;

import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;

import controller_park.Controller;

import javax.swing.*;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.Consumer;

import org.openstreetmap.gui.jmapviewer.Coordinate;

import coordinates.Coordinates;
import graph.Vertex;



public class view_from_park extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JTextField txtNombre;
	private JTextField txtDescripcion;
	
	private JMapViewer mapViewer;
	
	
	private JButton saveButton;
	private JButton finalizar;
	private JButton explicacion;
	
	private JLabel landscapeName;
	private JLabel description;
	
	private JPanel panelPrincipal;
	
	private Consumer<ICoordinate> mapClickListener;

	
	
	public view_from_park() {
		inicializarVentana();
	}
		
		public void inicializarVentana() {
			setTitle("Parque Nacional Circuito Altas Cumbres");
			setSize(1000, 600);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			panelPrincipal = new JPanel();
			panelPrincipal.setLayout(null);
			setContentPane(panelPrincipal);

			inicializarMapa();
			inicializarCamposDeTexto();
			inicializarBotones();
			inicializarLabelTitulo();
		}

		private void inicializarMapa() {
			mapViewer = new JMapViewer();
		    mapViewer.setBounds(10, 10, 600, 540);
		    Coordinate coord = new Coordinate(-32.06908122640573, -64.55121562299708);
		    mapViewer.setDisplayPosition(coord, 10);
		    panelPrincipal.add(mapViewer); 
		}

		private void inicializarCamposDeTexto() {
		    // Label Nombre
		    landscapeName = new JLabel("Nombre:");
		    landscapeName.setBounds(620, 75, 80, 25);
		    getContentPane().add(landscapeName);

		    // Campo de texto Nombre
		    txtNombre = new JTextField();
		    txtNombre.setBounds(689, 75, 250, 25);
		    getContentPane().add(txtNombre);

//		    // Label Descripción
//		    description = new JLabel("Descripción:");
//		    description.setBounds(620, 132, 100, 25);
//		    getContentPane().add(description);
//
//		    // Campo de texto Descripción
//		    JScrollPane scrollDescripcion = new JScrollPane();
//		    scrollDescripcion.setBounds(689, 132, 250, 100);
//		    getContentPane().add(scrollDescripcion);
//
//		    txtDescripcion = new JTextField();
//		    scrollDescripcion.setViewportView(txtDescripcion);
//POR EL MOMENTO NO VAMOS A INGRESAR DESCRIPCION 
		}

		private void inicializarBotones() {
		    // Botón Guardar
		    saveButton = new JButton("Guardar");
		    saveButton.setBounds(761, 253, 100, 30);
		    getContentPane().add(saveButton);

		    // Botón Finalizar
		    finalizar = new JButton("Finalizar ingreso");
		    finalizar.setBounds(737, 382, 164, 54);
		    getContentPane().add(finalizar);

		    // Botón Explicación
		    explicacion = new JButton("Explicacion");
		    explicacion.setBounds(885, 527, 89, 23);
		    getContentPane().add(explicacion);
		}

		private void inicializarLabelTitulo() {
		    JLabel labelTitulo = new JLabel("Ingresar los datos del Punto de Interes");
		    labelTitulo.setBounds(620, 10, 300, 54); // Ancho corregido a 300 para mejor visibilidad
		    getContentPane().add(labelTitulo);
		}
	
	
	public void aggVertexToMap(String vertexName, String description, Coordinate coordinate) {
		addVertexToMap(vertexName,description,coordinate);
	}
	
	 private void addVertexToMap(String vertexName,String description, Coordinate coordinate) {
	        MapMarkerDot marker = new MapMarkerDot(vertexName, coordinate);
	        marker.getStyle().setBackColor(Color.RED);
	        marker.getStyle().setColor(Color.BLUE);
	        mapViewer.addMapMarker(marker);

	            // Update the map
	        mapViewer.revalidate();
	        mapViewer.repaint();
	    }
	 public void setMapClickListener(Consumer<ICoordinate> consumer) {
		    this.mapClickListener = consumer;

		    mapViewer.addMouseListener(new MouseAdapter() {
		        @Override
		        public void mouseClicked(MouseEvent e) {
		            if (mapClickListener != null) {
		                Point point = e.getPoint();
		                ICoordinate coord = mapViewer.getPosition(point);
		                mapClickListener.accept(coord);
		            }
		        }
		    });
		}
	 public JButton getSaveButton() {
		 return saveButton;
	 }
	 public JButton getFinalizar() {
			return finalizar;
		}
	// Devuelve el campo de texto donde se escribe el nombre del landscape
	 public JTextField getLandscapeNameField() {
	     return txtNombre; // Deberías tener un JTextField llamado así
	 }

	 // Devuelve el campo de texto donde se escribe la descripción del landscape
	 public JTextField getLandscapeDescriptionField() {
	     return txtDescripcion; // Deberías tener un JTextField llamado así
	 }

	 // Limpia el campo de nombre del landscape
	 public void clearLandscapeNameField() {
		 txtNombre.setText("");
	 }

	 // Limpia el campo de descripción del landscape
	 public void clearLandscapeDescriptionField() {
		 txtDescripcion.setText("");
	 }
	 
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
		    view_from_park ventana = new view_from_park();
		    ventana.setVisible(true);
		});
	}

}
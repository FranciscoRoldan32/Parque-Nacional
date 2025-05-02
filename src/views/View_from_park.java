package views;

import org.openstreetmap.gui.jmapviewer.JMapViewer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openstreetmap.gui.jmapviewer.Coordinate;

public class View_from_park extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField txtNombre;
	private JTextArea txtDescripcion;
	private JMapViewer mapViewer;

	public View_from_park() {
		setTitle("Parque Nacional Circuito Altas Cumbres");
		setSize(1000, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Panel del mapa
		mapViewer = new JMapViewer();
		mapViewer.setBounds(10, 10, 600, 540);
		Coordinate coord = new Coordinate(-32.06908122640573, -64.55121562299708);
		mapViewer.setDisplayPosition(coord, 10);
		getContentPane().setLayout(null);
		getContentPane().add(mapViewer);

		// Campo Nombre
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(620, 75, 80, 25);
		getContentPane().add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setBounds(689, 75, 250, 25);
		getContentPane().add(txtNombre);

		// Campo Descripción
		JLabel lblDescripcion = new JLabel("Descripción:");
		lblDescripcion.setBounds(620, 132, 100, 25);
		getContentPane().add(lblDescripcion);
		JScrollPane scrollDescripcion = new JScrollPane();
		scrollDescripcion.setBounds(689, 132, 250, 100);
		getContentPane().add(scrollDescripcion);

		txtDescripcion = new JTextArea();
		scrollDescripcion.setViewportView(txtDescripcion);

		// Botón guardar
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(761, 253, 100, 30);
		getContentPane().add(btnGuardar);

		JLabel labelTitulo = new JLabel("Ingresar los datos del Punto de Interes");
		labelTitulo.setBounds(620, 10, 200, 54);
		getContentPane().add(labelTitulo);

		JButton btnExplicacion = new JButton("Explicacion");
		btnExplicacion.setBounds(885, 527, 89, 23);
		getContentPane().add(btnExplicacion);

		// Acción del botón
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				String nombre = txtNombre.getText();
//				String descripcion = txtDescripcion.getText();

			}
		});
	}
}
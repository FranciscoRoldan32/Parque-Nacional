package view_parque;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Explained extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel titulo;
	private JTextArea txtExplicacion;
	private JButton btnVolverMenu;
	private JScrollPane scrollPane;

	public Explained() {
		setLayout(null);

		titulo = new JLabel("Parque Nacional");
		titulo.setBounds(258, 11, 222, 36);
		titulo.setFont(new Font("Arial", Font.BOLD, 24));
		add(titulo);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(99, 58, 416, 267);
		add(scrollPane);
		
				txtExplicacion = new JTextArea(10, 30);
				scrollPane.setViewportView(txtExplicacion);
				txtExplicacion.setLineWrap(true);
				txtExplicacion.setWrapStyleWord(true);
				txtExplicacion.setEditable(false);
				txtExplicacion.setText("");

		btnVolverMenu = new JButton("Volver la Aplicacion");
		btnVolverMenu.setBounds(299, 460, 150, 30);
		btnVolverMenu.setBackground(Color.RED);
		add(btnVolverMenu);
	}

	public void addBackListener(ActionListener listener) {
		btnVolverMenu.addActionListener(listener);
	}
}

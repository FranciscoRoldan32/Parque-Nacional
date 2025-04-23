package view_parque;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class View_Edge_Connections extends JDialog {
    private JComboBox<String> comboOrigen;
    private JComboBox<String> comboDestino;
    private JSpinner spinnerPeso;
    private DefaultListModel<String> listaAristasModel;
    private java.util.List<Arista> aristas;

    public View_Edge_Connections(JFrame parent, java.util.List<String> vertices) {
        super(parent, "Agregar Aristas", true);
        this.aristas = new ArrayList<>();

        setLayout(new BorderLayout());

        // Panel de selección
        JPanel panelSeleccion = new JPanel(new GridLayout(3, 2, 5, 5));
        comboOrigen = new JComboBox<>(vertices.toArray(new String[0]));
        comboDestino = new JComboBox<>(vertices.toArray(new String[0]));
        spinnerPeso = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));

        panelSeleccion.add(new JLabel("Origen:"));
        panelSeleccion.add(comboOrigen);
        panelSeleccion.add(new JLabel("Destino:"));
        panelSeleccion.add(comboDestino);
        panelSeleccion.add(new JLabel("Peso (1-10):"));
        panelSeleccion.add(spinnerPeso);

        add(panelSeleccion, BorderLayout.NORTH);

        // Lista de aristas agregadas
        listaAristasModel = new DefaultListModel<>();
        JList<String> listaAristas = new JList<>(listaAristasModel);
        add(new JScrollPane(listaAristas), BorderLayout.CENTER);

        // Botones
        JPanel panelBotones = new JPanel();
        JButton btnAgregar = new JButton("Agregar Arista");
        JButton btnAceptar = new JButton("Aceptar");

        btnAgregar.addActionListener(e -> agregarArista());
        btnAceptar.addActionListener(e -> dispose());

        panelBotones.add(btnAgregar);
        panelBotones.add(btnAceptar);

        add(panelBotones, BorderLayout.SOUTH);

        setSize(400, 300);
        setLocationRelativeTo(parent);
    }

    private void agregarArista() {
        String origen = (String) comboOrigen.getSelectedItem();
        String destino = (String) comboDestino.getSelectedItem();
        int peso = (Integer) spinnerPeso.getValue();

        if (origen.equals(destino)) {
            JOptionPane.showMessageDialog(this, "El origen y destino no pueden ser iguales.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        aristas.add(new Arista(origen, destino, peso));
        listaAristasModel.addElement(origen + " -> " + destino + " (Peso: " + peso + ")");
    }

    public java.util.List<Arista> getAristas() {
        return aristas;
    }

    // Clase auxiliar para representar una arista
    public static class Arista {
        public String origen, destino;
        public int peso;

        public Arista(String o, String d, int p) {
            this.origen = o;
            this.destino = d;
            this.peso = p;
        }
    }
}
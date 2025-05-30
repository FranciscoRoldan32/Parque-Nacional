package views;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import model.Dto.*;

public class View_Edge_Connections extends JDialog {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox<String> comboSrc;
    private JComboBox<String> comboDest;
    private JSpinner spinnerWeight;
    private DefaultListModel<String> listaAristasModel;
    private List<EdgeDTO> edgesDTO;
    private JButton btnAdd;
    private JButton btnAccept;

 
    private EdgeConnectionsListener edgeConnectionsListener;


    public interface EdgeConnectionsListener {
        void onEdgesDefined(List<EdgeDTO> edges);
    }

    public View_Edge_Connections(JFrame parent, List<String> labels,
                                 EdgeConnectionsListener listener) {
    	
        super(parent, "Agregar Conexiones", true);
        setTitle("Definir Senderos");
        this.edgesDTO = new ArrayList<>();
        this.edgeConnectionsListener = listener;

        getContentPane().setLayout(new BorderLayout());
        setSize(400, 300);
        setLocationRelativeTo(parent);

        initComponents(labels);
    }

    private void initComponents(List<String> labels) {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));

        comboSrc  = new JComboBox<>(createModel(labels, null));
        comboDest = new JComboBox<>(createModel(labels, null));
        spinnerWeight = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));

        panel.add(new JLabel("Origen:"));
        panel.add(comboSrc);
        panel.add(new JLabel("Destino:"));
        panel.add(comboDest);
        panel.add(new JLabel("Peso ambiental (1-10):"));
        panel.add(spinnerWeight);

        getContentPane().add(panel, BorderLayout.NORTH);

        listaAristasModel = new DefaultListModel<>();
        JList<String> edgeList = new JList<>(listaAristasModel);
        getContentPane().add(new JScrollPane(edgeList), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        btnAdd = new JButton("Agregar");
        btnAccept = new JButton("Aceptar");
        btnPanel.add(btnAdd);
        btnPanel.add(btnAccept);
        getContentPane().add(btnPanel, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> onAdd());
        btnAccept.addActionListener(e -> onAccept());
    }

    private ComboBoxModel<String> createModel(List<String> items, String exclude) {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("-- Seleccionar --");
        for (String it : items) {
            if (exclude == null || !it.equals(exclude))
            	model.addElement(it);
        }
        return model;
    }

    private void onAdd() {
        String src = (String) comboSrc.getSelectedItem();
        String dst = (String) comboDest.getSelectedItem();
        int peso = (Integer) spinnerWeight.getValue();

        if (src == null || dst == null || src.equals("-- Seleccionar --") || dst.equals("-- Seleccionar --")) {
            JOptionPane.showMessageDialog(this, "Debe elegir origen y destino.");
            return;
        }

        if (src.equals(dst)) {
            JOptionPane.showMessageDialog(this, "Origen y destino no pueden ser iguales.");
            return;
        }

        for (EdgeDTO input : edgesDTO) {
            if ((input.getOrigen().equals(src) && input.getDestino().equals(dst)) ||
                (input.getOrigen().equals(dst) && input.getDestino().equals(src))) {
                JOptionPane.showMessageDialog(this, "Ya existe una conexión entre estos dos vértices.");
                return;
            }
        }

        EdgeDTO input = new EdgeDTO(src, dst, peso);
        edgesDTO.add(input);
        listaAristasModel.addElement(input.toString());
    }

    private void onAccept() {
        if (edgeConnectionsListener != null) {
            edgeConnectionsListener.onEdgesDefined(edgesDTO);
        }
        dispose();
    }

    public List<EdgeDTO> getEdgesDTO() {
        return Collections.unmodifiableList(edgesDTO);
    }
}
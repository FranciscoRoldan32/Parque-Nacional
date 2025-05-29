package views;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

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

    // Añadir campo para el listener
    private EdgeConnectionsListener edgeConnectionsListener;


    public interface EdgeConnectionsListener {
        void onEdgesDefined(List<EdgeDTO> edges);
    }

    public static class EdgeDTO {
        public final String srcLabel;
        public final String destLabel;
        public final int weight;
        public EdgeDTO(String src, String dest, int w) {
            this.srcLabel = src;
            this.destLabel = dest;
            this.weight = w;
        }
        @Override
        public String toString() {
            return srcLabel + " -> " + destLabel + " (" + weight + ")";
        }
    }

    public View_Edge_Connections(JFrame parent, List<String> labels,
                                 EdgeConnectionsListener listener) {
        super(parent, "Agregar Conexiones", true);
        setTitle("Definir Senderos");
        this.edgesDTO = new ArrayList<>();
        // Guardar el listener
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
		int w = (Integer) spinnerWeight.getValue();

		if (src == null || dst == null || src.equals("-- Seleccionar --") || dst.equals("-- Seleccionar --")) {
			JOptionPane.showMessageDialog(this, "Debe elegir origen y destino.");
			return;
		}

		if (src.equals(dst)) {
			JOptionPane.showMessageDialog(this, "Origen y destino no pueden ser iguales.");
			return;
		}

		for (EdgeDTO dto : edgesDTO) {
			if ((dto.srcLabel.equals(src) && dto.destLabel.equals(dst))
					|| (dto.srcLabel.equals(dst) && dto.destLabel.equals(src))) {
				JOptionPane.showMessageDialog(this, "Ya existe una conexión entre estos dos vértices.");
				return;
			}
		}

		EdgeDTO dto = new EdgeDTO(src, dst, w);
		edgesDTO.add(dto);
		listaAristasModel.addElement(dto.toString());
	}

    private void onAccept() {
        // Llamar al listener antes de cerrar la ventana
        if (edgeConnectionsListener != null) {
            edgeConnectionsListener.onEdgesDefined(edgesDTO);
        }
        dispose();
    }

    public List<EdgeDTO> getEdgesDTO() {
        return Collections.unmodifiableList(edgesDTO);
    }
}
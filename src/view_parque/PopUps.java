package view_parque;

import javax.swing.*;
import java.awt.event.*;

public class PopUps {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Ejemplo de Pop-up");
        JButton boton = new JButton("Realizar acción");

        boton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Aquí va la acción que se realiza primero
                System.out.println("Acción realizada");

                // Luego mostramos la explicación del siguiente paso
                JOptionPane.showMessageDialog(frame,
                    "Ahora debes dirigirte al menú principal para continuar con el siguiente paso.",
                    "Siguiente Paso",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new java.awt.FlowLayout());
        frame.add(boton);
        frame.setVisible(true);
    }
}
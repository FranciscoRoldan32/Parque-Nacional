package main;

import javax.swing.SwingUtilities;

import controller_park.Controller;
import graph.Graph;
import view_parque.view_from_park;

public class Main {
	 public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
	            view_from_park view = new view_from_park();
	            Graph graph = new Graph();
	            Controller controller = new Controller(view, graph);

	            view.setVisible(true);
	        });
	    }

}

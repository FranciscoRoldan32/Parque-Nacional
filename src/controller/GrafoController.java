package controller;

import javax.swing.SwingUtilities;
import views.View_from_park;

public class GrafoController {

	public GrafoController() {
		SwingUtilities.invokeLater(() -> {
			new View_from_park().setVisible(true);
		});
	}
		
}

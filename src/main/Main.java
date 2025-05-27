package main;

import controller.GrafoController;
import views.View_from_park;
 

public class Main {

	public static void main(String[] args) {
		try {
			View_from_park view = new View_from_park();
			new GrafoController(view);
		} catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
		}
	}

}

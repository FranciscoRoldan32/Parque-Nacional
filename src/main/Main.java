package main;


import controller.GrafoController;
 

public class Main {

	public static void main(String[] args) {
		try {
			new GrafoController();
			
		} catch (Exception e) {
			System.out.println(e);
			// TODO: handle exception
		}
	}

}

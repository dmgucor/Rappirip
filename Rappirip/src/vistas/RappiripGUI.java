package vistas;

import extras.Conexion;
import extras.Fonts;

public class RappiripGUI {
	
	private Conexion conexion;
	
	public RappiripGUI() {
		new Fonts();
		conexion = new Conexion();
		new LoginGUI(conexion, false);
	}
	
	public static void main(String[] args) {
		new RappiripGUI();
	}
}

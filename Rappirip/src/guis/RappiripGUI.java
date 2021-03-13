package guis;

import extras.Conexion;

public class RappiripGUI {
	
	private Conexion conexion;
	
	public RappiripGUI() {
		conexion = new Conexion();
		new LoginGUI(conexion);
	}
	
	public static void main(String[] args) {
		new RappiripGUI();
	}
}

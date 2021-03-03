package guis;

import extras.Conexion;

public class RappiripGUI {
	
	private LoginGUI loginScreen;
	private Conexion conexion;
	
	public RappiripGUI() {
		conexion = new Conexion();
		loginScreen = new LoginGUI(conexion);
	}
	
	public static void main(String[] args) {
		new RappiripGUI();
	}
}

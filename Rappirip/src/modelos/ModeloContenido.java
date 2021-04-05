package modelos;

import javax.swing.JPanel;

import extras.Conexion;
import vistas.EditarPerfilGUI;
import vistas.LoginGUI;

public class ModeloContenido {
	public ModeloContenido() {
		
	}
	
	public JPanel getEditarPerfilPanel(boolean darkMode, UsuarioModelo usuario) {
		return new EditarPerfilGUI(darkMode, usuario);
	}
	
	public LoginGUI getLogin(boolean darkMode) {
		return new LoginGUI(new Conexion(), darkMode);
	}
}

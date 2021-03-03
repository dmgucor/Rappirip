package modulos;

import java.sql.ResultSet;
import java.sql.SQLException;

import extras.Conexion;

public class Registro {
	private Conexion conexion;
	static char USUARIO_CLIENTE = 'C';
	static String USERNAME_EXISTENTE = "Oops! Este nombre de usuario ya existe";

	public Registro(Conexion conexion) {
		this.conexion = conexion;
	}

	public String registrarUsuario(String username, char[] password, String nombre) {
		String message = "";
		try {
			ResultSet consultaUsername = conexion.consultar("SELECT username FROM log_in WHERE username = '" + username + "';" );
			//Ya existe el nombre de usuario
			if(consultaUsername.next())
				return USERNAME_EXISTENTE;
			
			else {
				conexion.consultar("INSERT INTO log_in VALUES ('" + username + "', '" + new String(password) + "');");
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return message;
	}
}

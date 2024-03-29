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

	public String registrarUsuario(String username, char[] password, String nombre, int avatar_id) {
		String message = "";
		try {
			conexion.get();
			ResultSet consultaUsername = conexion.consultar("SELECT username FROM log_in WHERE username = '" + username + "';" );
			//Ya existe el nombre de usuario
			if(consultaUsername.next())
				return USERNAME_EXISTENTE;

			else {
				//Registrar usuario
				conexion.actualizar("INSERT INTO log_in VALUES ('" + username + "', '" + new String(password) + "');");
				conexion.actualizar("INSERT INTO usuario VALUES ('" + username + "', '" + nombre + "', " + avatar_id + ", '" + USUARIO_CLIENTE + "');");

				message = "Se ha registrado a " + username + " exitosamente!";
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
			message = "Hubo un error en el registro, intÚntalo de nuevo";
		}
		return message;
	}

	public ResultSet getAvatares() {
		conexion.get();
		ResultSet resultado = conexion.consultar("SELECT * FROM avatar;");
		return resultado;
	}
}

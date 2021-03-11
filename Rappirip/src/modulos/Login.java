package modulos;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import extras.Conexion;

public class Login {
	private Conexion conexion;

	public Login(Conexion conexion) {
		this.conexion = conexion;
	}

	public boolean login(String username, char[] password) {
		boolean logueado = false;
		try {
			conexion.get();
			ResultSet resultado = conexion.consultar("SELECT password FROM log_in WHERE username = '" + username + "';");
			if(resultado.next()) {

				//Verificar que el password es correcto
				char[] pass = resultado.getString("password").toCharArray();
				if(Arrays.equals(password, pass)) {
					resultado = conexion.consultar("SELECT nombre FROM usuario WHERE username = '" + username + "';");
					if (resultado.next()) {
						System.out.println("Bienvenido " + resultado.getString("nombre"));
						logueado = true;
					}
				}
				//Password es incorrecto
				else
					logueado =  false;
			}
			//El usuario no existe
			else 
				logueado =  false;

		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}

		return logueado;
	}
}

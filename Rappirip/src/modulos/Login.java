package modulos;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;

import extras.Conexion;

public class Login {
	private Conexion conexion;

	public Login() {
		conexion = new Conexion();
	}
	public boolean login(String username, String password) {
		boolean res = false;
		try {
			conexion.get();
			ResultSet resultado = conexion.consultar("SELECT password FROM log_in WHERE username = '" + username + "';");
			System.out.println("SELECT password FROM log_in WHERE username = '" + username + "';");
			if(resultado.next()) {
				System.out.println("SELECT nombre FROM usuario WHERE username = '" + username + "';");
				resultado = conexion.consultar("SELECT nombre FROM usuario WHERE username = '" + username + "';");
				if (resultado.next()) {
					System.out.println("Bienvenido " + resultado.getString("nombre"));
					res = true;
				}
			}
			else {
				System.out.println("Usuario o clave incorrecto");
				res =  false;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}

		return res;
	}

	public static void main(String[] args) throws IOException {
		Login login = new Login();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		boolean logged = false;
		while (!logged) {
			System.out.println("Ingrese su nombre de usuario");

			String username = reader.readLine();
			System.out.println("ingrese su contraseņa");
			String password = reader.readLine();

			logged = login.login(username, password);
		}
		reader.close();

	}
}

package modulos;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import extras.Conexion;
import modelos.ClienteDTO;

public class Login {
	private Conexion conexion;

	public Login(Conexion conexion) {
		this.conexion = conexion;
	}

	public ClienteDTO login(String username, char[] password) {
		try {
			conexion.get();
			ResultSet resultado = conexion.consultar("SELECT password FROM log_in WHERE username = '" + username + "';");
			if(resultado.next()) {

				//Verificar que el password es correcto
				char[] pass = resultado.getString("password").toCharArray();
				if(Arrays.equals(password, pass)) {
					conexion.get();

					resultado = conexion.
							consultar("select usuario.username, nombre, tipo_usuario, imagen, direccion, telefono from usuario, avatar, cliente_info where usuario.username ="
									+ " '" + username +
									"' and avatar.avatar_id = icono and cliente_info.username = " + " '" +
									username + "';");


					ClienteDTO clienteDTO = new ClienteDTO();
					clienteDTO.toDTO(resultado);
					return clienteDTO;

				}
				//Password es incorrecto
				else
					return null;
			}
			//El usuario no existe
			else 
				return null;

		}
		catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}

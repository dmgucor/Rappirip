package modelos;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.imageio.ImageIO;


public class ClienteDTO extends UsuarioModelo {
	String direccion;
	int telefono;
	TarjetaDTO[] tarjetas;

	public ClienteDTO(String username, String nombre, BufferedImage avatar, String direccion, int telefono, char tipoUsuario) {
		super(username, nombre, avatar, tipoUsuario);

		this.direccion = direccion;
		this.telefono = telefono;

		//Lugar sólo para 3 tarjetas
		tarjetas = new TarjetaDTO[2];
	}
	
	public ClienteDTO() {
		
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public void setTelefono(String direccion) {
		this.direccion = direccion;
	}

	public void setTarjetas(TarjetaDTO[] tarjetas) {
		this.tarjetas = tarjetas;
	}
	
	public void setTipoUsuario(char tipo) {
		this.tipoUsuario = tipo;
	}

	public void agregarTarjeta(TarjetaDTO tarjeta) {
		for (int i = 0; i < tarjetas.length; i++) {
			if(tarjetas[i] == null)
				tarjetas[i] = tarjeta;
		}
	}

	public void eliminarTarjeta(TarjetaDTO tarjeta) {
		for (int i = 0; i < tarjetas.length; i++) {
			if(tarjetas[i] == tarjeta)
				tarjetas[i] = null;
		}
	}

	public String getDireccion() {
		return direccion;
	}

	public int getTelefono() {
		return telefono;
	}

	public TarjetaDTO[] getTarjetas() {
		return tarjetas;
	}
	
	public char getTipoUsuario() {
		return tipoUsuario;
	}

	public ClienteDTO toDTO(ResultSet info) {
		try {
			if(info.next()) {
				setUsername(info.getString("username"));
				setAvatar(ImageIO.read(info.getBinaryStream("imagen")));
				setDireccion(info.getString("direccion"));
				setNombre(info.getString("nombre"));
				setTelefono(direccion);
				setTipoUsuario(info.getString("tipo_usuario").charAt(0));
				
				return this;
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}

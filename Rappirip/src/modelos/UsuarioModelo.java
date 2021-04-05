package modelos;

import java.awt.image.BufferedImage;

public class UsuarioModelo {
	String username;
	String nombre;
	BufferedImage avatar;
	char tipoUsuario;

	public UsuarioModelo(String username, String nombre, BufferedImage avatar, char tipoUsuario) {
		this.username = username;
		this.nombre = nombre;
		this.avatar = avatar;
		this.tipoUsuario = tipoUsuario;
	}
	
	public UsuarioModelo() {
		
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setAvatar(BufferedImage avatar) {
		this.avatar = avatar;
	}

	public String getUsername() {
		return username;
	}

	public String getNombre() {
		return nombre;
	}

	public BufferedImage getAvatar() {
		return avatar;
	}
	
	public char getTipoUsuario() {
		return tipoUsuario;
	}
}

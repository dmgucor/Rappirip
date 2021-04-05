package modelos;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TarjetaDTO {
	private int idTarjeta;
	private int numero;
	private int cvv;
	private Date expiracion;
	private String nombreCliente;

	public TarjetaDTO(int idTarjeta, int numero, int cvv, Date expiracion, String nombreCliente) {
		this.idTarjeta = idTarjeta;
		this.cvv = cvv;
		this.expiracion = expiracion;
		this.nombreCliente = nombreCliente;
		this.numero = numero;
	}

	public void setId(int id) {
		this.idTarjeta = id;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public void setCVV(int cvv) {
		this.cvv = cvv;
	}

	public void setExpiracion(Date expiracion) {
		this.expiracion = expiracion;
	}

	public void setNombreCliente(String nombre) {
		this.nombreCliente = nombre;
	}

	public int getID() {
		return idTarjeta;
	}

	public int getNumero() {
		return numero;
	}

	public int getCVV() {
		return cvv;
	}

	public Date getExpiracion() {
		return expiracion;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public TarjetaDTO toDTO(ResultSet info) {
		try {
			if(info.next()) {
				setCVV(info.getInt("cvv"));
				setExpiracion(info.getDate("expiracion"));
				setId(info.getInt("id_tarjeta"));
				setNombreCliente(info.getString("nombre_cliente"));
				setNumero(info.getInt("numero"));
				
				return this;
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}

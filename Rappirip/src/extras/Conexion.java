package extras;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {

	private Connection conexion;

	public Conexion get() {
		String cadcon = "jdbc:mysql://localhost:3306/rappirip";
		String user = "root";
		String password = "@Spoonerism1";
		try {
			setConexion(DriverManager.getConnection(cadcon, user, password));

			if (getConexion() != null) {
				System.out.println("conectado");
			}
			else {
				System.out.println("falló conexión");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return this;

	}

	public Connection getConexion() {
		return conexion;
	}

	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}

	public ResultSet consultar(String sql) {
		ResultSet resultado;
		try {
			Statement sentencia = getConexion().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			resultado = sentencia.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}        return resultado;
	}

}

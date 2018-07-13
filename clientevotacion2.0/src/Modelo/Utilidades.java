package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Utilidades {
	protected static String  ip;
	public static Connection getConexion(){
		Connection conexion = null;
		 ip = "localhost";//192.168.0.27//localhost
		try {
			Class.forName("com.mysql.jdbc.Driver");
            conexion=DriverManager.getConnection("jdbc:mysql://"+ip+"/registro","root","");   
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conexion;
	}
	
	
	//aqui hacer el voto Nulo
}
package modelo;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Formatter;

import javafx.concurrent.Task;
import application.Main;

   public class Utilidades extends Task<Integer>{
	   private Main main;
	   public Main getMain(){
			return main;
		}
		public void setMain(Main main){
			this.main = main;
		}
	// esto es de la conexion
		public static Connection getConexion(){
		Connection conexion=null;
		try {
			    Class.forName("com.mysql.jdbc.Driver");
			    conexion=DriverManager.getConnection("jdbc:mysql://"+Main.nombreServidor+"/"+Main.nombreBasedeDatos+"","root","");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conexion;
	}
	//inicio
	//para encriptar la contrasena
	public String encriptarContrasena(String password){
		    String sha1 = "";
		    try{
		        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
		        crypt.reset();
		        crypt.update(password.getBytes("UTF-8"));
		        sha1 = byteToHex(crypt.digest());
		    }
		    catch(Exception e){
		        e.printStackTrace();
		    }
		    return sha1;
		}
		private String byteToHex(final byte[] hash){
		    Formatter formatter = new Formatter();
		    for (byte b : hash){
		        formatter.format("%02x", b);
		    }
		    String result = formatter.toString();
		    formatter.close();
		    return result;
		}
	//fin codigoencriptar	
	
	
		
		//esto es para los hilos que usa la clase menu en el parpadeo
		
		@Override
		protected Integer call() throws Exception {
			System.out.println("Iniciando el hilo");
	        int i;
	        for(i= 0; i < 10000; i++){
	          if(isCancelled()){//Unicamente verificar si se cancelo el hilo
	            break;
	          }
	          System.out.println("Iteracion " + i);
	          updateMessage("Valor: "+i); 	//Este metodo actualiza el property llamado MessageProperty de la clase task
											//por lo tanto tambien se modificara cualquier vinculo con este property
	          Thread.sleep(3000); //Pausa
	        }
	        return i;
		}	
	
		
}

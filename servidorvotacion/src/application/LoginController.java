package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
public class LoginController 
{
	@FXML private TextField txtNombreUsuario;
	@FXML private PasswordField txtContrasena;
	@FXML private Button btnAceptar;
	@FXML private Button btncancelar;
	@FXML private Label lblMensaje;
	@FXML private Label lblImagenLoign;
	@FXML private CheckBox checkDescamarcar;
	@FXML private TextField textDesenmascarar;
	   
	private ObservableList<String>Usuarios = FXCollections.observableArrayList();
	private ObservableList<String>Contrasenas= FXCollections.observableArrayList();
	//public EstadisticasController miObjEstadistica;
    
	public void initialize()  {
		txtNombreUsuario.setText("administrador");
		txtContrasena.setText("administrador");
		//
		//Inicio Codigo Contraseña
		textDesenmascarar.setManaged(false);
		textDesenmascarar.setVisible(false);
	    textDesenmascarar.managedProperty().bind(checkDescamarcar.selectedProperty());
		textDesenmascarar.visibleProperty().bind(checkDescamarcar.selectedProperty());
        txtContrasena.managedProperty().bind(checkDescamarcar.selectedProperty().not());
		txtContrasena.visibleProperty().bind(checkDescamarcar.selectedProperty().not());
		//esto desenmascara el textfield
		textDesenmascarar.textProperty().bindBidirectional(txtContrasena.textProperty());
         //fin codigo contraseña
		lblImagenLoign.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("../Imagenes/Logion.png"))));
		   
		
		try {
				Class.forName("com.mysql.jdbc.Driver");
	    		Connection conexion1=DriverManager.getConnection("jdbc:mysql://"+Main.nombreServidor+"/"+Main.nombreBasedeDatos+"","root","");   
	            //Cargar informacion de las CONTRASEÑAS
		        Statement instruccionCONTRASEÑA= conexion1.createStatement(); 
	            ResultSet resultadoCONTRASEÑA= instruccionCONTRASEÑA.executeQuery("SELECT A.Nombre_Usuario ,A.Contrasena FROM `administradores` A ");
	            while(resultadoCONTRASEÑA.next())
	            {
	             Usuarios.add(resultadoCONTRASEÑA.getString("Nombre_Usuario")); 
	             Contrasenas.add(resultadoCONTRASEÑA.getString("Contrasena"));
	            }
	        conexion1.close();
	        }
		   catch (ClassNotFoundException e){
				e.printStackTrace();
			}
		   catch (SQLException e){
				e.printStackTrace();
			    e.getMessage().toString();
			    this.lblMensaje.setText(" Error en la conexion con la base de datos,contacte su proveedor de servicios.");
			 }
	}
	@Override
	public String toString(){
		return "Error en la conexion con la base de datos";
				
	}
	
	@FXML 
    private void verificarContrasena()
	{    
		        lblMensaje.setVisible(false);
		    
		        
		        for(int i=0;i<Contrasenas.size();i++)
		        {
		      	  if ((!txtContrasena.getText().equals(Contrasenas.get(i)))||(!txtNombreUsuario.getText().equals(Usuarios.get(i)))){
					    lblMensaje.setVisible(true);
						lblMensaje.setText ("Su contraseña es incorrecta !");
					    //lblMensaje.setText ("Su contraseña ha sido confirmada");
			        	lblMensaje.setTextFill (Color.rgb (210, 39, 30));
		           	  } 
				    else {
			            lblMensaje.setVisible(true);
			            //lblMensaje.setText ("Su contraseña es incorrecta !");
			        	lblMensaje.setText ("Su contraseña ha sido confirmada");
			        	lblMensaje.setTextFill (Color.rgb(21, 117, 84));
			         	
			        	try {
							//espero un poco para joder el bote
			        		lblMensaje.setText("...ESPERE UN MOMENTO...");
			        		
			        		Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						} 
			        	 //lblMensaje.setVisible(false);
			        	 llamarVentanaMenu(Usuarios.get(i));
			        	//main.cerrarVentana("EscenaPrincipal");	
			        }
		      	 }
		        txtContrasena.clear ();
		       //txtNombreUsuario.clear();
	 }
	
   
	private Main main;
    public Main getMain(){
		return main;
	}
	public void setMain(Main main){
		this.main = main;
	}
	
	public void llamarVentanaMenu(String NombreUsuario){
		main.VerVentanaMenu(NombreUsuario);
	}
	@FXML
	public void cerrarVentanaLogin(){
		main.cerrarVentana("EscenaPrincipal");	
	}
	
}
    

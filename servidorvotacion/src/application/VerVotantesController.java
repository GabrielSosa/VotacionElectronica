package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import org.controlsfx.dialog.Dialogs;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import modelo.Votantes;

public class VerVotantesController implements Initializable {
	
	private Main main;
	public Main getMain() {
		return main;
	}
	public void setMain(Main main) {
		this.main = main;
	}
	// inicio verificar luego si los nesesito todos
	@FXML private Label lblNombre;
	@FXML private Label lblId;
	@FXML private Label lblDireccion;
	@FXML private Label lblGenero;
	@FXML private Label lblEdad;
	@FXML private Label lblTelefono;
	@FXML private Label lblVoto;
	@FXML private Label lblFecha;
	//@FXML private Label lblImagen;
	@FXML private Label lblValidacion;
	
	
	//componentes para la foto del votante
	@FXML private ImageView ImgFoto;
	
	//
	@FXML private Label lblError;
	
	@FXML private Button btnAgregar;
	@FXML private Button btnEditar;
	@FXML private Button btnEliminar;
	
	@FXML private Button btnLimpiar;
	@FXML private TextField txtFecha;
	@FXML private AnchorPane anchorAgregar;
	@FXML private TableView<Votantes> tblVotantes;
	@FXML private TableColumn<Votantes, String> columnaNombre;
	@FXML private TableColumn<Votantes, String> columnaId;
	@FXML private TableColumn<Votantes, String> columnaDireccion;
	@FXML private TableColumn<Votantes, String> columnaGenero;
	@FXML private TableColumn<Votantes, Integer> columnaEdad;
	@FXML private TableColumn<Votantes, String> columnaTelefono;
	@FXML private TableColumn<Votantes, Integer> columnaVoto;
	@FXML private TableColumn<Votantes, String> columnafecha;
	@FXML private TableColumn<Votantes, String> columnaImagen;
	@FXML private TableColumn<Votantes, String> columnaValidacion;
	//fin
	@FXML private Label lblImagen1;
	@FXML private Label lblImagen2;
	@FXML private Button btnRegresar;
	
	private ObservableList<Votantes> Votantes=FXCollections.observableArrayList();
	
	//private ObservableList<String> nomRango=FXCollections.observableArrayList();
	

	public void initialize(URL location, ResourceBundle resource) {
		lblImagen1.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("../Imagenes/Votantes.png"))));
		lblImagen2.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("../Imagenes/VotantesInvertida.png"))));
		
		
		columnaNombre.setCellValueFactory(new PropertyValueFactory<Votantes, String>("nombre"));
		columnaId.setCellValueFactory(new PropertyValueFactory<Votantes, String>("id"));
		columnaDireccion.setCellValueFactory(new PropertyValueFactory<Votantes, String>("direccion"));
		columnaGenero.setCellValueFactory(new PropertyValueFactory<Votantes, String>("genero"));
		columnaEdad.setCellValueFactory(new PropertyValueFactory<Votantes, Integer>("edad"));
		columnaTelefono.setCellValueFactory(new PropertyValueFactory<Votantes, String>("telefono"));
		columnaVoto.setCellValueFactory(new PropertyValueFactory<Votantes, Integer>("voto"));
		columnafecha.setCellValueFactory(new PropertyValueFactory<Votantes, String>("fecha"));
		//columnaImagen.setCellValueFactory(new PropertyValueFactory<Votantes, String>("imagen"));
		columnaValidacion.setCellValueFactory(new PropertyValueFactory<Votantes, String>("validacion"));
		
		
		cargarVotantesBD();
       	tblVotantes.setItems(Votantes);
		tblVotantes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Votantes>() {
			@Override
			public void changed(ObservableValue<? extends Votantes> lista,Votantes oldValue, Votantes newValue) 
			{
				btnAgregar.setDisable(true);
				btnEditar.setDisable(false);
				btnEliminar.setDisable(false);
				
				if (newValue!=null) //Evitar que se ejecute el metoto cuando se elimina un registro
					mostrarInformacion(newValue);
				//System.out.println(newValue.toString());	
			}

		});
		tblVotantes.addEventHandler(MouseEvent.MOUSE_MOVED,new EventHandler<MouseEvent>() 
	       		{
	               @Override 
	               public void handle(MouseEvent e)
	               {
	            	btnAgregar.setDisable(true);
	   				btnEditar.setDisable(false);
	   				btnEliminar.setDisable(false);
	               }
	           });
			anchorAgregar.addEventHandler(MouseEvent.MOUSE_MOVED,new EventHandler<MouseEvent>() 
	         		{
	                 @Override 
	                 public void handle(MouseEvent e)
	                 {
	                	    btnAgregar.setDisable(false);
	        				btnEditar.setDisable(false);
	        				btnEliminar.setDisable(false);
	                	 
	                 }
	             });
	}
	public void mostrarInformacion(Votantes votante){
		lblNombre.setText(votante.getNombre());
		lblId.setText(votante.getId());
		lblDireccion.setText(votante.getDireccion());
		lblGenero.setText(votante.getGenero());
		lblEdad.setText(String.valueOf(votante.getEdad()));
		lblTelefono.setText(votante.getTelefono());
		lblVoto.setText(String.valueOf(votante.getVoto()));
		lblFecha.setText(votante.getFecha());
		//lblImagen.setText(votante.getImagen());
		
		try{
		  String NombreFoto=votante.getId();
		  ImgFoto.setImage(new Image("http://"+Main.nombreServidor+"/RegistroVoto/imagenesusuarios/"+NombreFoto+".jpeg"));
           //lblError.setText(" ");
			
		}
		catch(Exception e)
		{
			e.getMessage().toString();
			lblError.setText("No se encontro fotografia");
			ImgFoto.setImage(new Image("FotosVotantes/Usuario.png"));
		}
	}
	@Override
	public String toString()
	{
	    return "Error al buscar el archivo";
		
	}
	
 private void cargarVotantesBD() {
	 try 
     {
	    Class.forName("com.mysql.jdbc.Driver");//Esta vaina carga el driver
	     Connection conexion=DriverManager.getConnection("jdbc:mysql://"+main.nombreServidor+"/"+main.nombreBasedeDatos+"","root","");
         
           Statement instruccionVotantes= conexion.createStatement(); 
           ResultSet resultadoVotantes= instruccionVotantes.executeQuery(
        		   "SELECT A.nomcompleto,A.id,A.direccion,A.genero,A.edad,A.telefono,A.voto,A.fecha,A.imagen,A.validacion "+
                    "FROM `usuario` A ");
           while(resultadoVotantes.next()){
        	   Votantes ObjVotante = new Votantes(
        			   resultadoVotantes.getString("nomcompleto"),
        			   resultadoVotantes.getString("id"),
        			   resultadoVotantes.getString("direccion"),
        			   resultadoVotantes.getString("genero"),
        			   resultadoVotantes.getInt("edad"),
        			   resultadoVotantes.getString("telefono"),
        			   resultadoVotantes.getInt("Voto"),
        			   resultadoVotantes.getString("fecha"),
        			   resultadoVotantes.getString("Imagen"),
        			   resultadoVotantes.getString("validacion"));
              	Votantes.add(ObjVotante);
           
           }
         conexion.close();
	}
	catch (ClassNotFoundException e){
		e.printStackTrace();
	}catch (SQLException e) {
		e.printStackTrace();
	}
	}

 @FXML public void llamarEditarVotante() {
	 int selectedIndex = tblVotantes.getSelectionModel().getSelectedIndex();
     if (selectedIndex >= 0) {
    	 
    	 main.VerEditarVotante();
     } else {
         // Nothing selected.
         Dialogs.create().title("Error").masthead("No se ha seleccionado Votante").message("Para editar seleccione un Votante de la tabla.").showWarning();
     }
	
	}
 
 
 @FXML private void VerVentanaAgregarNuevoVotante(){
 	    main.VerAgregarNuevoVotante();
 }
 
 
 @FXML private void AyudaVotantes(){
 	       Dialogs.create().title("Ayuda Acerca de Votantes").masthead("Esta esta ventana usted puede visualizar el listado de "
 	 		+ "Votantes en nuestra base se datos asi como tambien hacer ediciones "
 	 		+ "en sus datos personales hasta eliminarlos del sistema,no se recominda eliminar votante alguno,pulse aceptar para cerrar")
 	 		.showInformation();
 }
 @FXML private void EliminarVotante() {
     int selectedIndex = tblVotantes.getSelectionModel().getSelectedIndex();
     String CodigoParaeliminar=tblVotantes.getSelectionModel().getSelectedItem().getId();
     
     if (selectedIndex >= 0) {
         Dialogs.create().title("Advertencia").masthead("Esta seguro que desea eliminar el votante seleccionado")
         .message("Pulse Aceptar para eliminar,Cancelar para cerrar esta ventana").showConfirm();
         //eliminado en la BD
         eliminarVotanteBD(CodigoParaeliminar);
         //Eliminado en el tableView
         tblVotantes.getItems().remove(selectedIndex);
         Dialogs.create().title("Votante eliminado").masthead("Votante Eliminado").showInformation();
         
     } else {
         // Nothing selected.
         Dialogs.create().title("Error").masthead("No se ha seleccionado Votante").message("Para eliminar seleccione un Votante de la tabla.").showWarning();
     }
 }
 private  void eliminarVotanteBD(String Identidad){
		try{
			 Connection conexion = modelo.Utilidades.getConexion();
			 //Cargar informacion de alumnos
	        PreparedStatement instruccionEliminar=conexion.prepareStatement("DELETE FROM `usuario` WHERE id = ? ");
	        instruccionEliminar.setString(1,Identidad);
	        instruccionEliminar.executeUpdate();
	        conexion.close();
	  } catch(Exception e){
			e.printStackTrace();
		}
	}
 //cerraventana
   @FXML public void regresarAlMenu() {
    main.cerrarVentana("VentanaVotantes");
} 



}

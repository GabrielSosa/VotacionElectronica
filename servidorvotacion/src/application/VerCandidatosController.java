package application;


import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import modelo.Candidatos;

import org.controlsfx.dialog.Dialogs;

public class VerCandidatosController  implements Initializable {
	
	private Main main;
	public Main getMain() {
		return main;
	}
	public void setMain(Main main) {
		this.main = main;
	}
	
	public static Candidatos ObjCandidato;
	
	//inicio verificar luego si los nesesito todos
	//@FXML private Label lblCodigo;
	@FXML private Label lblNombre;
	@FXML private Label lblEdad;
	@FXML private Label lblId;
	@FXML private Label LlbDireccion;
	@FXML private Label lblGenero;
	@FXML private Label lblPartido;
	@FXML private Label lblRango;
	@FXML private Label lblVotos;
	
	//mi componente Image para la foto
	@FXML private ImageView ImgFoto;
	//
	@FXML private Label lblError;
	
	@FXML private Button btnAgregar;
	@FXML private Button btnEditar;
	@FXML private Button btnEliminar;
	
	@FXML private Button btnLimpiar;
	@FXML private AnchorPane anchorAgregar;
	@FXML public  TableView<Candidatos> tblCandidatos;
	@FXML private TableColumn<Candidatos, String> columnaCodigo;
	@FXML private TableColumn<Candidatos, String> columnaNombre;
	@FXML private TableColumn<Candidatos, Integer> columnaEdad;
	@FXML private TableColumn<Candidatos, String> columnaId;
	@FXML private TableColumn<Candidatos, String> columnaGenero;
	@FXML private TableColumn<Candidatos, String> columnaDireccion;
	@FXML private TableColumn<Candidatos, String> columnaPartido;
	@FXML private TableColumn<Candidatos, String> columnaRango;
	@FXML private TableColumn<Candidatos, Integer> columnaVotos;
	//fin
	@FXML private Label lblImagen1;
	@FXML private Label lblImagen2;
	@FXML private Button btnRegresar;
	
	public static ObservableList<Candidatos> Candidatos=FXCollections.observableArrayList();
	public ObservableList<Candidatos> getCandidatos() {
		return VerCandidatosController.Candidatos;
	}

public void initialize(URL location, ResourceBundle resource){
	    btnAgregar.setDisable(true);
     	btnEditar.setDisable(true);
	    btnEliminar.setDisable(true);
	    
	    lblImagen1.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("../Imagenes/TuDecides.png"))));
		lblImagen2.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("../Imagenes/TuDecides.png"))));
		
		//columnaCodigo.setCellValueFactory(new PropertyValueFactory<Candidatos, String>("codigoCandidato"));
		columnaNombre.setCellValueFactory(new PropertyValueFactory<Candidatos, String>("Nombre"));
		columnaId.setCellValueFactory(new PropertyValueFactory<Candidatos, String>("Id"));
		columnaEdad.setCellValueFactory(new PropertyValueFactory<Candidatos, Integer>("Edad"));
		columnaGenero.setCellValueFactory(new PropertyValueFactory<Candidatos, String>("Genero"));
		columnaDireccion.setCellValueFactory(new PropertyValueFactory<Candidatos, String>("Direccion"));
		columnaPartido.setCellValueFactory(new PropertyValueFactory<Candidatos, String>("Partido"));
		columnaRango.setCellValueFactory(new PropertyValueFactory<Candidatos, String>("Rango"));
		columnaVotos.setCellValueFactory(new PropertyValueFactory<Candidatos, Integer>("Votos"));
		cargarCandidatosBD();
        
		tblCandidatos.setItems(Candidatos);
		
       tblCandidatos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Candidatos>() {
			@Override
			public void changed(ObservableValue<? extends Candidatos> lista,Candidatos oldValue, Candidatos newValue) 
			{
				btnAgregar.setDisable(true);
				btnEditar.setDisable(false);
				btnEliminar.setDisable(false);
				
				if (newValue!=null) //Evitar que se ejecute el metoto cuando se elimina un registro
					mostrarInformacion(newValue);
				    ObjCandidato=newValue;
				//System.out.println(newValue.toString());	
			}   
		});
		
       tblCandidatos.addEventHandler(MouseEvent.MOUSE_MOVED,new EventHandler<MouseEvent>() 
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
private void mostrarInformacion(Candidatos candidato){
		//lblCodigo.setText(candidato.getCodigoCandidato());
		lblNombre.setText(candidato.getNombre());
		lblEdad.setText(String.valueOf(candidato.getEdad()));
		lblGenero.setText(candidato.getGenero());
		lblId.setText(candidato.getId());
		LlbDireccion.setText(candidato.getDireccion());
		lblPartido.setText(candidato.getPartido());
		lblRango.setText(candidato.getRango());
		lblVotos.setText(String.valueOf(candidato.getVotos()));
		try{
		  //String NombreFoto=candidato.getId();
		  //ImgFoto.setImage(new Image("FotosCandidatos/"+NombreFoto+".jpg"));
		  
			//aqui obtengo la fotos de la base de datos no de la pagina de registro 
			ImgFoto.setImage(candidato.getImagen());
		  lblError.setText(" ");
			
		}
		catch(Exception e)
		{
			e.getMessage().toString();
			lblError.setText("No se encontro fotografia");
			ImgFoto.setImage(new Image("FotosCandidatos/Usuario.png"));
		}
	}
@Override public String toString(){
	    return "Error al buscar el archivo";
	}
private void cargarCandidatosBD() {
	Candidatos.clear();
	InputStream inputStream;
	try 
     {
		   Connection conexion = modelo.Utilidades.getConexion();
		   Statement instruccionCandidatos= conexion.createStatement(); 
           ResultSet resultadoCandidatos= instruccionCandidatos.executeQuery(
        		   "SELECT A.Cod_candidato,A.Nombre,B.Nombre_Rango,A.Direccion,A.Id,A.Edad,A.Genero,C.Nombre_Partido,A.Imagen,A.Votos "+
        		   "FROM `candidato` A "+ 
        		   "INNER JOIN `rangos` B "+ 
        		   "ON (A.Cod_Rango = B.Cod_Rango) "+ 
        		   "INNER JOIN `partidos` C "+
        		   "ON (A.Cod_Partido = C.Cod_Partido) ");
           while(resultadoCandidatos.next())
               {
        	   inputStream = resultadoCandidatos.getBinaryStream("Imagen");
        	   Image imagen = null;
				if(inputStream!=null){
					BufferedImage bimage = ImageIO.read(inputStream);
			        WritableImage writableImage = new WritableImage(bimage.getWidth(), bimage.getHeight());
			        imagen = SwingFXUtils.toFXImage(bimage,writableImage);
		        } 	
				 Candidatos ObjCandidato = new Candidatos(
       			   resultadoCandidatos.getString("Nombre"),
       			   resultadoCandidatos.getInt("Edad"),
       			   resultadoCandidatos.getString("Id"),
       			   resultadoCandidatos.getString("Genero"),
       			   resultadoCandidatos.getString("Direccion"), 
       			   resultadoCandidatos.getString("Nombre_Partido"),
       			   resultadoCandidatos.getString("Nombre_Rango"),
       			   imagen,
       			   resultadoCandidatos.getInt("Votos"));
             	  Candidatos.add(ObjCandidato);
			 }	
       conexion.close();
	   }
	catch (Exception e) {
		e.printStackTrace();
	}
	
}
@FXML private void VerVentanaAgregarNuevoCandidato(){
	main.VerAgregarNuevoCandidato();
}
@FXML private void VerVentanaEditarCandidato(){
     int selectedIndex = tblCandidatos.getSelectionModel().getSelectedIndex();
	//
	if(selectedIndex >= 0) {
		main.VerEditarCandidato(); 
	   new EditarCandidatoController().setIndiceEliminar(selectedIndex);
       //envio el candidato
       
	} else {
	        //Nothing selected.
	        Dialogs.create().title("Error").masthead("No se ha seleccionado candidato").message("Para Editar seleccione un candidato de la tabla.").showWarning();
	    }
}

@FXML private void Ayuda(){
	 Dialogs.create().title("Ayuda Acerca de candidatos").masthead("Esta esta ventana usted puede visualizar el listado de "
	 		+ "Candidatos en nuestra base se datos asi como tambien hacer ediciones "
	 		+ "en sus datos personales hasta eliminarlos del sistema,pulse aceptar para cerrar").showInformation();
}
@FXML private void EiminarCandidato() {
    int selectedIndex = tblCandidatos.getSelectionModel().getSelectedIndex();
    String CodigoParaeliminar=tblCandidatos.getSelectionModel().getSelectedItem().getId();
   //OJO gabo aqui estoy eliminando Candidato por el Identidad  chiva con los candidatos con mismo Id
    if (selectedIndex >= 0) {
           Dialogs.create().title("Advertencia").masthead("Esta seguro que desea eliminar Candidato")
          .message("Pulse Aceptar para eliminar,Cancelar para cerrar esta ventana").showConfirm();
        //Eliminado de la base de datos
        eliminarCandidatoBD(CodigoParaeliminar);
        //eliminado del tableview
        tblCandidatos.getItems().remove(selectedIndex);
        Dialogs.create().title("Candidato eliminado").masthead("El Candidato seleccionado ha sido eliminado").showInformation();
        limpiarComponentes();
        
    } else {
        // Nothing selected.
        Dialogs.create().title("Error").masthead("No se ha seleccionado candidato").message("Para eliminar seleccione un candidato de la tabla.").showWarning();
    }
}
private  void eliminarCandidatoBD(String Identidad){
	try{
		 Connection conexion = modelo.Utilidades.getConexion();
		 //Cargar informacion de alumnos
        PreparedStatement instruccionEliminar=conexion.prepareStatement("DELETE FROM `candidato` WHERE Id = ? ");
        instruccionEliminar.setString(1,Identidad);
        instruccionEliminar.executeUpdate();
        conexion.close();
  } catch(Exception e){
		e.printStackTrace();
	}
}
//
private void limpiarComponentes(){
	//lblCodigo.setText("");
    lblNombre.setText("");
    LlbDireccion.setText("");
	lblId.setText("");
	lblGenero.setText("");
	lblEdad.setText("");
	ImgFoto.setImage(null);
	lblRango.setText("");
	lblPartido.setText("");
	lblVotos.setText("");
	//btnAceptar.setDisable(false);
}
 //cerraventana
	   @FXML public void regresarMenu() {
	    main.cerrarVentana("VentanaCandidatos");
	  } 
}

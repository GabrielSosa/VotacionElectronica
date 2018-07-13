package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import org.controlsfx.dialog.Dialogs;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import modelo.Historial;


public class HistorialController implements Initializable{

	
	private Main main;
	public Main getMain() {
		return main;
	}
	public void setMain(Main main) {
		this.main = main;
	}
	
	@FXML private Label lblNombre;
	@FXML private Label lblId;
	@FXML private Label lblFecha;
	@FXML private Label lblPresidente;
	@FXML private Label lblAlcalde;
	@FXML private Label lblDiputado;
	@FXML private Label lblError;
	
	@FXML private Button btnRegresar;
	//mi componente Image para la foto
	@FXML public ImageView ImgFoto;
	//
	//@FXML private Label lblError;
	@FXML private TableView<Historial> tblHistorial;
	
	@FXML private TableColumn<Historial, String> columnaNombre;
	@FXML private TableColumn<Historial, String> columnaId;
	@FXML private TableColumn<Historial, String> columnaFecha;
	@FXML private TableColumn<Historial, String> columnaPresidente;
	@FXML private TableColumn<Historial, String> columnaAlcalde;
	@FXML private TableColumn<Historial, String> columnaDiputado;
	//fin
	@FXML private Label lblImagen1;
	@FXML private Label lblImagen2;
	private ObservableList<Historial> observalbleHistorial = FXCollections.observableArrayList();
	
	public void initialize(URL location, ResourceBundle resource){
		lblImagen2.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("../Imagenes/VotantesInvertida.png"))));
		lblImagen1.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("../Imagenes/TuDecides.png"))));
		
		columnaNombre.setCellValueFactory(new PropertyValueFactory<Historial, String>("Nombre"));
	    columnaId.setCellValueFactory(new PropertyValueFactory<Historial, String>("Identidad"));
	    columnaFecha.setCellValueFactory(new PropertyValueFactory<Historial, String>("Fecha"));
		columnaPresidente.setCellValueFactory(new PropertyValueFactory<Historial, String>("Presidente"));
		columnaAlcalde.setCellValueFactory(new PropertyValueFactory<Historial, String>("Alcalde"));
		columnaDiputado.setCellValueFactory(new PropertyValueFactory<Historial, String>("Diputado"));
		cargarHistorialBD();
	    tblHistorial.setItems(observalbleHistorial);
		
		tblHistorial.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Historial>() {
			@Override
			public void changed(ObservableValue<? extends Historial> lista,Historial oldValue, Historial newValue) 
			{
			    if (newValue!=null)//Evitar que se ejecute el metoto cuando se elimina un registro
					mostrarInformacion(newValue);
			     }
       });
	}
	private void mostrarInformacion(Historial historial){
		lblNombre.setText(historial.getNombre());
		lblId.setText(historial.getIdentidad());
		lblFecha.setText(historial.getFecha());
		lblPresidente.setText(historial.getPresidente());
		lblAlcalde.setText(historial.getAlcalde());
		lblDiputado.setText(historial.getDiputado());
		try{
		  String NombreFoto=historial.getIdentidad();
		  ImgFoto.setImage(new Image("http://"+Main.nombreServidor+"/RegistroVoto/imagenesusuarios/"+NombreFoto+".jpeg"));
		  lblError.setText(" ");
		}
		catch(Exception e)
		{
			e.getMessage().toString();
			lblError.setText("No se encontro fotografia");
			ImgFoto.setImage(new Image("FotosCandidatos/Usuario.png"));
		}
	
	}
	
	private void cargarHistorialBD() {
		 try 
	     {
		     Class.forName("com.mysql.jdbc.Driver");//Esta vaina carga el driver
		     Connection conexion=DriverManager.getConnection("jdbc:mysql://"+Main.nombreServidor+"/"+Main.nombreBasedeDatos+"","root","");  
		     
		     Statement instruccionHistorial= conexion.createStatement(); 
	           ResultSet resultadoHistorial= instruccionHistorial.executeQuery(
	        		   "SELECT A.Identidad, A.Nombre_Completo, A.Fecha, A.Presidente, A.Alcalde, A.Diputados  FROM `historial` A ");
	           while(resultadoHistorial.next()){
	        	   Historial ObjHistorial = new Historial(resultadoHistorial.getString("Identidad"),
	        			   resultadoHistorial.getString("Nombre_Completo"),resultadoHistorial.getString("Fecha"),
	        			   resultadoHistorial.getString("Presidente"),resultadoHistorial.getString("Alcalde"),
	        			   resultadoHistorial.getString("Diputados"));
	        	   observalbleHistorial.add(ObjHistorial);
	           
	           }
	       conexion.close();
		}
		catch (ClassNotFoundException e){
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}
}

	@Override
	public String toString()
	{
	    return "Error al buscar el archivo";
	}
	
	@FXML
	private void AyudaHistorial(){
		 Dialogs.create().title("Ayuda Acerca del Historial").masthead("En esta ventana usted puede visualizar el listado de "
		 		+ "todos los Votantes asi como el registro de por cuales candidatos voto").showInformation();
	}

	
	 //cerraventana
	   @FXML public void regresaraLMenu() {
	    main.cerrarVentana("VentanaHistorial");
	  } 
	
}

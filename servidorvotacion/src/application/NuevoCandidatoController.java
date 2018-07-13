package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;

import org.controlsfx.dialog.Dialogs;

import modelo.Candidatos;

public class NuevoCandidatoController {
   //
	public NuevoCandidatoController() {
	  }
	//
	private Main main;
	
	public Main getMain() {
		return main;
	}
	public void setMain(Main main) {
		this.main = main;
	}
//@FXML public  TextField txtCodigo;
@FXML public   TextField txtNombre;
@FXML public  TextField txtEdad;
@FXML public  TextField txtId;
@FXML public  Label  lblErrores;
@FXML public  TextField txtDireccion;
//
@FXML public  ComboBox<String>cboPartido;
@FXML public  ComboBox<String>cboRango;
//
@FXML public  RadioButton rbtFemenino;
@FXML public  RadioButton rbtMasculino;
@FXML private ToggleGroup grupoGenero;
//
@FXML private  Button btnAceptar;
@FXML private  Button btnCancelaar;
@FXML private Button btnSeleccionar;
@FXML private Button btnFoto;

//Image foto
@FXML private ImageView imgFoto;

//patrones de candidatos
	private String patronId = "[0-9]{13}";
	private Pattern patternId;
	private Matcher matcherId;
	/*
	private String patronCodigo = "[0-9]{2}";
	private Pattern patternCodigo;
	private Matcher matcherCodigo;
	*/
	
//mi archivo file
     public static File archivoActual;
    //estos HashMap me guardan los codigos y nombre de partidos y rangos esto por el problema de los string en el campo cod partidos y rangos
     private HashMap<String,Integer> codigosYnombresPartidos = new HashMap<String,Integer>();
     private HashMap<String,Integer> codigosYnombresRangos= new HashMap<String,Integer>();


private ObservableList<String> nomPartidos=FXCollections.observableArrayList();
private ObservableList<String> nomRangos=FXCollections.observableArrayList();

private ObservableList<Integer> codigoPartidos=FXCollections.observableArrayList();
private ObservableList<Integer> codigoRangos=FXCollections.observableArrayList();

public void initialize()
{
	 CargarPartidosyRangos();
	 cboPartido.setItems(nomPartidos);
	 cboRango.setItems(nomRangos);
	 
}


private void CargarPartidosyRangos()  {
	 try 
     {
		   Connection conexion = modelo.Utilidades.getConexion();
			 //INSTRUCCION PARA OBTENER LOS PARTIDOS
	       Statement instruccionPartidos= conexion.createStatement(); 
           ResultSet resultadoPartidos= instruccionPartidos.executeQuery(" SELECT A.Nombre_Partido,A.Cod_Partido FROM `partidos` A ");
           while(resultadoPartidos.next()){
        	   nomPartidos.add(resultadoPartidos.getString("Nombre_Partido"));
        	   codigoPartidos.add(resultadoPartidos.getInt("Cod_Partido")); 
        	   
        	   codigosYnombresPartidos.put(resultadoPartidos.getString("Nombre_Partido"),resultadoPartidos.getInt("Cod_Partido"));
           }
           //INSTRUCCION PARA OBTENER LOS RANGOS
           Statement instruccionRangos= conexion.createStatement(); 
           ResultSet resultadoRangos= instruccionRangos.executeQuery(" SELECT A.Nombre_Rango,A.Cod_Rango FROM `rangos` A ");
           while(resultadoRangos.next()){
        	   nomRangos.add(resultadoRangos.getString("Nombre_Rango"));
        	   codigoRangos.add(resultadoRangos.getInt("Cod_Rango")); 
        	   codigosYnombresRangos.put(resultadoRangos.getString("Nombre_Rango"),resultadoRangos.getInt("Cod_Rango"));
               
        	   }
         conexion.close();
	}
	catch (SQLException e) {
		e.printStackTrace();
	}
}
@FXML private void agregarCandidato(){
	String resultado = validarDatos();
	if (!resultado.equals("")){//Si hay errores
		lblErrores.setText(resultado);
		lblErrores.setVisible(true);
		lblErrores.setTextFill (Color.rgb (210, 39, 30));
	}else{//En caso de no haber errores
		lblErrores.setText("");
		lblErrores.setVisible(true);
		//Aqui agrego al table view
		  VerCandidatosController.Candidatos.add(new Candidatos(txtNombre.getText(),
				Integer.valueOf(txtEdad.getText()),txtId.getText(),txtDireccion.getText(),
				rbtFemenino.isSelected()?"Femenino":"Masculino",cboPartido.getSelectionModel().getSelectedItem(),
			    cboRango.getSelectionModel().getSelectedItem(),imgFoto.getImage(),0));
		 //aqui guardo en base de datos
		   guardarCandidatoBD();
		   lblErrores.setText("El candidato ha sido agregado exitosamente");
		   lblErrores.setTextFill (Color.rgb(21, 117, 84));
		    limpiarComponentes();
		try {
			Thread.sleep(1000);
			//esperar un segundo para cerrar la ventana si lo agrega 
			main.cerrarVentana("VentanaNuevoCandidato");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
public  void guardarCandidatoBD(){
    Connection conexion = modelo.Utilidades.getConexion();
	//ojo esta consulata esta mala
	try{
	    PreparedStatement instruccionAgregar = conexion.prepareStatement(
	    "INSERT INTO candidato(Nombre,Cod_Rango,Id,Direccion,Edad,Genero,Cod_Partido,Votos,Imagen) VALUES(?,?,?,?,?,?,?,?,?) ");
	   // instruccionAgregar.setInt(1,36);
        instruccionAgregar.setString(1,txtNombre.getText());
        instruccionAgregar.setString(2,String.valueOf(codigosYnombresRangos.get(cboRango.getSelectionModel().getSelectedItem())));
        instruccionAgregar.setString(3,txtId.getText());
        instruccionAgregar.setString(4,txtDireccion.getText());
        instruccionAgregar.setInt(5,Integer.valueOf(txtEdad.getText()));
        instruccionAgregar.setString(6,rbtFemenino.isSelected()?"Femenino":"Masculino");
        instruccionAgregar.setString(7,String.valueOf(codigosYnombresPartidos.get(cboPartido.getSelectionModel().getSelectedItem())));
        instruccionAgregar.setInt(8,0);
        instruccionAgregar.setBinaryStream(9,new FileInputStream(archivoActual),(int)archivoActual.length());
       //en esta Consulta le mando cero porque en los votos porque si es un nuevo candidato tiene 0 votos
        //en esta coinsulata aumento un nuevo valor al codigo de candidato NOTA:no nesesito Instanciar Stament
        //conexion.prepareStatement("SELECT `Cod_candidato` FROM `candidato` nextValue");
        //
	    // DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	    //instruccion.setString(3, dateFormatter.format(txtDtPckrFecha.getValue()));
	    int registros = instruccionAgregar.executeUpdate();
	    if(registros == 1)
	        Dialogs.create().title("Confirmar").masthead("Se ha Guardado un Nuevo Candidato").message("Se ha Agregado un Nuevo Candidato a la base de datos").showInformation();
		    else
		    Dialogs.create().title("Error").masthead("No se ha guardado candidato").message("No se ha guardado candidato en nuestra base de datos,vuelva a intentar").showWarning();
		    conexion.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
}


//inicio aqui es del file chooser
@FXML public void abrirImagen(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    //Definir filtros para que solo se puedan abrir imagenes
    FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
    FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
    fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
    archivoActual = fileChooser.showOpenDialog(main.getStgNuevoPerfil());
    if (archivoActual!=null){
	  try {
            BufferedImage bufferedImage = ImageIO.read(archivoActual);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            imgFoto.setImage(image);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
//fin file chooser
@FXML public  void verVentanaTomarFoto() {
	    main.VerTomarFoto();
	} 
public String validarDatos() {
	//if (txtCodigo.getText().isEmpty())
	//	return "El campo Codigo esta vacio";
	
	if (txtNombre.getText().isEmpty())
		return "El campo Nombre esta vacio";
	
	if (txtDireccion.getText().isEmpty())
		return "El campo Direccion esta vacio";
	if (txtId.getText().isEmpty())
		return "El campo Identidad esta vacio";
		
	if (txtEdad.getText().isEmpty())
		return "El campo Edad esta vacio";
	
	if (!rbtFemenino.isSelected() && !rbtMasculino.isSelected())
		return "Debe seleccionar un genero";
	
	if(imgFoto.getImage()==null){	
		return "Seleccione una imagen o tome una foto";
	}
	
	if (cboPartido.getSelectionModel().getSelectedIndex()<0)
		return "Debe seleccionar un Partido";
	if (cboRango.getSelectionModel().getSelectedIndex()<0)
		return "Debe seleccionar un Rango";
	try{
		Integer.valueOf(txtEdad.getText());
	   }catch(NumberFormatException e){
		return "La Edad debe ser un numero entero";
    	}
	
	patternId = Pattern.compile(patronId);
	matcherId = patternId.matcher(txtId.getText());
	
	if(!matcherId.matches())
		return "El numero de Identidad dede tener 13 caracteres \n (No incluya guiones)";
	/*
	patternCodigo = Pattern.compile(patronCodigo);
	matcherCodigo = patternCodigo.matcher(txtCodigo.getText());
	
	if(!matcherCodigo.matches())
		return "El Codigo de Candidato debe tener 2 Caracteres";
	*/
	return "";
}
public void limpiarComponentes(){
	//txtCodigo.setText("");
	txtNombre.setText("");
	txtDireccion.setText("");
	txtId.setText("");
	txtEdad.setText("");
	cboPartido.getSelectionModel().select(null);
	imgFoto.setImage(null);
	cboRango.getSelectionModel().select(null);
	rbtFemenino.setSelected(false);
	rbtMasculino.setSelected(false);
	//btnAceptar.setDisable(false);
}
//cerraventana
@FXML public void cerrarNuevoCandidato() {
 main.cerrarVentana("VentanaNuevoCandidato");
} 

}

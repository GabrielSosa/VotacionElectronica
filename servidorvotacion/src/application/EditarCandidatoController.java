package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

import modelo.Candidatos;

import org.controlsfx.dialog.Dialogs;

public class EditarCandidatoController implements Initializable {
	private Main main;
	//mis Obetos de otras clases
	private int indiceEditar;
	public int getIndiceEliminar() {
		return indiceEditar;
	}
	public void setIndiceEliminar(int indiceEditar) {
		this.indiceEditar = indiceEditar;
	}
	//objetoCandidato
	private Candidatos ObjCandidato=VerCandidatosController.ObjCandidato;
	//
	public Main getMain() {
		return main;
	}
	public void setMain(Main main) {
		this.main = main;
	}
	
	@FXML public  TextField txtCodigo;
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
	@FXML private  Button btnActualizar;
	@FXML private  Button btnCancelarActualizar;

	@FXML private Button btnSeleccionar;
	@FXML private Button btnFoto;

	//Image foto
	@FXML private ImageView imgFoto;
    //patrones de candidatos
		private String patronId = "[0-9]{13}";
		private Pattern patternId;
		private Matcher matcherId;
	//mi archivo file
		   public static File archivoActual;
		  //estos HashMap me guardan los codigos y nombre de partidos y rangos es to or el problema de los string en el campo cod partidos y rangos
		   private HashMap<String,Integer> codigosYnombresPartidos = new HashMap<String,Integer>();
		   private HashMap<String,Integer> codigosYnombresRangos= new HashMap<String,Integer>();


		private ObservableList<String> nomPartidos=FXCollections.observableArrayList();
		private ObservableList<String> nomRangos=FXCollections.observableArrayList();

		private ObservableList<Integer> codigoPartidos=FXCollections.observableArrayList();
		private ObservableList<Integer> codigoRangos=FXCollections.observableArrayList();
         
		
		@Override
		public void initialize(URL arg0, ResourceBundle arg1) {
			     mostrarInformacion(this.ObjCandidato);
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

		@FXML private void EditarCandidato(){
			String resultado = validarDatos();
			if (!resultado.equals("")){//Si hay errores
				lblErrores.setText(resultado);
				lblErrores.setVisible(true);
			}else{//En caso de no haber errores
				lblErrores.setText("");
				lblErrores.setVisible(true);
				//Aqui consulto los votos de ese candidato especifico para esos mismos ponerles ene el constructor  OjO volver x cualquier
				//AGRADO futuro
				 int VotosEncontrados=ColocarVotosACandidato(txtId.getText());
				//
				//Aqui agrego al table view
				 Candidatos a= new Candidatos(txtNombre.getText(),
						Integer.valueOf(txtEdad.getText()),txtId.getText(),rbtFemenino.isSelected()?"Femenino":"Masculino",
						txtDireccion.getText(),cboPartido.getSelectionModel().getSelectedItem(),
					    cboRango.getSelectionModel().getSelectedItem(),imgFoto.getImage(),VotosEncontrados);
				   //aqui Edito En el tableView   
				    VerCandidatosController.Candidatos.set(indiceEditar,a);
				   //aqui guardo en base de datos
				   EditarCandidatoBD(VotosEncontrados,txtId.getText());
				   
				   lblErrores.setText("El candidato ha sido actualizado exitosamente");
				   lblErrores.setTextFill (Color.rgb(21, 117, 84));
				   limpiarComponentes();
				try {
					Thread.sleep(1000);
					//esperar un segundo para cerrar la ventana si lo agrega 
					main.cerrarVentana("VentanaEditarCandidato");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}		
		private int ColocarVotosACandidato(String Id) {
			int Votos=0;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection conexion1=DriverManager.getConnection("jdbc:mysql://"+Main.nombreServidor+"/"+Main.nombreBasedeDatos+"","root","");   
	            //Cargar informacion de las CONTRASEÑAS
		        Statement instruccionVotos= conexion1.createStatement(); 
	            ResultSet resultadoVotos= instruccionVotos.executeQuery("SELECT A.Votos  FROM `Candidato` A WHERE Id='"+Id+"' ");
	            while(resultadoVotos.next()){Votos=resultadoVotos.getInt("Votos");}
	            } catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
    		 return Votos;
	   }
		private  void EditarCandidatoBD(int Votos, String Id){
			  Connection conexion = modelo.Utilidades.getConexion();
				//ojo esta consulata esta mala
				try{
					//si falla esta consulta pone esto antes de nombre   Cod_candidato = ?
					PreparedStatement instruccionModificar= 
			        conexion.prepareStatement("UPDATE `candidato` SET Nombre = ?, "+
					"Cod_Rango =?,Id= ?,Direccion= ?,Edad= ?,Genero= ?, "+
			        "Cod_Partido= ?,Imagen= ?, Votos= ?  WHERE Id= '"+Id+"' ");
					//instruccionModificar.setString(1,txtCodigo.getText());
					instruccionModificar.setString(1,txtNombre.getText());
					instruccionModificar.setString(2,String.valueOf(codigosYnombresRangos.get(cboRango.getSelectionModel().getSelectedItem())));
					instruccionModificar.setString(3,txtId.getText());
					instruccionModificar.setString(4,txtDireccion.getText());
					instruccionModificar.setInt(5,Integer.valueOf(txtEdad.getText()));
					instruccionModificar.setString(6,rbtFemenino.isSelected()?"Femenino":"Masculino");
					instruccionModificar.setString(7,String.valueOf(codigosYnombresPartidos.get(cboPartido.getSelectionModel().getSelectedItem())));
					instruccionModificar.setBinaryStream(8,new FileInputStream(archivoActual),(int)archivoActual.length());
				    //en esta instruccion le mando dero en los votos porque si es un nuevo candidato tiene 0 votos
					instruccionModificar.setInt(9,Votos);
			        int registros =instruccionModificar.executeUpdate();	    
				    if(registros == 1)
				        Dialogs.create().title("Confirmar").masthead("Se ha Modificado un Candidato").message("Se ha Actualizado la informacion del candidato selecionado").showInformation();
					    else
					    Dialogs.create().title("Error").masthead("No se ha Modificado Candidato").message("No se ha  Actualizado informacion  en la base de datos,vuelva a intentar").showWarning();
					    conexion.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		  //inicio aqui es del file chooser
		  @FXML private void EditarImagen() {
		  FileChooser fileChooser = new FileChooser();
		  //Definir filtros para que solo se puedan abrir imagenes
		  FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
		  FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
		  fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
		 
		  archivoActual = fileChooser.showOpenDialog(main.getstgEditarCandidto());
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
		private String validarDatos() {
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
		private void limpiarComponentes(){
			txtNombre.setText("");
			txtDireccion.setText("");
			txtId.setText("");
			txtEdad.setText("");
			cboPartido.getSelectionModel().select(null);
			imgFoto.setImage(null);
			cboRango.getSelectionModel().select(null);
			rbtFemenino.setSelected(false);
			rbtMasculino.setSelected(false);
		}
	    private void mostrarInformacion(Candidatos  candidato ){
			
	    	txtNombre.setText(candidato.getNombre());
			txtEdad.setText(String.valueOf(candidato.getEdad()));
			txtId.setText(candidato.getId());
			txtDireccion.setText(candidato.getDireccion());
			cboPartido.getSelectionModel().select(candidato.getPartido());
			cboRango.getSelectionModel().select(candidato.getRango());
			if(candidato.getGenero().equals("Femenino"))
				rbtFemenino.setSelected(true);
			else if(candidato.getGenero().equals("Masculino"))
				rbtMasculino.setSelected(true);
			
			try{
			  //String NombreFoto=candidato.getId();
			  //ImgFoto.setImage(new Image("FotosCandidatos/"+NombreFoto+".jpg"));
			 imgFoto.setImage(candidato.getImagen());
			 lblErrores.setText(" ");
				
			}
			catch(Exception e)
			{
				e.getMessage().toString();
				lblErrores.setText("No se encontro fotografia");
				imgFoto.setImage(new Image("FotosCandidatos/Usuario.png"));
			}
		
		}
	    
	    @FXML public  void verVentanaRetomarFoto() {
		    main.VerTomarFoto();
		} 
	    
        //cerrar la venta de editar candidatos
		@FXML private void cerrarEditarCandidato() {
			main.cerrarVentana("VentanaEditarCandidato");
			}
		
		
}

package application;

import java.util.HashMap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
public class Main extends Application {
//referencias a mis objetos de mis clases controladoras
private HistorialController VentanaHistorialController;
private MenuController VentanaMenuController;
private TemporizadorController VentanaTemporizadorController;
private VerCandidatosController VentanaCandidatosController;
private EstadisticasController VentanaEstadisticasController;
private VerVotantesController VentanaVotantesController;
//
private GraficoBarrasController VentanaBarrasController;
private BarrasPresidentesController VentanaBarrasPresidentesController;
private BarrasAlcaldesController VentanaBarrasAlcaldesController;
private BarrasDiputadosController VentanaBarrasDiputadosController;
//
public  GraficoPastelController VentanaPastelController;
private PastelPresidentesController VentanaPastelPresidentesController;
private PastelAlcaldesController VentanaPastelAlcaldesController;
private PastelDiputadosController VentanaPastelDiputadosController;
//
private NuevoCandidatoController ventanaNuevoCandidatoController;
private NuevoVotanteController ventanaNuevoVotanteController;

private EditarCandidatoController ventanaEditarCandidatoController;
private EditarVotanteController ventanaEditarVotanteController;
//
//para mi ventana Login y creditos
private LoginController VentanaPrincipalController;
private CreditosController VentanaCreditosController;
private TomarFotoController VentanaTomarFotoController;


//
public HashMap<String,Stage> misVentanas = new HashMap<String,Stage>();


//ventans de mis clases
public Stage EscenaPrincipal;
public Stage ventanaLogin;
public Stage ventanaCreditos;

//
public Stage ventanaHistorial;
public Stage ventanaCandidatos;
public Stage ventanaEstadisticas;
public Stage ventanaVotantes;
public Stage ventanaTemporizador;
public Stage ventanaIniciarVotacion;
public Stage ventanaMenu;
//
public Stage VentanaBarras;
public Stage VentanaBarrasPresidentes;
public Stage VentanaBarrasDiputados;
public Stage VentanaBarrasAlcaldes;
//
public Stage VentanaPastel;
public Stage VentanaPastelPresidentes;
public Stage VentanaPastelDiputados;
public Stage VentanaPastelAlcaldes;
public Stage VentanaEstadisticas;
//
public Stage ventanaNuevoCandidato;
public Stage ventanaNuevoVotante;

public Stage ventanaEditarCandidato;
public Stage ventanaEditarVotante;
public Stage ventanaTomarFoto;



//

//ver luego esto sirve para el file chooser
private Stage stgNuevoPerfil;
private Stage stgEditarCandidto;

//Modificar estas variableas para la conexxion y el nombre dse la base dedatos
public final static String  nombreBasedeDatos="registro";//registro//candidatos
public final static  String nombreServidor="localhost";//192.168.0.27//localhost
//
@Override public void start(Stage formularioL) {
	this.EscenaPrincipal=formularioL;
	try {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("LoginAdmin.fxml"));
		
		AnchorPane root = (AnchorPane)loader.load();
		this.VentanaPrincipalController= loader.getController();
		this.VentanaPrincipalController.setMain(this);
	    //formularioL.setFullScreen(true);
		//formularioL.setMaximized(true);
		//formularioL.setIconified(true);
		//formularioL.setOpacity(0.9);
		formularioL.setResizable(false);
		//formularioL.setAlwaysOnTop(true);
		formularioL.getIcons().add(new Image("Imagenes/logion.png"));
		Scene scene = new Scene(root,600,400);
		scene.getStylesheets().add(getClass().getResource("RegistroVotacionElectronico.css").toExternalForm());
		misVentanas.put("EscenaPrincipal",EscenaPrincipal);
		formularioL.setTitle("Usuario Contaseña");
		formularioL.centerOnScreen();
		formularioL.setScene(scene);
		formularioL.initStyle(StageStyle.UNDECORATED);
		formularioL.show();
	} 
	catch(Exception e){
		e.printStackTrace();
	    }
	}
//En este bloque estan todos los llamados a todas d la ventanas del programa
public  void VerVentanaMenu(String NombreUsuario) 
{
	try{
		ventanaMenu = new Stage();
		misVentanas.put("VentanaMenu",ventanaMenu);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Menu.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		this.VentanaMenuController = loader.getController();
		this.VentanaMenuController.setMain(this);
		//ventanaMenu.initOwner(EscenaPrincipal);
		ventanaMenu.initModality(Modality.WINDOW_MODAL);
		ventanaMenu.getIcons().add(new Image("Imagenes/logo 1.png"));
		//ventanaMenu.setOpacity(0.9);
		ventanaMenu.setResizable(false);
		ventanaMenu.setTitle("Menu principal");
		//Nombre.setText(NombreUsuario);
		VentanaMenuController.setLabelUsuario(NombreUsuario);
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("RegistroVotacionElectronico.css").toExternalForm());
		ventanaMenu.initStyle(StageStyle.UNDECORATED);
		ventanaMenu.setScene(scene);
		ventanaMenu.show();
		//cerrando la ventana Login Modificar luego o dejar asi
	    EscenaPrincipal.close();
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}
public  void VerVentanaVotantes() 
{try{
	    ventanaVotantes = new Stage();
		misVentanas.put("VentanaVotantes",ventanaVotantes);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("VerVotantes.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		this.VentanaVotantesController=loader.getController();
		this.VentanaVotantesController.setMain(this);
		ventanaVotantes.initOwner(ventanaMenu);
		ventanaVotantes.getIcons().add(new Image("Imagenes/Check.jpg"));
		ventanaVotantes.setOpacity(0.9);
		ventanaVotantes.setResizable(false);
		//ventanaVotantes.setMaximized(true);
		ventanaVotantes.initModality(Modality.WINDOW_MODAL);
		ventanaVotantes.setTitle("Votantes Globales");
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("RegistroVotacionElectronico.css").toExternalForm());
		ventanaVotantes.setScene(scene);
		ventanaVotantes.show();
		//this.VentanaLoginController.setMain(this);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}
public  void VerVentanaCandidatos() 
{try{
	    ventanaCandidatos = new Stage();
		misVentanas.put("VentanaCandidatos",ventanaCandidatos);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("VerCandidatos.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		this.VentanaCandidatosController = loader.getController();
		this.VentanaCandidatosController.setMain(this);
		ventanaCandidatos.initOwner(ventanaMenu);
		ventanaCandidatos.getIcons().add(new Image("Imagenes/Check.jpg"));
		ventanaCandidatos.setOpacity(0.9);
		ventanaCandidatos.setResizable(false);
		ventanaCandidatos.initModality(Modality.WINDOW_MODAL);
		ventanaCandidatos.setTitle(" Candidatos Totales ");
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("RegistroVotacionElectronico.css").toExternalForm());
		
		ventanaCandidatos.setScene(scene);
		ventanaCandidatos.show();
		//this.VentanaLoginController.setMain(this);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}
public  void VerVentanaHistorial() 
{try{
	    ventanaHistorial = new Stage();
		misVentanas.put("VentanaHistorial",ventanaHistorial);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Historial.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		this.VentanaHistorialController = loader.getController();
		this.VentanaHistorialController.setMain(this);
		ventanaHistorial.initOwner(ventanaMenu);
		ventanaHistorial.getIcons().add(new Image("Imagenes/Check.jpg"));
		ventanaHistorial.setOpacity(0.9);
		ventanaHistorial.setResizable(false);
		ventanaHistorial.initModality(Modality.WINDOW_MODAL);
		ventanaHistorial.setTitle(" Historial de votaciones");
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("RegistroVotacionElectronico.css").toExternalForm());
		
		ventanaHistorial .setScene(scene);
		ventanaHistorial .show();
		//this.VentanaLoginController.setMain(this);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}
public  void VerVentanaTemporizador() 
{try{
	    ventanaTemporizador = new Stage();
		misVentanas.put("VentanaTemporizador",ventanaTemporizador);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Temporizador.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		this.VentanaTemporizadorController = loader.getController();
		this.VentanaTemporizadorController.setMain(this);
		ventanaTemporizador.initOwner(ventanaMenu);
		ventanaTemporizador.getIcons().add(new Image("Imagenes/Check.jpg"));
		ventanaTemporizador.setOpacity(0.9);
		ventanaTemporizador.setResizable(false);
		ventanaTemporizador.initModality(Modality.WINDOW_MODAL);
		ventanaTemporizador.setTitle(" Iniciar Elecciones");
		Scene scene = new Scene(root,750,450);
		scene.getStylesheets().add(getClass().getResource("RegistroVotacionElectronico.css").toExternalForm());
		
		ventanaTemporizador.setScene(scene);
		ventanaTemporizador.show();
		//this.VentanaLoginController.setMain(this);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}
public  void VerVentanaEstaditicas() 
{
	try{
		ventanaEstadisticas = new Stage();
		misVentanas.put("VentanaEstadisticas",ventanaEstadisticas);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Estadisticas.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		this.VentanaEstadisticasController = loader.getController();
		this.VentanaEstadisticasController.setMain(this);
		//Cerar o no cerrar luego miro que hago
		//ventanaEstadisticas.initOwner(ventanaLogin);
		//ventanaLogin.close();
		//
		ventanaEstadisticas.initOwner(ventanaMenu);
		ventanaEstadisticas.initModality(Modality.WINDOW_MODAL);
		ventanaEstadisticas.getIcons().add(new Image("Imagenes/Estadisticas.jpg"));
		ventanaEstadisticas.setOpacity(0.9);
		ventanaEstadisticas.setResizable(false);
		ventanaEstadisticas.setTitle(" Estadisticas Generales");
		//Nombre.setText(NombreUsuario);
		//VentanaEstadisticasController.setLabelUsuario(NombreUsuario);
		Scene scene = new Scene(root,650,400);
		scene.getStylesheets().add(getClass().getResource("RegistroVotacionElectronico.css").toExternalForm());
		
		ventanaEstadisticas.setScene(scene);
		ventanaEstadisticas.show();
		
		//cerrando la ventana Login Modificar luego o dejar asi
	   // EscenaPrincipal.close();
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}
//ventanas Barras
public  void VerGraficoBarrasDialog() 
{
	try{
		VentanaBarras = new Stage();
		misVentanas.put("GraficoBarras",VentanaBarras);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("GraficoBarras.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		this.VentanaBarrasController = loader.getController();
		VentanaBarras.initOwner(VentanaEstadisticas);
		VentanaBarras.initOwner(ventanaMenu);
		VentanaBarras.getIcons().add(new Image("Imagenes/Check.jpg"));
		VentanaBarras.setOpacity(0.9);
		VentanaBarras.setResizable(false);
		VentanaBarras.initModality(Modality.WINDOW_MODAL);
		VentanaBarras.setTitle("Grafico de barras");
		Scene scene = new Scene(root,1200,500);
		//scene.getStylesheets().add(getClass().getResource("RegistroVotacionElectronico.css").toExternalForm());
		
		VentanaBarras.setScene(scene);
		VentanaBarras.show();
		VentanaBarrasController.setMain(this);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}
public  void VerGraficoBarrasAlcaldesDialog() 
{
	try{
		VentanaBarrasAlcaldes = new Stage();
		misVentanas.put("GraficoBarrasAlcaldes",VentanaBarrasAlcaldes);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("BarrasAlcaldes.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		this.VentanaBarrasAlcaldesController = loader.getController();
		VentanaBarrasAlcaldes.initOwner(VentanaEstadisticas);
		VentanaBarrasAlcaldes.initOwner(ventanaMenu);
		VentanaBarrasAlcaldes.getIcons().add(new Image("Imagenes/Check.jpg"));
		VentanaBarrasAlcaldes.setOpacity(0.9);
		VentanaBarrasAlcaldes.setResizable(false);
		VentanaBarrasAlcaldes.initModality(Modality.WINDOW_MODAL);
		VentanaBarrasAlcaldes.setTitle("Grafico de barras de Alcaldes");
		Scene scene = new Scene(root,1200,500);
		//scene.getStylesheets().add(getClass().getResource("RegistroVotacionElectronico.css").toExternalForm());
		
		VentanaBarrasAlcaldes.setScene(scene);
		VentanaBarrasAlcaldes.show();
		VentanaBarrasAlcaldesController.setMain(this);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}
public  void VerGraficoBarrasPresidentesDialog() 
{
	try{
		VentanaBarrasPresidentes = new Stage();
		misVentanas.put("GraficoBarrasPresidentes",VentanaBarrasPresidentes);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("BarrasPresidentes.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		this.VentanaBarrasPresidentesController = loader.getController();
		VentanaBarrasPresidentes.initOwner(VentanaEstadisticas);
		VentanaBarrasPresidentes.initOwner(ventanaMenu);
		VentanaBarrasPresidentes.getIcons().add(new Image("Imagenes/Check.jpg"));
		VentanaBarrasPresidentes.setOpacity(0.9);
		VentanaBarrasPresidentes.setResizable(false);
		VentanaBarrasPresidentes.initModality(Modality.WINDOW_MODAL);
		VentanaBarrasPresidentes.setTitle("Grafico de barras de presidentes");
		
		Scene scene = new Scene(root,1200,500);
		//scene.getStylesheets().add(getClass().getResource("RegistroVotacionElectronico.css").toExternalForm());
		VentanaBarrasPresidentes.setScene(scene);
		VentanaBarrasPresidentes.show();
		VentanaBarrasPresidentesController.setMain(this);
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
}
public  void VerGraficoBarrasDiputadosDialog() 
{
	try{
		VentanaBarrasDiputados = new Stage();
		misVentanas.put("GraficoBarrasDiputados",VentanaBarrasDiputados);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("BarrasDiputados.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		this.VentanaBarrasDiputadosController = loader.getController();
		VentanaBarrasDiputados.initOwner(VentanaEstadisticas);
		VentanaBarrasDiputados.initOwner(ventanaMenu);
		VentanaBarrasDiputados.getIcons().add(new Image("Imagenes/Check.jpg"));
		VentanaBarrasDiputados.setOpacity(0.9);
		VentanaBarrasDiputados.setResizable(false);
		VentanaBarrasDiputados.initModality(Modality.WINDOW_MODAL);
		VentanaBarrasDiputados.setTitle("Grafico de barras de Diputados");
		Scene scene = new Scene(root,1200,500);
		//scene.getStylesheets().add(getClass().getResource("RegistroVotacionElectronico.css").toExternalForm());
		
		VentanaBarrasDiputados.setScene(scene);
		VentanaBarrasDiputados.show();
		VentanaBarrasDiputadosController.setMain(this);
	}
	catch(Exception e){
		e.printStackTrace();
	}
}
//Ventanas Pastel
public  void VerGraficoPastelDialog() 
{
	try
	{
		VentanaPastel = new Stage();
		misVentanas.put("GraficoPastel",VentanaPastel);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("GraficoPastel.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		this.VentanaPastelController = loader.getController();
		VentanaPastel.initOwner(VentanaEstadisticas);
		VentanaPastel.initOwner(ventanaMenu);
		VentanaPastel.initModality(Modality.WINDOW_MODAL);
		VentanaPastel.getIcons().add(new Image("Imagenes/Check.jpg"));
		VentanaPastel.setTitle("Grafico Circular");
		VentanaPastel.setOpacity(0.9);
		VentanaPastel.setResizable(false);
		//aqui estaba el problema del stage del pastel
		Scene scene2=new Scene((new Group()));
		scene2.getStylesheets().add(getClass().getResource("RegistroVotacionElectronico.css").toExternalForm());
		((Group) (scene2).getRoot()).getChildren().add(VentanaPastelController.getChartGrafico());
		VentanaPastelController.getChartGrafico().setTitle(" Candidatos Totales");
		//VentanaPastel.setWidth(800);
		//VentanaPastel.setHeight(500);
	  //
		VentanaPastel.setScene(scene2);
		//formularioPastel.setFullScreen(true);
		VentanaPastel.show();
		VentanaPastelController.setMain(this);
	 } catch(Exception e){
		e.printStackTrace();
	}
}
public  void VerGraficoPastelPresidentesDialog() 
	{
		try
		{
			VentanaPastelPresidentes= new Stage();
			misVentanas.put("GraficoPastelPresidentes",VentanaPastelPresidentes);
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("PastelPresidentes.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			this.VentanaPastelPresidentesController = loader.getController();
			VentanaPastelPresidentes.initOwner(VentanaEstadisticas);
			VentanaPastelPresidentes.initOwner(ventanaMenu);
			VentanaPastelPresidentes.initModality(Modality.WINDOW_MODAL);
			VentanaPastelPresidentes.getIcons().add(new Image("Imagenes/Check.jpg"));
			VentanaPastelPresidentes.setTitle("Grafico Circular Presidencial");
			VentanaPastelPresidentes.setOpacity(0.9);
			VentanaPastelPresidentes.setResizable(false);
			//aqui estaba el problema del stage del pastel
			
			
			Scene scene2=new Scene((new Group()));
			scene2.getStylesheets().add(getClass().getResource("RegistroVotacionElectronico.css").toExternalForm());
			
			((Group) (scene2).getRoot()).getChildren().add(VentanaPastelPresidentesController.getChartGrafico());
			VentanaPastelPresidentesController.getChartGrafico().setTitle(" Candidatos Presidenciales");
			//
	        VentanaPastelPresidentes.setScene(scene2);
			//formularioPastel.setFullScreen(true);
	       VentanaPastelPresidentes.show();
	       VentanaPastelPresidentesController.setMain(this);
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}
public  void VerGraficoPastelDiputadosDialog() 
   	{
   		try
   		{
   			VentanaPastelDiputados= new Stage();
   			misVentanas.put("GraficoPastelDiputados",VentanaPastelDiputados);
   			FXMLLoader loader = new FXMLLoader();
   			loader.setLocation(getClass().getResource("PastelDiputados.fxml"));
   			AnchorPane root = (AnchorPane)loader.load();
   			this.VentanaPastelDiputadosController = loader.getController();
   			VentanaPastelDiputados.initOwner(VentanaEstadisticas);
   			VentanaPastelDiputados.initOwner(ventanaMenu);
   			VentanaPastelDiputados.initModality(Modality.WINDOW_MODAL);
   			VentanaPastelDiputados.getIcons().add(new Image("Imagenes/Check.jpg"));
   			VentanaPastelDiputados.setTitle("Grafico Circular de Diputados");
   			VentanaPastelDiputados.setOpacity(0.9);
   			VentanaPastelDiputados.setResizable(false);
   			//aqui estaba el problema del stage del pastel
   			Scene scene2=new Scene((new Group()));
   			scene2.getStylesheets().add(getClass().getResource("RegistroVotacionElectronico.css").toExternalForm());
   			
   			((Group) (scene2).getRoot()).getChildren().add(VentanaPastelDiputadosController.getChartGrafico());
   			VentanaPastelDiputadosController.getChartGrafico().setTitle("Candidatos a Diputados");
   			//formularioPastel.setWidth(1200);
   			//formularioPastel.setHeight(500);
   			Label caption = new Label("");
   	        caption.setTextFill(Color.DARKORANGE);
   	        caption.setStyle("-fx-font: 24 arial;");
      	//
   	        VentanaPastelDiputados.setScene(scene2);
   			//formularioPastel.setFullScreen(true);
       	    VentanaPastelDiputados.show();
   	        VentanaPastelDiputadosController.setMain(this);
   			
   		} catch(Exception e){
   			e.printStackTrace();
   		}
   	}     
public  void VerGraficoPastelAlcaldesDialog() 
	{
		try
		{
			VentanaPastelAlcaldes= new Stage();
			misVentanas.put("GraficoPastelAlcaldes",VentanaPastelAlcaldes);
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("PastelAlcaldes.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			this.VentanaPastelAlcaldesController = loader.getController();
			VentanaPastelAlcaldes.initOwner(VentanaEstadisticas);
			VentanaPastelAlcaldes.initOwner(ventanaMenu);
			VentanaPastelAlcaldes.initModality(Modality.WINDOW_MODAL);
			VentanaPastelAlcaldes.getIcons().add(new Image("Imagenes/Check.jpg"));
			VentanaPastelAlcaldes.setTitle("Grafico Circular de Alcaldes");
			VentanaPastelAlcaldes.setOpacity(0.9);
			VentanaPastelAlcaldes.setResizable(false);
			VentanaPastelAlcaldesController.getBotton();
			//aqui estaba el problema del stage del pastel
			Scene scene2=new Scene((new Group()));
			scene2.getStylesheets().add(getClass().getResource("RegistroVotacionElectronico.css").toExternalForm());
			
			((Group) (scene2).getRoot()).getChildren().add(VentanaPastelAlcaldesController.getChartGrafico());
			VentanaPastelAlcaldesController.getChartGrafico().setTitle("Candidatos a Alcaldes");
		   	VentanaPastelAlcaldes.setScene(scene2);
			//formularioPastel.setFullScreen(true);
	        VentanaPastelAlcaldes.show();
	        VentanaPastelAlcaldesController.setMain(this);
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}
//ventanas de nuevo candidato y votante
public  void VerAgregarNuevoCandidato() 
{
	try{
		ventanaNuevoCandidato = new Stage();
		misVentanas.put("VentanaNuevoCandidato",ventanaNuevoCandidato);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("NuevoCandidato.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		this.ventanaNuevoCandidatoController= loader.getController();
		ventanaNuevoCandidato.initOwner(ventanaCandidatos);
		ventanaNuevoCandidato.getIcons().add(new Image("Imagenes/Check.jpg"));
		ventanaNuevoCandidato.setOpacity(0.9);
		ventanaNuevoCandidato.setResizable(false);
		ventanaNuevoCandidato.initModality(Modality.WINDOW_MODAL);
		ventanaNuevoCandidato.setTitle("Agregar un nuevo Candidato ");
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("RegistroVotacionElectronico.css").toExternalForm());
		
		ventanaNuevoCandidato.setScene(scene);
		ventanaNuevoCandidato.show();
		ventanaNuevoCandidatoController.setMain(this);
	}
	catch(Exception e){
		e.printStackTrace();
	}
}
public  void VerEditarCandidato() 
{
	try{
		ventanaEditarCandidato= new Stage();
		misVentanas.put("VentanaEditarCandidato",ventanaEditarCandidato);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("EditarCandidato.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		//ventanaEditarCandidatoController
		this.ventanaEditarCandidatoController= loader.getController();
		this.ventanaEditarCandidatoController.setMain(this);
		ventanaEditarCandidato.initOwner(ventanaCandidatos);
		ventanaEditarCandidato.getIcons().add(new Image("Imagenes/Check.jpg"));
		ventanaEditarCandidato.setOpacity(0.9);
		ventanaEditarCandidato.setResizable(false);
		ventanaEditarCandidato.initModality(Modality.WINDOW_MODAL);
		ventanaEditarCandidato.setTitle("Actualizar  Candidato ");
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("RegistroVotacionElectronico.css").toExternalForm());
		
		ventanaEditarCandidato.setScene(scene);
		ventanaEditarCandidato.show();
	}
	catch(Exception e){
		e.printStackTrace();
	}
}
public  void VerAgregarNuevoVotante() 
{
	try{
		ventanaNuevoVotante= new Stage();
		misVentanas.put("VentanaNuevoVotante",ventanaNuevoVotante);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("NuevoVotante.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		this.ventanaNuevoVotanteController= loader.getController();
		ventanaNuevoVotante.initOwner(ventanaVotantes);
		ventanaNuevoVotante.getIcons().add(new Image("Imagenes/Check.jpg"));
		ventanaNuevoVotante.setOpacity(0.9);
		ventanaNuevoVotante.setResizable(false);
		ventanaNuevoVotante.initModality(Modality.WINDOW_MODAL);
		ventanaNuevoVotante.setTitle("Agregar un nuevo Votante ");
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("RegistroVotacionElectronico.css").toExternalForm());
		
		ventanaNuevoVotante.setScene(scene);
		ventanaNuevoVotante.show();
		ventanaNuevoVotanteController.setMain(this);
	}
	catch(Exception e){
		e.printStackTrace();
	}
}
public  void VerEditarVotante() 
{
	try{
		
		ventanaEditarVotante= new Stage();
		misVentanas.put("VentanaEditarVotante",ventanaEditarVotante);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("EditarVotante.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		this.ventanaEditarVotanteController= loader.getController();
		ventanaEditarVotante.initOwner(ventanaVotantes);
		ventanaEditarVotante.getIcons().add(new Image("Imagenes/Check.jpg"));
		ventanaEditarVotante.setOpacity(0.9);
		ventanaEditarVotante.setResizable(false);
		ventanaEditarVotante.initModality(Modality.WINDOW_MODAL);
		ventanaEditarVotante.setTitle("Editar Votante seleccionado ");
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("RegistroVotacionElectronico.css").toExternalForm());
		
		ventanaEditarVotante.setScene(scene);
		ventanaEditarVotante.show();
		this.ventanaEditarVotanteController.setMain(this);
	}
	catch(Exception e){
		e.printStackTrace();
	}
}

public  void VerVentanaCreditos() 
{
	try{
		
		ventanaCreditos= new Stage();
		misVentanas.put("VentanaCreditos",ventanaCreditos);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Creditos.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		this.VentanaCreditosController= loader.getController();
		ventanaCreditos.initOwner(ventanaMenu);
		ventanaCreditos.getIcons().add(new Image("Imagenes/Check.jpg"));
		ventanaCreditos.setOpacity(0.9);
		ventanaCreditos.setResizable(false);
		ventanaCreditos.initModality(Modality.WINDOW_MODAL);
		ventanaCreditos.setTitle("Creditos");
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("RegistroVotacionElectronico.css").toExternalForm());
		
		ventanaCreditos.setScene(scene);
		ventanaCreditos.show();
		ventanaCreditos.centerOnScreen();
	    this.VentanaCreditosController.setMain(this);
	}
	catch(Exception e){
		e.printStackTrace();
	}
}

public  void VerTomarFoto() 
{
	try{
		ventanaTomarFoto= new Stage();
		misVentanas.put("VentanaTomarFoto",ventanaTomarFoto);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("TomarFoto.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		this.VentanaTomarFotoController= loader.getController();
	    this.VentanaTomarFotoController.setMain(this);
		ventanaTomarFoto.initOwner(ventanaCandidatos);
		ventanaTomarFoto.getIcons().add(new Image("Imagenes/Check.jpg"));
		//ventanaTomarFoto.initStyle(StageStyle.UNDECORATED);
	    ventanaTomarFoto.setOpacity(0.9);
		ventanaTomarFoto.setResizable(false);
		ventanaTomarFoto.initModality(Modality.WINDOW_MODAL);
		ventanaTomarFoto.setTitle("Sonrrie");
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("RegistroVotacionElectronico.css").toExternalForm());
		ventanaTomarFoto.setScene(scene);
		ventanaTomarFoto.show();
		ventanaTomarFoto.centerOnScreen();
}
	catch(Exception e){
		e.printStackTrace();
	}
}

//Final de todos los llamados

//Inico esto es del file chooser
public Stage getStgNuevoPerfil() {
	return stgNuevoPerfil;
}

public Stage getStgPrimaryStage() {
	return EscenaPrincipal;
}
public void abrirStageNuevoVotante(){
	if(stgNuevoPerfil == null) //En caso de no estar cargado el formulario lo carga por primera vez
		VerAgregarNuevoVotante();
	stgNuevoPerfil.showAndWait();
}

public Stage getstgEditarCandidto() {
	return stgEditarCandidto;
}

public void abrirStageEditarCandidato(){
	if(stgEditarCandidto== null) //En caso de no estar cargado el formulario lo carga por primera vez
		VerEditarCandidato();
	stgEditarCandidto.showAndWait();
}
//fin de los del file chooser

//Metodo para cerrar las ventanas
 public void cerrarVentana(String nombreVentana){
	misVentanas.get(nombreVentana).close();
}
	public static void main(String[] args) {
		launch(args);
	}
}

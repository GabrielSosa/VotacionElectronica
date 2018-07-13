package Project;
import java.io.IOException;
import java.util.HashMap;

import Modelo.Historial;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class Main extends Application {
	
	private ValidacionController VentanaValidacionController;
	private LoginController loguearseController;
	

	public 	PresidenteController VentanaPresidenteController;
	public 	AlcaldeController 	 VentanaAlcaldeController;
	public DiputadoController  ventanaDiputadoController;
	public ResultadoController ventanaResultadoController;
	
	
	public Stage EscenaPrincipal;
	public Stage ventanaValidacion;
	public Stage ventanaPresidente;
	public Stage ventanaAlcalde;
	public Stage ventanaDiputado;
	public Stage ventanaResultado;
	
	
	private HashMap<String,Stage> misVentanas = new HashMap<String,Stage>();
	
	Historial a = new Historial();
	
	
	

	@Override
	public void start(Stage formularioL) {
		this.EscenaPrincipal=formularioL;
		//Parent root = null;
		try {
			
			//root = FXMLLoader.load(getClass().getResource("WebCamPreview.fxml"));
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("Login.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			this.loguearseController= loader.getController();
			this.loguearseController.setMain(this);
			//this.loguearseController.setMain(this);
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("RegistroVotacionElectronico.css").toExternalForm());
			formularioL.setTitle("Lector de camara");
			formularioL.setScene(scene);
			//this.loguearseController.setMain(this);
			misVentanas.put("Principal",EscenaPrincipal);
			formularioL.centerOnScreen();
			formularioL.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void VerValidacion(String NombreUsuario, String id, String pin)
	{
	try {
		ventanaValidacion = new Stage();
		misVentanas.put("Validacion",ventanaValidacion);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Validacion.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		this.VentanaValidacionController = loader.getController();
		this.VentanaValidacionController.setMain(this);
		ventanaValidacion.initOwner(EscenaPrincipal);
		ventanaValidacion.initModality(Modality.WINDOW_MODAL);
		VentanaValidacionController.setLabelUsuario(NombreUsuario);
		a.setNombre(NombreUsuario);
		a.setId(id);
		VentanaValidacionController.setPin(pin);
		VentanaValidacionController.VerFoto(id);
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("RegistroVotacionElectronico.css").toExternalForm());
		ventanaValidacion.setScene(scene);
		ventanaValidacion.show();
		
	} catch(Exception e) {
		e.printStackTrace();
	}
	}
	
	public void VentanaPresidente()
	{
	try {
		ventanaPresidente = new Stage();
		misVentanas.put("presidente", ventanaPresidente);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Presidente.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		this.VentanaPresidenteController = loader.getController();
		this.VentanaPresidenteController.setMain(this);
		ventanaPresidente.initOwner(EscenaPrincipal);
		ventanaPresidente.initModality(Modality.WINDOW_MODAL);
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("RegistroVotacionElectronico.css").toExternalForm());
		ventanaPresidente.setScene(scene);
		ventanaPresidente.show();
		
	} catch(Exception e) {
		e.printStackTrace();
	}
	}
	
	public void VentanaAlcalde(String Presidente)
	{
	try {
		
		ventanaAlcalde = new Stage();
		misVentanas.put("alcalde", ventanaAlcalde);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Alcalde.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		this.VentanaAlcaldeController  = loader.getController();
		this.VentanaAlcaldeController.setMain(this);
		a.setPresidente(Presidente);
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("RegistroVotacionElectronico.css").toExternalForm());
		ventanaAlcalde.setScene(scene);
		ventanaAlcalde.show();
		ventanaPresidente.close();
		
	} catch(Exception e) {
		e.printStackTrace();
	}
	}
	
	public void VentanaDiputado(String Alcalde)
	{
	try {
		ventanaDiputado = new Stage();
		misVentanas.put("diputado", ventanaDiputado);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Diputado.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		this.ventanaDiputadoController = loader.getController();
		this.ventanaDiputadoController.setMain(this);
		a.setAlcalde(Alcalde);
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("RegistroVotacionElectronico.css").toExternalForm());
		ventanaDiputado.setScene(scene);
		ventanaDiputado.show();
		ventanaAlcalde.close();
		
		
	} catch(Exception e) {
		e.printStackTrace();
	}
	}
	
	public void VentanaResultado()
	{
	try {
		ventanaResultado = new Stage();
		misVentanas.put("resultado", ventanaResultado);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("Resultado.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		this.ventanaResultadoController = loader.getController();
		this.ventanaResultadoController.setMain(this);
		ventanaResultadoController.nombreCompleto(a.getNombre());
		ventanaResultadoController.Presidente(a.getPresidente());
		ventanaResultadoController.MostrarAlcalde(a.getAlcalde());
		ventanaResultadoController.MostrarDiputados(a.getDiputados());
		ventanaResultadoController.numeroIdendtidad(a.getId());
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("RegistroVotacionElectronico.css").toExternalForm());
		ventanaResultado.setScene(scene);
		ventanaResultado.show();
		ventanaDiputado.close();
		
		
	} catch(Exception e) {
		e.printStackTrace();
	}
	}
	
    
	
	public static void main(String[] args) {
		launch(args);
	}
}


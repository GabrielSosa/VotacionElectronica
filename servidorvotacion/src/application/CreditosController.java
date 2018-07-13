package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CreditosController implements Initializable {
	@FXML private ImageView ImgFoto;
	@FXML private Button btnAceptar;
	@FXML private ImageView ImgFondo;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//ImgFondo.setImage((new Image(getClass().getResourceAsStream("../Imagenes/FondoGeneral.jpg"))));
		ImgFoto.setImage((new Image(getClass().getResourceAsStream("../Imagenes/fonfoVida.jpg"))));
		
	}
	
	private Main main;
	public Main getMain() {
		return main;
	}
	public void setMain(Main main) {
		this.main = main;
	}
	
	//cerraventana
	   @FXML public void regresarAlMenu() {
	    main.cerrarVentana("VentanaCreditos");
	  } 
}

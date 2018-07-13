package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TemporizadorController implements Initializable {

	
@FXML private ComboBox<Integer> cboHora;
@FXML private ComboBox<Integer> cboMinutos;
private ObservableList<Integer> hora;
private ObservableList<Integer> minutos;
private MenuController menu =  new MenuController();

@FXML private Label lblImagen1;

	@FXML private Button btnRegresar;
	private Main main;
	public Main getMain() {
		return main;
	}
	public void setMain(Main main) {
		this.main = main;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
//		 
		
		hora = FXCollections.observableArrayList();
	    minutos = FXCollections.observableArrayList();
	    
	   // menu.hora = cboHora.getSelectionModel().getSelectedItem();
		//int minutos = cboMinutos.getSelectionModel().getSelectedItem();
		//menu.lblFinaliza.setText("funciona");
		  
	    lblImagen1.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("../Imagenes/fechayHora.png"))));
		
	    for (int i = 1; i <= 12; i++) {
	    		hora.add(i);
		}
	    
	    cboHora.setItems(hora);
	    for (int i = 1; i <= 60; i++) {
	    	minutos.add(i);
		}
	    cboMinutos.setItems(minutos);
	    
	
	} 

	
	
	//cerraventana
	   @FXML public void regresarAlMenu() {
	      main.cerrarVentana("VentanaTemporizador");
		  
	  }
	
	
	
}

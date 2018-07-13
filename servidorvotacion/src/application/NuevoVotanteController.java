package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class NuevoVotanteController {
	private Main main;
	public Main getMain() {
		return main;
	}
	public void setMain(Main main) {
		this.main = main;
	}


@FXML private TextField txtNombre;
@FXML private TextField txtId;
@FXML private TextField txtDireccion;
@FXML private TextField txtgenero;
@FXML private TextField txtEdad;
@FXML private TextField txtTelefono;
@FXML private TextField txtFecha;
@FXML private TextField txtVotos;

@FXML private Button btnAceptar;
@FXML private Button btnCancelaar;




//cerraventana
@FXML public void cerrarAgregarVotante() {
   main.cerrarVentana("VentanaNuevoVotante");
} 
	
	
}

package application;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class EditarVotanteController {
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
//@FXML private TextField txtgenero;
@FXML private TextField txtEdad;
@FXML private TextField txtTelefono;
@FXML private TextField txtFecha;
@FXML private TextField txtVotos;
@FXML public  Label  lblErrores;
@FXML private RadioButton rbtFemenino;
@FXML private RadioButton rbtMasculino;
@FXML private ToggleGroup grupoGenero;
@FXML private Button btnEditar;
@FXML private Button btnCancelar;



//cerraventana
@FXML public void cerrarEditarVotante() {
   main.cerrarVentana("VentanaEditarVotante");
} 
	
	
	
	

}

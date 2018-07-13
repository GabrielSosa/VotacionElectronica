package Project;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Modelo.Utilidades;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ResultadoController implements Initializable {
@FXML public VBox vBoxDiputados;
	
@FXML public Label lblNomVotante;
@FXML public Label lblFecha;
@FXML public Label lblNomPresidente;
@FXML public Label lblNomAlcalde;
@FXML public Label lblNomDiputado;
@FXML public Button btnEnviar;

private String fecha;
private String nombreComp;
private String id;
private String nombrePresi;
private String nombreAlcal;
private String Diputados = "";

public Main main;


public Main getMain() {
	return main;
}

public void setMain(Main main) {
	this.main = main;
}


public void numeroIdendtidad(String id){
	this.id = id;
	
}

public void nombreCompleto(String nombre){
	this.nombreComp = nombre;
	lblNomVotante.setText(nombreComp);
}

public void Presidente(String nombre){
	this.nombrePresi = nombre;
	lblNomPresidente.setText(nombrePresi);
	
}

public void MostrarAlcalde(String nombre){
	this.nombreAlcal = nombre;
	lblNomAlcalde.setText(nombreAlcal);
}

public void MostrarDiputados(ArrayList<String> diputados){
	
	for(int i =0; i<diputados.size(); i++){
		Label label = new Label();
		label.setText(diputados.get(i));
		label.setPadding(new Insets(20,0,0,0));
		label.setTextFill(Color.rgb(248,241,241));
		label.setFont(new Font(22));
		vBoxDiputados.getChildren().add(label);
		Diputados += diputados.get(i)+", ";
	}
	
}

public void EnviarHistorial(){
	try
    {
			Connection oConeccion = Utilidades.getConexion();
            PreparedStatement rs =  oConeccion.prepareStatement("insert into historial(Identidad, Nombre_Completo, Fecha, Presidente, Alcalde, Diputados) "+
            													 "values(?, ?, ?, ?, ?, ?)");
            rs.setString(1, id);
            rs.setString(2, nombreComp);
            rs.setString(3, fecha);
            rs.setString(4, nombrePresi);
            rs.setString(5, nombreAlcal);
            rs.setString(6, Diputados);
            rs.execute();
            
           // oConeccion.commit();
            oConeccion.close();
    }
    catch(Exception e)
    {
           e.printStackTrace();
          
    }
}

public void EnviarVoto(){
	try
    {
			Connection oConeccion = Utilidades.getConexion();
            PreparedStatement rs =  oConeccion.prepareStatement("UPDATE usuario SET voto = "+1+" WHERE `id` = '"+id+"'");
            rs.executeUpdate();
            //oConeccion.commit();
            oConeccion.close();
    }
    catch(Exception e)
    {
           e.printStackTrace();
           System.out.print("Mal");         
    }
}




@FXML
public void TerminarVotacion(){
	
	EnviarHistorial();
	EnviarVoto();
	Stage formularioL =  new Stage();
	main.start(formularioL);
	main.ventanaResultado.close();
}

public void LLenarArreglo(){
	
}

@Override
public void initialize(URL arg0, ResourceBundle arg1) {
	
	fecha = "17/04/2015";
	lblFecha.setText(fecha);
	
}


	
}

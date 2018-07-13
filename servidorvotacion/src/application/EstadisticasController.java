package application;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.controlsfx.dialog.Dialogs;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.stage.Modality;
import javafx.stage.Stage;
public class EstadisticasController 
{
	 @FXML private Button btnBarras;
	 @FXML private Button btnBarrasPresidentes;
	 @FXML private Button btnBarrasAlcaldes;
	 @FXML private Button btnBarrasDiputados;
	 
	 @FXML private Button btnPastel;
	 @FXML private Button btnPastelPresidentes;
	 @FXML private Button btnPastelAlcaldes;
	 @FXML private Button btnPastelDiputados;
	 
	 @FXML private Button btnCerrar;
	 @FXML private Label lblUsuario;
    
	   private String Nombretemporal;
	   //codigo del parametro del main
	    private Main main3;
	   //metod get y set
		public Main getMain(){
			return main3;
		}
		public void setMain(Main main)	{
			this.main3 = main;
		}
	
	   //GENERALES
		@FXML public void llamarGraficoBarras() {
		   main3.VerGraficoBarrasDialog();
	    } 
		
		@FXML public void llamarGraficoPastel(){
			  main3.VerGraficoPastelDialog();
		    } 
		//BARRAS
		@FXML public void llamarGraficoBarrasPresidentes() {
		   main3.VerGraficoBarrasPresidentesDialog();
	    }  
		@FXML public void llamarGraficoBarrasDiputados() {
		   main3.VerGraficoBarrasDiputadosDialog();
	    }
		@FXML public void llamarGraficoBarrasAlcaldes() {
		   main3.VerGraficoBarrasAlcaldesDialog();
	   }  
	    //PASTELES
		@FXML public void llamarGraficoPastelPresidentes(){
		  main3.VerGraficoPastelPresidentesDialog();
	    } 
		@FXML public void llamarGraficoPastelDiputados(){
		  main3.VerGraficoPastelDiputadosDialog();
	    } 
		@FXML public void llamarGraficoPastelAlcaldes(){
		  main3.VerGraficoPastelAlcaldesDialog();
	    } 
		
		
		@FXML
		private void AyudaEstadisticas(){
			 Dialogs.create().title("Ayuda Acerca de las estadisticas").masthead("Esta esta ventana usted puede seleccionar un tipo de grafico"
			 		+ " con el cual usted puede ver los avances matemáticos de la elecciones en curso,pulse aceptar para cerrar ").showInformation();
		}

		
		
	    @FXML
	    public void cerrarVentanaEstadisticas(){
		    main3.cerrarVentana("VentanaEstadisticas");
		} 
	  
	   
}

package application;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class PastelAlcaldesController {
	@FXML public PieChart chartGrafico;//= new PieChart(); 
	@FXML private Button btnAceptarP;
	
	
	private Main main;
    
    private Scene Escena;
   
    public Main getMain(){
		return main;
	}
	public void setMain(Main main){
		this.main = main;
	}
	public Button getBotton(){
		return this.btnAceptarP;
	}
	//
	public void setEscena(Scene escena){
		this.Escena = escena;
	}
    public Scene getEscena(){
	  return this.Escena;
	}

    public PieChart getChartGrafico() {
		return chartGrafico;
	}
	public void setChartGrafico(PieChart chartGrafico) {
		this.chartGrafico = chartGrafico;
	}
     
	  private ObservableList<String> Candidatos = FXCollections.observableArrayList();
    private ObservableList<Integer> Votos = FXCollections.observableArrayList();
    private ObservableList<PieChart.Data> pieChartDatos = FXCollections.observableArrayList();
    	/*//Atributos para conexion con la BD
  		private String url;
  	    private Connection conexion;   
  	    private String usuario="DESARROLLO";
  	    private String contrasena="oracle";
  	    private String SID="xe";
  	    private String host="localhost";
  	    private String puerto="1521";
    */
   	  @FXML
    private void initialize() {
		colocarDiputados();

    }
  
	private void colocarDiputados(){
        ObtenerCandidatosBD(); 
		chartGrafico = new PieChart(pieChartDatos);
  	   	chartGrafico.setClockwise(true);
  		chartGrafico.setLabelLineLength (10);
  		chartGrafico.setLegendSide (Side.BOTTOM);
  	 }
    private void ObtenerCandidatosBD() {
    	try {
    		    Class.forName("com.mysql.jdbc.Driver");
    		    Connection conexion1=DriverManager.getConnection("jdbc:mysql://"+main.nombreServidor+"/"+main.nombreBasedeDatos+"","root","");   
              //Cargar informacion de los candidatos totales(NO TIENE SENTIDO VERDAD)
	        Statement instruccionGENERAL= conexion1.createStatement(); 
            ResultSet resultadoGENERAL= instruccionGENERAL.executeQuery(
            		"SELECT A.Nombre ,B.Nombre_Rango, C.Nombre_Partido,A.Votos "+
	            		"FROM `candidato` A "+
	            		"INNER JOIN `rangos` B "+
	            		"ON (A.Cod_Rango = B.Cod_Rango) "+
	            		"INNER JOIN `partidos` C "+
	            		"ON (A.Cod_Partido = C.Cod_Partido)"+
	            		"WHERE (Nombre_Rango='Alcalde') ");
            while(resultadoGENERAL.next())
            {
            	Candidatos.add(resultadoGENERAL.getString("Nombre"));
            	Votos.add(resultadoGENERAL.getInt("Votos"));
            }
            //agregando datos al pie
            for(int i=0;i<Candidatos.size();i++)
	  		{
	  			pieChartDatos.add(new PieChart.Data(Candidatos.get(i),Votos.get(i)));
	  		}
	     	
          conexion1.close();
        }catch (ClassNotFoundException e){
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@FXML
    private void CerrarPastelAlcaldes() 
    {
	   main.cerrarVentana("GraficoPastelAlcaldes");
    }
    
}

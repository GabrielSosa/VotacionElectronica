package application;


import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.util.Duration;

public class GraficoBarrasController 
{       
	    @FXML private Button btnAceptar;
	    @FXML private CategoryAxis xAxis;
	    @FXML private NumberAxis yAxis;
	    @FXML private BarChart<String,Integer> bcGrafcoBarras;
	    @FXML private Label  Etiquetaflotante ;//= new Label("");
		
	    /*
	    * Ojo aqui nesesito trabajar solo con los campos de la BD
	    * Primer Nombre
	    * Primer Apellido
	    * Partido
	    * Cargo O rango
	    * y
	    * votos
	    * 
	    * los demas campos casi qe me valen
	    * 
	    *    * */
	    private ObservableList<String> Candidatos = FXCollections.observableArrayList();
	    private ObservableList<Integer> Votos = FXCollections.observableArrayList();
	    private ObservableList<String> Candidatos2 = FXCollections.observableArrayList();
	    private ObservableList<Integer> Votos2 = FXCollections.observableArrayList();
	    private ObservableList<String> Candidatos3 = FXCollections.observableArrayList();
	    private ObservableList<Integer> Votos3 = FXCollections.observableArrayList();
       
	    private XYChart.Series<String, Integer> seriesPresidentes = new XYChart.Series<>();
	    private XYChart.Series<String, Integer> seriesAlcades = new XYChart.Series<>();
	    private XYChart.Series<String, Integer> seriesDiputados = new XYChart.Series<>();
	    
	    
	    
	    private ObservableList<String> CandidatosTotales = FXCollections.observableArrayList();
	    private ObservableList<Integer> VotosTotales = FXCollections.observableArrayList();
	    
	    @FXML
	    private void initialize()
	    {   
        	ColocarCandidatos();
        	//
        	ObtenerCandidatosUniversales();
        	//
        	Etiquetaflotante.setTextFill(Color.FORESTGREEN);
   	        Etiquetaflotante.setStyle("-fx-font: 18 arial;");
         for ( Series<String, Integer> data : bcGrafcoBarras.getData())
   	        {
   	    	data.getChart().addEventHandler(MouseEvent.MOUSE_MOVED,new EventHandler<MouseEvent>() 
   	            		{
   	                    @Override 
   	                    public void handle(MouseEvent e)
   	                    {
   	                     //ActualizarPresidentesBD();
   	                     Etiquetaflotante.setVisible(true);	
   	                   	 Etiquetaflotante.setTranslateX(e.getX());
   	                     Etiquetaflotante.setTranslateY(e.getY());
   	                     Etiquetaflotante.setText(CandidatosTotales+" Tienen "+(VotosTotales+" Votos"));
   	                     
   	                 }
   	                });
   	    	 
   	        }
	     }
	   

		private void ObtenerCandidatosUniversales() {
	    		try {
	    			//OJO AQUI COPIE PARA LA MODIFOCAION DE LAS ETIQUETAS 
	    			Class.forName("com.mysql.jdbc.Driver");//Esta vaina carga el driver
	    			   Connection conexion=DriverManager.getConnection("jdbc:mysql://"+Main.nombreServidor+"/"+Main.nombreBasedeDatos+"","root","");//voy a utilizar solo una conceccion   
	    				
	    			 Statement instruccion= conexion.createStatement(); 
	 		            ResultSet resultadoPresidentes= instruccion.executeQuery(
	 	        	    		"SELECT A.Nombre ,B.Nombre_Rango, C.Nombre_Partido,A.Votos "+
	 	        	    		"FROM `candidato` A "+
	 	        	    		"INNER JOIN `rangos` B "+ 
	 	        	    		"ON (A.Cod_Rango = B.Cod_Rango) "+ 
	 	        	    		"INNER JOIN `partidos` C "+
	 	        	    		"ON (A.Cod_Partido = C.Cod_Partido) Order by Nombre_Partido ");
	 	  		        while(resultadoPresidentes.next()){
	 	  		        	CandidatosTotales.add(resultadoPresidentes.getString("Nombre"));
	 	  		        	VotosTotales.add(resultadoPresidentes.getInt("Votos"));
	 	  		        }
	    		
	    		}catch (SQLException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
		         		
		}
	@SuppressWarnings("unchecked")
		private void ColocarCandidatos()
	    { 
	    	 seriesPresidentes.setName ("Presidentes");
	         ObtenerPresidentesBD();     
	         seriesAlcades.setName ("Alcaldes");
	         ObtenerAlcaldesBD();
	  		 seriesDiputados.setName ("Diputados");
		     ObtenerDiputadosBD();
		  	 xAxis.setLabel("Candidatos");
		  		yAxis.setLabel("Votos");
		  		yAxis.setAnimated (true);
		  		xAxis.setAnimated (true);
		  		bcGrafcoBarras.setTitle("Grafico de Barras de todos los Candidatos ");
		  		bcGrafcoBarras.getData().addAll(seriesPresidentes,seriesAlcades,seriesDiputados);
		  	    bcGrafcoBarras.setBarGap(3);
		  	    bcGrafcoBarras.setCategoryGap (10);
		  	   bcGrafcoBarras.setAnimated(true);
       	   
		  	    xAxis.setTickLabelRotation(30);
	         
	    }
   
	    private void ObtenerDiputadosBD() 
	    {
	    	try {
			    Class.forName("com.mysql.jdbc.Driver");
				Connection conexion1=DriverManager.getConnection("jdbc:mysql://"+Main.nombreServidor+"/"+Main.nombreBasedeDatos+"","root","");
			    //Cargar informacion de los ALCALDES
		        Statement instruccionDIPUTADOS= conexion1.createStatement(); 
	            ResultSet resultadoDIPUTADOS= instruccionDIPUTADOS.executeQuery(
	            		"SELECT A.Nombre ,B.Nombre_Rango, C.Nombre_Partido,A.Votos "+
	            		"FROM `candidato` A "+
	            		"INNER JOIN `rangos` B "+
	            		"ON (A.Cod_Rango = B.Cod_Rango) "+
	            		"INNER JOIN `partidos` C "+
	            		"ON (A.Cod_Partido = C.Cod_Partido)"+
	            		"WHERE (Nombre_Rango='Diputado') ");
	            while(resultadoDIPUTADOS.next())
	            {
	            	Candidatos3.add(resultadoDIPUTADOS.getString("Nombre"));
	            	Votos3.add(resultadoDIPUTADOS.getInt("Votos"));
	            }
	            //agregando datos a la serie
	            for(int i=0;i<Candidatos3.size();i++)
	            {
	             	seriesDiputados.getData().add(new XYChart.Data<>(Candidatos3.get(i),Votos3.get(i)));
	            }
	            conexion1.close();
	        }catch (ClassNotFoundException e){
				e.printStackTrace();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		
		}
		private void ObtenerAlcaldesBD() {
			try {
				 Class.forName("com.mysql.jdbc.Driver");
	    		  Connection conexion1=DriverManager.getConnection("jdbc:mysql://"+main.nombreServidor+"/"+main.nombreBasedeDatos+"","root","");   

		        //Cargar informacion de los ALCALDES
		        Statement instruccionALCALDES= conexion1.createStatement(); 
	            ResultSet resultadoALCALDES= instruccionALCALDES.executeQuery(
	            		"SELECT A.Nombre ,B.Nombre_Rango, C.Nombre_Partido,A.Votos "+
	            		"FROM `candidato` A "+
	            		"INNER JOIN `rangos` B "+
	            		"ON (A.Cod_Rango = B.Cod_Rango) "+
	            		"INNER JOIN `partidos` C "+
	            		"ON (A.Cod_Partido = C.Cod_Partido)"+
	            		"WHERE (Nombre_Rango='Alcalde') ");
	            while(resultadoALCALDES.next())
	            {
	            	Candidatos2.add(resultadoALCALDES.getString("Nombre"));
	            	Votos2.add(resultadoALCALDES.getInt("Votos"));
	            }
	            //agregando datos a la serie
	            for(int i=0;i<Candidatos2.size();i++)
	            {
	             	seriesAlcades.getData().add(new XYChart.Data<>(Candidatos2.get(i),Votos2.get(i)));
	            }
	          conexion1.close();
	        }
			catch (ClassNotFoundException e)
	    	{
				e.printStackTrace();
			}
	    	catch (SQLException e) 
	    	{
				e.printStackTrace();
			}
			
		}
		private void ObtenerPresidentesBD() {
			try {
				    Class.forName("com.mysql.jdbc.Driver");
	    		    Connection conexion1=DriverManager.getConnection("jdbc:mysql://"+main.nombreServidor+"/"+main.nombreBasedeDatos+"","root","");   

		         //Cargar informacion de lso presedentes
		        Statement instruccionPRESIDENTES = conexion1.createStatement(); 
	            ResultSet resultadoPRESIDENTES= instruccionPRESIDENTES.executeQuery(
	            		"SELECT A.Nombre ,B.Nombre_Rango, C.Nombre_Partido,A.Votos "+
	            		"FROM `candidato` A "+
	            		"INNER JOIN `rangos` B "+
	            		"ON (A.Cod_Rango = B.Cod_Rango) "+
	            		"INNER JOIN `partidos` C "+
	            		"ON (A.Cod_Partido = C.Cod_Partido)"+
	            		"WHERE (Nombre_Rango='Presidente') ");
	            while(resultadoPRESIDENTES.next())
	            {
	            	Candidatos.add(resultadoPRESIDENTES.getString("Nombre"));
	            	Votos.add(resultadoPRESIDENTES.getInt("Votos"));
	            }
	            //agregando datos a la serie
	            for(int i=0;i<Candidatos.size();i++)
	            {
	             	seriesPresidentes.getData().add(new XYChart.Data<>(Candidatos.get(i),Votos.get(i)));
	            }
	          conexion1.close();
	      }
	    	catch (ClassNotFoundException e)
	    	{
				e.printStackTrace();
			}
	    	catch (SQLException e) 
	    	{
				e.printStackTrace();
			}
			
		}
		
		
		@Override
		public String toString()
		{
			return "Probando";
		}
        //metodos para mis ventanas
		private Main main;
	    public Main getMain(){
			return main;
		}
		public void setMain(Main main){
			this.main = main;
		}
	    @FXML
	    private void CerrarBarras()
		{
			main.cerrarVentana("GraficoBarras");
	    }






	    
	}

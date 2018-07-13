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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class BarrasAlcaldesController {
	@FXML private Button btnAceptar;
    @FXML private CategoryAxis xAxis;
    @FXML private NumberAxis yAxis;
    @FXML private BarChart<String,Integer> bcGrafcoBarrasAlcaldes;
    @FXML private Label  Etiquetaflotante ; 
    private ObservableList<String> nomPartidos = FXCollections.observableArrayList();
     private ObservableList<XYChart.Series<String,Integer>> seriePartidos= FXCollections.observableArrayList();
    	 
     private ObservableList<String> CandidatosTotales = FXCollections.observableArrayList();
     private ObservableList<Integer> VotosTotales = FXCollections.observableArrayList();
     
     
     
     @FXML
	 private void initialize()
	    {   
         btnAceptar.setText("Aceptar");
	     xAxis.setLabel("Candidatos a Alcalde");
		 yAxis.setLabel("Votos");
		 yAxis.setAnimated (true);
		 xAxis.setAnimated (true);
		 bcGrafcoBarrasAlcaldes.setTitle("Grafico de Barras de todos los Alcaldes");
		 bcGrafcoBarrasAlcaldes.setBarGap(3);
	     bcGrafcoBarrasAlcaldes.setCategoryGap(10);
	     xAxis.setTickLabelRotation(30);
	     ObtenerAlcaldesBD();
	     //esto es momentaneo aqui obtengo todo los datos paramostar en la etiqueta
	     ObtenerTodoAlcaldes();
	     //
	     Etiquetaflotante.setTextFill(Color.DARKORANGE);
	     Etiquetaflotante.setStyle("-fx-font: 18 arial;");
      for ( Series<String, Integer> data : bcGrafcoBarrasAlcaldes.getData())
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
 	private void ObtenerTodoAlcaldes() {
		try {
			//OJO AQUI COPIE PARA LA MODIFOCAION DE LAS ETIQUETAS 
			Class.forName("com.mysql.jdbc.Driver");//Esta vaina carga el driver
			   Connection conexion=DriverManager.getConnection("jdbc:mysql://"+Main.nombreServidor+"/"+Main.nombreBasedeDatos+"","root","");//voy a utilizar solo una conceccion   
				
			for(int i=0;i<nomPartidos.size();i++){
	        	    Statement instruccion= conexion.createStatement(); 
		            ResultSet resultadoPresidentes= instruccion.executeQuery(
	        	    		"SELECT A.Nombre ,B.Nombre_Rango, C.Nombre_Partido,A.Votos "+
	        	    		"FROM `candidato` A "+
	        	    		"INNER JOIN `rangos` B "+ 
	        	    		"ON (A.Cod_Rango = B.Cod_Rango) "+ 
	        	    		"INNER JOIN `partidos` C "+
	        	    		"ON (A.Cod_Partido = C.Cod_Partido) "+
	        	    		"WHERE ((Nombre_Rango ='Alcalde') && (A.Cod_Partido ='"+(i+1)+"'))");
	  		        while(resultadoPresidentes.next()){
	  		        	CandidatosTotales.add(resultadoPresidentes.getString("Nombre"));
	  		        	VotosTotales.add(resultadoPresidentes.getInt("Votos"));
	  		        }
		}
		}catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
         		
}
@SuppressWarnings("unchecked")
private void ObtenerAlcaldesBD()
    {
	   try 
	     {
		   Class.forName("com.mysql.jdbc.Driver");//Esta vaina carga el driver
		   Connection conexion=DriverManager.getConnection("jdbc:mysql://"+main.nombreServidor+"/"+main.nombreBasedeDatos+"","root","");//voy a utilizar solo una conceccion   
			   
		       Statement instruccion= conexion.createStatement(); 
	           ResultSet resultadoPartidos= instruccion.executeQuery("SELECT A.Nombre_Partido FROM `partidos` A");
	           while(resultadoPartidos.next()){
	        	   nomPartidos.add(resultadoPartidos.getString("Nombre_Partido"));
	        	   //System.out.println(resultadoPartidos.getString("Nombre_Partido"));
	           }
	           for(int i=0;i<nomPartidos.size();i++){
	        	   //Ojo aqui pase lo Obsevables list
	        	   ObservableList<String> Candidatos = FXCollections.observableArrayList();
	        	   ObservableList<Integer> Votos = FXCollections.observableArrayList(); 
                    //Crear y Nombrar  la serie
	        	     seriePartidos.add(new XYChart.Series<>());
	        	    (seriePartidos.get(i)).setName(nomPartidos.get(i));
	        	  
	        	    //Colocar datos ala serie
	        	    ResultSet resultadoAlcaldes= instruccion.executeQuery(
	        	    		"SELECT A.Nombre ,B.Nombre_Rango, C.Nombre_Partido,A.Votos "+
	        	    		"FROM `candidato` A "+
	        	    		"INNER JOIN `rangos` B "+ 
	        	    		"ON (A.Cod_Rango = B.Cod_Rango) "+ 
	        	    		"INNER JOIN `partidos` C "+
	        	    		"ON (A.Cod_Partido = C.Cod_Partido) "+
	        	    		"WHERE ((Nombre_Rango ='Alcalde') && (A.Cod_Partido ='"+(i+1)+"'))");
	  		        while(resultadoAlcaldes.next()){
	  		        	Candidatos.add(resultadoAlcaldes.getString("Nombre"));
	  		        	Votos.add(resultadoAlcaldes.getInt("Votos"));
	  		        }
	  		        //Agregando los datos que econtro el query A la serie
	  		         for(int j=0;j<Candidatos.size();j++){
	  		         (seriePartidos.get(i)).getData().add(new XYChart.Data<>(Candidatos.get(j),Votos.get(j)));
	  		         }
	  		   }
	          for(int j=0;j<seriePartidos.size();j++) {
	        	     //Agregar la serie al grafico
	  		         bcGrafcoBarrasAlcaldes.getData().addAll(seriePartidos.get(j));
	  		   }
	  		   
		   conexion.close();
    	}
		catch (ClassNotFoundException e){
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}
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
   private void CerrarBarrasAlcades()
	{
		main.cerrarVentana("GraficoBarrasAlcaldes");
   }
}

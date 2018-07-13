package application;

import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import org.controlsfx.dialog.Dialogs;

public class MenuController implements Initializable{
	@FXML private  Button btnEstadisticas;
	@FXML private  Button btncandidatos;
	@FXML private Button btnVotantes;
	@FXML private Button btnHistorial;
	@FXML private Button btnIniciarVotacion;
	@FXML private  Button btnSalirPrograma;
	@FXML private Label  lblHora;
	@FXML protected Label lblFinaliza;
	@FXML private ImageView imgFondo;
	@FXML private GridPane GridPane;
	@FXML private MenuBar miMenuBar;
	@FXML private Label LblIniciarVotacion;
	@FXML private ImageView ImgIniciarVotacion;
	@FXML private HBox HBoxIniciarVotacion;
		
	private String Nombretemporal;
    
     
	@FXML private Label lblUsuario;
         private Main main;
	   //metod get y set
		public Main getMain(){
			return main;
		}
		public void setMain(Main main)	{
			this.main = main;
		}
		public void setLabelUsuario(String nombreUsuario){
			this.Nombretemporal=nombreUsuario;
			this.lblUsuario.setText(Nombretemporal);
		}

	public void initialize(URL arg0, ResourceBundle arg1) {
		LblIniciarVotacion.setVisible(false);
		//que jodes con tu relojito gabo
		bindToTime();
		//
		btncandidatos.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("../Imagenes/candidatos.png"))));
		//btnIniciarVotacion.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("../Imagenes/Cronometro.png"))));
		btnEstadisticas.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("../Imagenes/estadistica.png"))));
		btnVotantes.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("../Imagenes/votar.png"))));
		btnHistorial.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("../Imagenes/urna.png"))));
		//hilos de pruebas
		LblIniciarVotacion.setTextFill(Color.DARKRED);
	    LblIniciarVotacion.setStyle("-fx-font: 24 arial;");

	
       ImgIniciarVotacion.addEventHandler(MouseEvent.MOUSE_MOVED,new EventHandler<MouseEvent>() 
	       		{
	               @Override 
	               public void handle(MouseEvent e)
	               {
	            	   ImgIniciarVotacion.setImage(new Image(getClass().getResourceAsStream("../Imagenes/ImagenesBotonIniciar/Cronometro.png")));
	            	   LblIniciarVotacion.setVisible(true);
	            	   LblIniciarVotacion.setPickOnBounds(true);
	            	   LblIniciarVotacion.setTranslateX(e.getX());
                	   LblIniciarVotacion.setTranslateY(e.getY());
                	  // LblIniciarVotacion.setText("Iniciar Votación");
                     	               
	               }
	           });
      
       IniciarParpadeo();
        	
		HBoxIniciarVotacion.addEventHandler(MouseEvent.MOUSE_MOVED,new EventHandler<MouseEvent>() 
   		{
            @Override 
            public void handle(MouseEvent e)
            {
         	 //btnIniciarVotacion.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("../Imagenes/ImagenesBotonIniciar/Cronometro2.png"))));
            	 ImgIniciarVotacion.setImage(new Image(getClass().getResourceAsStream("../Imagenes/ImagenesBotonIniciar/Cronometro1.png")));
           	   	               
            }
        });
		
		miMenuBar.addEventHandler(MouseEvent.MOUSE_MOVED,new EventHandler<MouseEvent>() 
   		{
            @Override 
            public void handle(MouseEvent e)
            {
         	 //btnIniciarVotacion.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("../Imagenes/ImagenesBotonIniciar/Cronometro4.png"))));
         	 ImgIniciarVotacion.setImage(new Image(getClass().getResourceAsStream("../Imagenes/ImagenesBotonIniciar/Cronometro4.png")));
       	    	               
            }
        });
		//fin eventos a borrar
		
		//imgFondo.setImage((new Image(getClass().getResourceAsStream("../Imagenes/FondoGeneral.jpg"))));
		
	} 

public void IniciarParpadeo()
   {
	Task<Integer> task = new Task<Integer>(){
		@Override
        protected Integer call() throws Exception{
           // System.out.println("Iniciando el hilo");
            int i;
            for(i= 0; i<10000000; i++){
              if(isCancelled()){//Unicamente verificar si se cancelo el hilo
                break;
              }
             // System.out.println("Iteracion " + i);
              updateMessage("Inicio Votacion: "+i); 
              ImgIniciarVotacion.setImage(new Image(getClass().getResourceAsStream("../Imagenes/ImagenesBotonIniciar/Cronometro1.png")));
       	      Thread.sleep(1000); //Pausa
       	      ImgIniciarVotacion.setImage(new Image(getClass().getResourceAsStream("../Imagenes/ImagenesBotonIniciar/Cronometro3.png")));
     	     
            }
            return i;
          }
        };
     
      /*	Task<Integer> task2 = new Task<Integer>(){
			@Override
	        protected Integer call() throws Exception{
	           // System.out.println("Iniciando el hilo");
	            int i;
	            for(i= 0; i<3; i++){
	              if(isCancelled()){//Unicamente verificar si se cancelo el hilo
	                break;
	              }
	              updateMessage("Inicio Votacion: "+i+1); 
	              ImgIniciarVotacion.setImage(new Image(getClass().getResourceAsStream("../Imagenes/ImagenesBotonIniciar/Cronometro1.png")));
           	     Thread.sleep(10); //Pausa
	            }
	            return i;
	          }
	        };//
       */ 
        Thread th = new Thread(task);
        th.setDaemon(true);
        LblIniciarVotacion.textProperty().bind(task.messageProperty());
        th.start();
        
        
        
        /* Thread th2 = new Thread(task2);
	        th2.setDaemon(true);
	      LblIniciarVotacion.textProperty().bind(task2.messageProperty());
          th2.start();
	    */
   }
	@FXML public void llamarVentanaEstadisticas() {
		      main.VerVentanaEstaditicas();
	    } 
		@FXML public void llamarVentanaCandidatos(){
			  main.VerVentanaCandidatos();
		    } 
		@FXML public void llamarVentanaVotantes(){
			  main.VerVentanaVotantes();
		    } 
		@FXML public void llamarVentanaHistorial(){
			  main.VerVentanaHistorial();
		    } 
		@FXML public void llamarVentanaTemporizador(){
			  main.VerVentanaTemporizador();
			 } 
		@FXML public void llamarVentanaCreditos(){
			     main.VerVentanaCreditos();
			} 
		@FXML private void AyudaMenu(){
			 Dialogs.create().title("Ayuda Acerca del programa").masthead("Esta es la ventana principal del programa en ella usted podra navegar por"
			 		+ " todas la opciones del sistema.").showInformation();
		  }
		
		@FXML public void SalirDelPrograma() {
		    //luego agrego el dialogo para presguntar si lo quierre elimiar  
			main.cerrarVentana("VerValidacion");
	    }
		
		private void bindToTime() {
		    Timeline timeline = new Timeline(
		      new KeyFrame(Duration.seconds(0),
		        new EventHandler<ActionEvent>() {
		          @Override public void handle(ActionEvent actionEvent) {
		            Calendar time = Calendar.getInstance();
		            String hourString = StringUtilities.pad(2, ' ', time.get(Calendar.HOUR) == 0 ? "12" : time.get(Calendar.HOUR) + "");
		            String minuteString = StringUtilities.pad(2, '0', time.get(Calendar.MINUTE) + "");
		            String secondString = StringUtilities.pad(2, '0', time.get(Calendar.SECOND) + "");
		            String ampmString = time.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";
		            lblHora.setText(hourString + ":" + minuteString + ":" + secondString + " " + ampmString);
		          }
		        }
		      ),
		      new KeyFrame(Duration.seconds(1))
		    );
		    timeline.setCycleCount(Animation.INDEFINITE);
		    timeline.play();
		  }
		}
	class StringUtilities {
		  public static String pad(int fieldWidth, char padChar, String s) {
		    StringBuilder sb = new StringBuilder();
		    for (int i = s.length(); i < fieldWidth; i++) {
		      sb.append(padChar);
		    }
		    sb.append(s);

		    return sb.toString();
		  }

	
}

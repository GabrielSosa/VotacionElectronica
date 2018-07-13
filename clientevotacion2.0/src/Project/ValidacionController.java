package Project;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class ValidacionController implements Initializable {

	
@FXML private Button boton;
@FXML private Label lblUsuario;
@FXML private ImageView ImgFoto;
@FXML private Label lblTiempo;
@FXML private TextField txtpin;
private String Nombretemporal;
private Main main;
private String pin;
	   
@Override
public void initialize(URL arg0, ResourceBundle arg1) {
	levantarHilos();
	//main.ventanaValidacion.close();
	
	
}
	//metod get y set
		public Main getMain(){
			return main;
		}
		public void setMain(Main main)	{
			this.main = main;
		}
		
		public void setLabelUsuario(String nombreUsuario){
			this.Nombretemporal=nombreUsuario;
			lblUsuario.setText(Nombretemporal);
		}
		
		public void VerFoto(String id){ 
			ImgFoto.setImage(new Image("http://localhost/registrovoto/Imagenesusuarios/"+id+".jpeg")); 
		}
		
		public String getPin() {
			return pin;
		 }
		
		public void setPin(String pin) {
			this.pin = pin;
			
		}

		public void levantarHilos(){//Este metodo solo levanta un hilo
			//Crear un nuevo Task (Solo es la definicion, aqui no se ejecuta nada del contenido)
			Task<Integer> task = new Task<Integer>(){
			
			//El metodo call es el primer metodo que se llama al ejecutar un hilo
			@Override
	        protected Integer call() throws Exception{
	           
	            int i;
	            for(i= 7; i>=0; i--){
	              if(isCancelled()){//Unicamente verificar si se cancelo el hilo
	                break;
	              }
	             // System.out.println("Iteracion " + i);
	              updateMessage("0:0"+i); 	//Este metodo actualiza el property llamado MessageProperty de la clase task
	              //por lo tanto tambien se modificara cualquier vinculo con este property
	              
	              Thread.sleep(1000); //Pausa
	              if(lblTiempo.getText().equals("0:00")){   
	            	  main.ventanaValidacion.close();
	  	    		}
	            }
	            return i;
	          }
	        };
		    
	        //La clase Task tiene un property que se llama message,
	        //en la siguiente linea se vincula este property con el property 
	        //respectivo del Label, como estan vinculados cada vez que se modifique
	        //el messageProperty del task tambien se modificara el contenido de la etiqueta
	        lblTiempo.textProperty().bind(task.messageProperty());

	        //Se crea un hilo normal que recibe como parametro una instancia de la clase Task
	        Thread th = new Thread(task);
	        th.setDaemon(true);
	        th.start();//Este metodo inicia el hilo, lo que hace es llamar al metodo call() de la clase Task
	        
	        
		}
		
@FXML
public void siguiente(){
	if(txtpin.getText().equals(pin))
	{	main.EscenaPrincipal.close();
		main.VentanaPresidente(); }
	else{
		
	}
		
}






}
	

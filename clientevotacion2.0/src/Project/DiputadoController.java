package Project;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import Modelo.Diputado;
import Modelo.Utilidades;

public class DiputadoController implements Initializable{	
	boolean bandera = false;
	int votoTemporal;
	
	int contador = 0;
	int cantidad = 0;
	int partidos = 0;
	
	@FXML ToggleButton tglBlanco;
	@FXML ToggleButton tglNulo;
	@FXML VBox superVBox;
	
	@FXML Button btnSiguiente;

	String urlPartido;
	String urlCandidato;
	String nombreCandidato;
	
	HashMap<Integer, ToggleButton> tglBotones = new HashMap<Integer, ToggleButton>();
	HashMap<Integer, HBox> hBoxes = new HashMap<Integer, HBox>();
	
	private ObservableList<Diputado> candidatos = FXCollections.observableArrayList();
	ArrayList<String> Partidos = new ArrayList<>();	
	
	int partido = 0;
	   
	@Override
	public void initialize(URL arg0, ResourceBundle arg1){
		CargarDiputadosBD();
		ContarPartidos();
	    partidos = Partidos.size();
	   
		for(int i=0; i<partidos; i++){
			
			hBoxes.put(i, new HBox());
			hBoxes.get(i).setAlignment(Pos.CENTER);
			superVBox.getChildren().add(hBoxes.get(i));
			
		}
		
		for(int i =0; i<cantidad; i++){
			
			VBox vbox = new VBox();
			vbox.setAlignment(Pos.CENTER);
			vbox.setPadding(new Insets(0,8,0,8));
			urlPartido = "Imagenes/"+candidatos.get(i).getPartido()+".jpg";
			urlCandidato = "";
			nombreCandidato = candidatos.get(i).getNombre().toString();
			//nomCandidatos.get(i);
			
			if(contador > 12){
				partido++;
				contador = 0;
			}
			
			ImageView imgvPartido = new ImageView(urlPartido);
			
			imgvPartido.setFitHeight(40);
			imgvPartido.setFitWidth(40);
			
			ImageView imgvCandidato = new ImageView(candidatos.get(i).getImagen());
			imgvCandidato.setFitHeight(40);
			imgvCandidato.setFitWidth(40);
			tglBotones.put(i, new ToggleButton());
			tglBotones.get(i).setText(nombreCandidato);
			tglBotones.get(i).setMaxSize(65, 10);
			vbox.getChildren().add(imgvPartido);
			vbox.getChildren().add(imgvCandidato);
			vbox.getChildren().add(tglBotones.get(i));
			
			for(int j=0; j<partidos; j++)
				if(partido == j)
					hBoxes.get(j).getChildren().add(vbox);
			
			contador++;
			
			if(partido == 5)
				partido = 0; 
		}
		
		limite();
	}
	
	private void CargarDiputadosBD() {
		int suma=0;
		InputStream inputStream;
		try
        {
               Connection oConeccion = Utilidades.getConexion();   

               Statement s=oConeccion.createStatement();
               ResultSet rs=s.executeQuery("SELECT A.Cod_candidato, "+
               "A.Nombre ,B.Nombre_Rango, C.Nombre_Partido, A.Votos, A.IMAGEN "+
               "FROM `candidato` A "+
               "INNER JOIN `rangos` B "+ 
               "ON (A.Cod_Rango = B.Cod_Rango) "+ 
               "INNER JOIN `partidos` C "+
               "ON (A.Cod_Partido = C.Cod_Partido) "+
               "WHERE (Nombre_Rango='Diputado') order by Nombre_Partido");
              
               while(rs.next())
               {
            	   inputStream = rs.getBinaryStream("IMAGEN");
 					Image imagen = null;
 					if(inputStream!=null){
 					BufferedImage bimage = ImageIO.read(inputStream);
 			        WritableImage writableImage = new WritableImage(bimage.getWidth(), bimage.getHeight());
 			        imagen = SwingFXUtils.toFXImage(bimage,writableImage);  
 			        }
            	   Diputado a = new Diputado();
            	   a.setCodCandidato(rs.getString("Cod_candidato"));
            	   a.setNombre(rs.getString("Nombre"));
            	   a.setRango(rs.getString("Nombre_Rango"));
            	   a.setPartido(rs.getString("Nombre_Partido"));
            	   a.setVotos(rs.getInt("Votos"));
            	   a.setImagen(imagen);
            	   candidatos.add(a);
                   suma++;
               	//   System.out.println(rs.getString("Nombre"));
               }
               cantidad=suma;
               oConeccion.close();
        }
		catch(Exception e)
        {
               e.printStackTrace();
               System.out.print("Mal");         
        }
	}
	
	private void EnviarVotosDiputadosBD(int votos) {
		int voto = votos; 	 
		int NumVoto = 0; 
		NumVoto = candidatos.get(voto).getVotos()+1;
		try
	         {
					Connection oConeccion = Utilidades.getConexion();
	                 PreparedStatement rs =  oConeccion.prepareStatement("UPDATE candidato SET Votos = "+NumVoto+" WHERE `Cod_candidato` = '"+candidatos.get(voto).getCodCandidato()+"'");
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
	
public void ContarPartidos(){

	
			
		for(int i=0; i<candidatos.size(); i++){
			Partidos.add(candidatos.get(i).getPartido());
			
		}
		
		for(int i=0; i<Partidos.size(); i++) {
		    String contrato = Partidos.get(i);
		    for(int j=i+1; j<Partidos.size(); j++) {
		        String contrato2 = Partidos.get(j);
		        if (contrato.equals(contrato2)) {
		            Partidos.remove(j);
		            j=i;
		            
		        }
		    }
		}

		
		
		//	return partidos.size();
			
			
	} 
		
public void actualizar()
{
	InputStream inputStream;
	try
    {
		Connection oConeccion = Utilidades.getConexion();
           Statement s=oConeccion.createStatement();
           ResultSet rs=s.executeQuery("SELECT A.Cod_candidato, "+
                   "A.Nombre ,B.Nombre_Rango, C.Nombre_Partido, A.Votos, A.IMAGEN "+
                   "FROM `candidato` A "+
                   "INNER JOIN `rangos` B "+ 
                   "ON (A.Cod_Rango = B.Cod_Rango) "+ 
                   "INNER JOIN `partidos` C "+
                   "ON (A.Cod_Partido = C.Cod_Partido) "+
                   "WHERE (Nombre_Rango='Diputado') order by Nombre_Partido");
           int c =0;
           while(rs.next())
           {
        	   inputStream = rs.getBinaryStream("IMAGEN");
				Image imagen = null;
				if(inputStream!=null){
				BufferedImage bimage = ImageIO.read(inputStream);
		        WritableImage writableImage = new WritableImage(bimage.getWidth(), bimage.getHeight());
		        imagen = SwingFXUtils.toFXImage(bimage,writableImage);  
		        }
        	   candidatos.get(c).setCodCandidato(rs.getString("Cod_candidato"));
        	   candidatos.get(c).setNombre(rs.getString("Nombre"));
        	   candidatos.get(c).setRango(rs.getString("Nombre_Rango"));
        	   candidatos.get(c).setPartido(rs.getString("Nombre_Partido"));
        	   candidatos.get(c).setVotos(rs.getInt("Votos"));
        	   candidatos.get(c).setImagen(imagen);
        	   c++;
                            	//   System.out.println(rs.getString("Nombre"));
           }
           cantidad=c;
           oConeccion.close();
    }
	catch(Exception e)
    {
           e.printStackTrace();
           System.out.print("Mal");         
    }		
}


	@FXML
	public void limite(){
		for(int i=0; i<cantidad; i++)
			tglBotones.get(i).setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent arg0){
					int a = 0;
					if(tglBlanco.isSelected() || tglNulo.isSelected()){
						if(tglBlanco.isSelected())
							tglNulo.setDisable(true);
						else
							tglBlanco.setDisable(true);
						for(int i=0; i<cantidad; i++)
							if(!tglBotones.get(i).isSelected())
								tglBotones.get(i).setDisable(true);
						btnSiguiente.setDisable(false);
					}
					else{
						for(int i=0; i<cantidad; i++)
							if(tglBotones.get(i).isSelected())
								a++;
						if(a==5){
							for(int i=0; i<cantidad; i++)
								if(!tglBotones.get(i).isSelected())
									tglBotones.get(i).setDisable(true);
						}
						else
							for(int i=0; i<cantidad; i++)
								if(!tglBotones.get(i).isSelected())
									tglBotones.get(i).setDisable(false);
						if(a>0){
							btnSiguiente.setDisable(false);
							tglNulo.setDisable(true);
							tglBlanco.setDisable(true);
						}
						else{
							btnSiguiente.setDisable(true);
							tglNulo.setDisable(false);
							tglBlanco.setDisable(false);
						}
					}
				}
			});
	}
	
	public void quitarVoto(int votoTemporal){
		actualizar();
		int voto = votoTemporal; 	 
		int NumVoto = 0; 
		NumVoto = candidatos.get(voto).getVotos()-1;
		try
	         {
					Connection oConeccion = Utilidades.getConexion();
	                 PreparedStatement rs =  oConeccion.prepareStatement("UPDATE candidato SET Votos = "+NumVoto+" WHERE `Cod_candidato` = '"+candidatos.get(voto).getCodCandidato()+"'");
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

	private Main main;

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}
	
	@FXML
	public void siguiente(){
		actualizar();
		if(bandera == true)
			quitarVoto(votoTemporal);
		int voto=0;
		for(int i=0; i<cantidad; i++)
			if(tglBotones.get(i).isSelected()){	
				voto=i;
				main.a.addDiputados(candidatos.get(i).getNombre().toString());
				EnviarVotosDiputadosBD(voto);
			}
		bandera = true;
		JOptionPane.showMessageDialog(null, "Gracias por votar!!");
		main.VentanaResultado();
		
	}
	
	
	@FXML
	public void atras(){
		String nombre = null;
		main.VentanaAlcalde(nombre);
		main.ventanaDiputado.close();
	}
}
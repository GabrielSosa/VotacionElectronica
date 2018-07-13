package Project;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

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
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import Modelo.Candidato;
import Modelo.Utilidades;


public class AlcaldeController implements Initializable{
	boolean bandera = false;
	
	int votoTemporal;
	
	

	int contador = 0;
	int cantidad;
	
	@FXML Button btnSiguiente;
	@FXML ToggleGroup tglGrupo;
	@FXML ToggleButton tglBlanco;
	@FXML ToggleButton tglNulo;
	@FXML HBox hBoxPrincipal1;
	@FXML HBox hBoxPrincipal2;
	String urlPartido;
	String urlCandidato;
	String nombreCandidato;
	
	HashMap<Integer, ToggleButton> tglBotones = new HashMap<Integer, ToggleButton>();
	private ObservableList<Candidato> candidatos;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1){
		candidatos = FXCollections.observableArrayList();
		CargarAlcaldeBD();
		for(int i =0; i<cantidad; i++){
			VBox vbox = new VBox();
			vbox.setAlignment(Pos.CENTER);
			vbox.setPadding(new Insets(0,8,0,8));
			urlPartido = "Imagenes/"+candidatos.get(i).getPartido()+".jpg";
			urlCandidato = "";
			nombreCandidato = candidatos.get(i).getNombre();
			//nomCandidatos.get(i);
			ImageView imgvPartido = new ImageView(urlPartido);
			
			imgvPartido.setFitHeight(80);
			imgvPartido.setFitWidth(80);
			
			ImageView imgvCandidato = new ImageView(candidatos.get(i).getImagen());
			imgvCandidato.setFitHeight(80);
			imgvCandidato.setFitWidth(80);
			tglBotones.put(i, new ToggleButton());
			tglBotones.get(i).setText(nombreCandidato);
			tglBotones.get(i).setToggleGroup(tglGrupo);
			vbox.getChildren().add(imgvPartido);
			vbox.getChildren().add(imgvCandidato);
			vbox.getChildren().add(tglBotones.get(i));
			contador++;
			
			if(contador < 9)
				hBoxPrincipal1.getChildren().add(vbox);
			else
				hBoxPrincipal2.getChildren().add(vbox);
		}
		
		onOfSiguiente();
	}
	
	private void CargarAlcaldeBD() {
		int suma =0;
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
               "WHERE (Nombre_Rango='Alcalde') order by Nombre_Partido");
               
               while(rs.next())
               {
            	   
            	    inputStream = rs.getBinaryStream("IMAGEN");
  					Image imagen = null;
  					if(inputStream!=null){
  					BufferedImage bimage = ImageIO.read(inputStream);
  			        WritableImage writableImage = new WritableImage(bimage.getWidth(), bimage.getHeight());
  			        imagen = SwingFXUtils.toFXImage(bimage,writableImage);  
  			        }
  					Candidato a = new Candidato();
  					a.setCodCandidato(rs.getString("Cod_candidato"));
  					a.setNombre(rs.getString("Nombre"));
  					a.setRango(rs.getString("Nombre_Rango"));
  					a.setPartido(rs.getString("Nombre_Partido"));
  					a.setVotos(rs.getInt("Votos"));
  					a.setImagen(imagen);
  						candidatos.add(a);
  				suma++;
  						
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
               "WHERE (Nombre_Rango='Alcalde') order by Nombre_Partido");
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
  					Candidato a = new Candidato();
  					a.setCodCandidato(rs.getString("Cod_candidato"));
  					a.setNombre(rs.getString("Nombre"));
  					a.setRango(rs.getString("Nombre_Rango"));
  					a.setPartido(rs.getString("Nombre_Partido"));
  					a.setVotos(rs.getInt("Votos"));
  					a.setImagen(imagen);
  						candidatos.add(a);
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

	public void onOfSiguiente(){
		for(int i=0; i<cantidad; i++)
			tglBotones.get(i).setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent arg0){
					boolean validacion = false;
					for(int i=0; i<cantidad; i++){
						if(tglBotones.get(i).isSelected()){
							btnSiguiente.setDisable(false);
							validacion = true;
							break;
						}
					}
					if(validacion == false)
						btnSiguiente.setDisable(true);
				}
			});
	}
	
	private void EnviarVotosAlcaldeBD(int votos) {
		actualizar();
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
		int voto= 0;
		if((tglGrupo.getSelectedToggle() == tglBlanco) || (tglGrupo.getSelectedToggle() == tglNulo))
			System.out.println("-1");
		else
			for(int i=0; i<cantidad; i++){
				if(tglGrupo.getSelectedToggle() == tglBotones.get(i)){
					voto = i;
					votoTemporal = voto;
					break;
				}
			}
		EnviarVotosAlcaldeBD(voto);
		bandera = true;
		main.VentanaDiputado(candidatos.get(voto).getNombre());
	}
	
	@FXML
	public void atras(){
		main.VentanaPresidente();
		main.ventanaAlcalde.close();
	}
}
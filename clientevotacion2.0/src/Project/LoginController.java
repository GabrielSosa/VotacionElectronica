package Project;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import Modelo.Login;
import Modelo.Utilidades;

import com.github.sarxos.webcam.Webcam;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;



public class LoginController implements Initializable{

	

	@FXML
	ComboBox<Login> cbCameraOptions;

	@FXML
	BorderPane bpWebCamPaneHolder;

	@FXML
	FlowPane fpBottomPane;

	@FXML
	ImageView imgWebCamCapturedImage;
	
	
	@FXML Button btnDisposeCamera;
	
	@FXML 
	Button btnIniciar;
	
	@FXML Label lblMensaje;
	
	private String temporal ="";
	private String Nombre;
	private String id;
	
	private ObservableList<Login>Verifica = FXCollections.observableArrayList();
	
	@FXML TextField txtleer;

	private BufferedImage grabandoImagen;
	private Webcam selWebCam = null;
	private boolean pararCamara = false;
	private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<Image>();

	private String cameraListPromptText = "Mostrar";
	private Main main;
	private String pin;
	
	public Main getMain(){
		return main;
	}
	
	public void setMain(Main main){
		this.main = main;
	}


	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnIniciar.setTooltip(new Tooltip("Boton de Inicio camara"));
		CargarBase();
		fpBottomPane.setDisable(true);
		// Este observable list carga los drivers
		ObservableList<Login> options = FXCollections.observableArrayList();
		int webCamCounter = 0;
		// va llenado respecto a los drivers de camara que encuentre
		for (Webcam webcam : Webcam.getWebcams()) {
			Login webCamInfo = new Login();
			webCamInfo.setWebCamIndex(webCamCounter);
			webCamInfo.setWebCamName(webcam.getName());
			options.add(webCamInfo);
			webCamCounter++;
		}
		// aqui pone los drivers que encontro si no encuentra no hay nada
		cbCameraOptions.setItems(options);
		cbCameraOptions.setPromptText(cameraListPromptText);
		// aqui se localiza cuando obtenemos el driver y lo seleccionamos llamamos al listener webcam
		cbCameraOptions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Login>() {

			@Override
			public void changed(ObservableValue<? extends Login> arg0, Login arg1, Login arg2) {
				if (arg2 != null) {
				//	System.out.println("WebCam Index: " + arg2.getWebCamIndex() + ": WebCam Name:" + arg2.getWebCamName());
					IniciaWebcam(arg2.getWebCamIndex());
				}
			}
		});
		Platform.runLater(new Runnable() {
			//Este metodo es como un hilo que sirve de interacion con el programa
			@Override
			public void run() {
				ObtenerDimensionesdeCamara();

			}
		});
		
		/*Platform.runLater(new Runnable() {
			@Override
			public void run() {
				txtleer.requestFocus();
				verificarContrasena();
			}
	    
			});*/
		txtleer.selectAll();

	}

	protected void ObtenerDimensionesdeCamara() {

		double height = bpWebCamPaneHolder.getHeight();
		double width = bpWebCamPaneHolder.getWidth();
		imgWebCamCapturedImage.setFitHeight(height);
		imgWebCamCapturedImage.setFitWidth(width);
		imgWebCamCapturedImage.prefHeight(height);
		imgWebCamCapturedImage.prefWidth(width);
		imgWebCamCapturedImage.setPreserveRatio(true);

	}

	protected void IniciaWebcam(final int webCamIndex) {

		Task<Void> IniciarWebCam = new Task<Void>() {

			@Override
			protected Void call() throws Exception {

				if (selWebCam == null) {
					selWebCam = Webcam.getWebcams().get(webCamIndex);
					selWebCam.open();
				} else {
				//	closeCamera();
					selWebCam = Webcam.getWebcams().get(webCamIndex);
					selWebCam.open();
				}
				ComenzarGrabar();
				return null;
			}

		};

		new Thread(IniciarWebCam).start();
		fpBottomPane.setDisable(false);
		//btnStartCamera.setDisable(true);
	}

	protected void ComenzarGrabar() {

		pararCamara = false;
		Task<Void> task = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				// este ciclo me activa la camara para que este rodando
				while (!pararCamara) {
					try {
						if ((grabandoImagen = selWebCam.getImage()) != null) {

							Platform.runLater(new Runnable() {

								@Override
								public void run() {
									final Image mainiamge = SwingFXUtils
										.toFXImage(grabandoImagen, null);
									imageProperty.set(mainiamge);
								}
							});

							grabandoImagen.flush();

						}
						
						
						LuminanceSource source = new BufferedImageLuminanceSource(grabandoImagen);
						BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
						try {
							Result decodificar = new MultiFormatReader().decode(bitmap);
							if (decodificar != null) {
								//System.out.println("El Qr es = "+ decodificar.getText());
								
								Platform.runLater(new Runnable(){

									@Override
									public void run() {
										txtleer.setText(decodificar.getText());
										//verificarContrasena(decodificar.getText());
										
									}
									
								});
								pararCamara = true;
								Platform.runLater(new Runnable() {
									@Override
									public void run() {
										txtleer.requestFocus();
										verificarContrasena();
									}
							    
									});
							}
						} catch (NotFoundException e) {
							// Si no se haya QR
							//System.out.println("Tiene un error no se donde putas XD");
							continue;
						}
						

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				return null;
				
			}
			

		};
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
		imgWebCamCapturedImage.imageProperty().bind(imageProperty);
	

	}
	
	@FXML
	public void startCamera(ActionEvent event) {
		
		ComenzarGrabar();
		
	}
	
	
	public void CargarBase()
	{
		try {
			Connection conexion1 = Utilidades.getConexion();
	        Statement instruccionCONTRASEÑA= conexion1.createStatement(); 
	        ResultSet resultadoCONTRASEÑA= instruccionCONTRASEÑA.executeQuery("SELECT nomcompleto, id, voto, pin FROM usuario ");
	       while(resultadoCONTRASEÑA.next())
	        {
	    	   Login a = new Login(resultadoCONTRASEÑA.getString("nomcompleto")
	    			   				,resultadoCONTRASEÑA.getString("id"),
	    			   					resultadoCONTRASEÑA.getInt("voto"),
	    			   					resultadoCONTRASEÑA.getString("pin"));
	    	   Verifica.add(a);
	  
	        }
	    conexion1.close();
	    }catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

@FXML
private void verificarContrasena()
	{
	 lblMensaje.setVisible(false);
			        for(int i=0;i<Verifica.size();i++)
			        {
			      	  if ((!txtleer.getText().equals(Verifica.get(i).getId())||(Verifica.get(i).getVoto()!=0)))
			      	  {
						    lblMensaje.setVisible(true);
							lblMensaje.setText ("Lo sentimos este usuario ya voto!");
						    //lblMensaje.setText ("Su contraseña ha sido confirmada");
				        	lblMensaje.setTextFill (Color.rgb (210, 39, 30));
				        
				        	
			           	  } 
					    else {
				            lblMensaje.setVisible(true);
				            //lblMensaje.setText ("Su contraseña es incorrecta !");
				        	lblMensaje.setText ("Su contraseña ha sido confirmada");
				        	lblMensaje.setTextFill (Color.rgb(21, 117, 84));
				         	/*aqui deberia poner un timer
				        	 ----------------------------
				        	*/
				        	
				        	lblMensaje.setVisible(false);
				        	Nombre = Verifica.get(i).getNomCompleto();
				        	id = Verifica.get(i).getId();
				        	pin = Verifica.get(i).getPin();
				        	VerValidacion(Nombre, id, pin);
				        	
				        	i = Verifica.size();
				        }
			      	 }
			        
		 }


public void VerValidacion(String Nombre, String id, String pin){
	this.temporal = Nombre;
	main.VerValidacion(temporal, id, pin);
	
	
}
	

	
}

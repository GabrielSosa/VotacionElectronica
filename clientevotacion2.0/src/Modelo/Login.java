package Modelo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Login {
	
	// En esta clase obtengo los nombres de los drivers que tiene la camara
	private String webCamName;
	private int webCamIndex;

	private StringProperty id;
	private StringProperty NomCompleto;
	private IntegerProperty voto;
	private StringProperty pin;
	
	public Login(String nomCompleto,String id, int voto,String pin) {
		this.NomCompleto = new SimpleStringProperty(nomCompleto);
		this.id = new SimpleStringProperty(id);
		this.voto = new SimpleIntegerProperty(voto);
		this.pin = new SimpleStringProperty(pin);
	}
	
	public Login() {
		
	}

	public StringProperty IdProperty() {
		return this.id;
	}
	
	public String getId() {
		return id.get();
	}
	
	public void setId(String id) {
		this.id = new SimpleStringProperty(id);
	}
	
	public IntegerProperty VotoProperty() {
		return this.voto;
	}
	
	public int getVoto() {
		return voto.get();
	}
	
	public void setVoto(int voto) {
		this.voto = new SimpleIntegerProperty(voto);
	}

	public String getNomCompleto() {
		return NomCompleto.get();
	}
	
	public StringProperty NomCompletoProperty() {
		return this.NomCompleto;
	}

	public void setNomCompletp(String nomCompleto) {
		NomCompleto = new SimpleStringProperty(nomCompleto);
	}
	public String getWebCamName() {
		return webCamName;
	}

	public void setWebCamName(String webCamName) {
		this.webCamName = webCamName;
	}

	public int getWebCamIndex() {
		return webCamIndex;
	}

	public void setWebCamIndex(int webCamIndex) {
		this.webCamIndex = webCamIndex;
	}

	@Override
	public String toString() {
		return webCamName;
	}

	public StringProperty PinProperty() {
		return this.pin;
	}
	
	public String getPin() {
		return pin.get();
	}

	public void setPin(String pin) {
		this.pin = new SimpleStringProperty(pin);
	}
}

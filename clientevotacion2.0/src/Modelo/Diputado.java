package Modelo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class Diputado {

	private StringProperty codCandidato;
	private StringProperty nombre;
	private StringProperty rango;
	private StringProperty id;
	private StringProperty direccion;
	private IntegerProperty edad;
	private StringProperty genero;
	private StringProperty partido;
	private IntegerProperty votos;
	private Image imagen;
	
	public Diputado(String codCandidato, String nombre,
			String rango, String id, String direccion,
			int edad, String genero,
			String partido, int votos, Image imagen) {
		
		this.codCandidato = new SimpleStringProperty(codCandidato);
		this.nombre = new SimpleStringProperty(nombre);
		this.rango = new SimpleStringProperty(rango);
		this.id = new SimpleStringProperty(id);
		this.direccion = new SimpleStringProperty(direccion);
		this.edad = new SimpleIntegerProperty(edad);
		this.genero = new SimpleStringProperty(genero);
		this.partido = new SimpleStringProperty(partido);
		this.votos = new SimpleIntegerProperty(votos);
	}
	
	public Diputado() {
		
	}
	
	public StringProperty CodCandidatoProperty() {
		return this.codCandidato;
	}
	
	public String getCodCandidato() {
		return codCandidato.get();
	}
	
	public void setCodCandidato(String codCandidato) {
		this.codCandidato = new SimpleStringProperty(codCandidato);
	}
	
	public StringProperty NombreProperty() {
		return this.nombre;
	}
	
	public String getNombre() {
		return nombre.get();
	}
	
	public void setNombre(String nombre) {
		this.nombre = new SimpleStringProperty(nombre);
	}
	
	public StringProperty RangoProperty() {
		return this.rango;
	}
	
	public String getRango() {
		return rango.get();
	}
	
	public void setRango(String rango) {
		this.rango = new SimpleStringProperty(rango);
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
	
	public StringProperty DireccionProperty() {
		return this.direccion;
	}
	
	public String getDireccion() {
		return direccion.get();
	}
	
	public void setDireccion(String direccion) {
		this.direccion = new SimpleStringProperty(direccion);
	}
	
	public IntegerProperty EdadProperty() {
		return this.edad;
	}
	
	public int getEdad() {
		return edad.get();
	}
	
	public void setEdad(int edad) {
		this.edad = new SimpleIntegerProperty(edad);
	}
	
	public StringProperty GeneroProperty() {
		return this.genero;
	}
	
	public String getGenero() {
		return genero.get();
	}
	
	public void setGenero(String genero) {
		this.genero = new SimpleStringProperty(genero);
	}
	
	public StringProperty PartidoProperty() {
		return this.partido;
	}
	
	public String getPartido() {
		return partido.get();
	}
	
	public void setPartido(String partido) {
		this.partido = new SimpleStringProperty(partido);
	}
	
	public IntegerProperty VotosProperty() {
		return this.votos;
	}
	
	public int getVotos() {
		return votos.get();
	}
	
	public void setVotos(int votos) {
		this.votos = new SimpleIntegerProperty(votos);
	}

	public Image getImagen() {
		return imagen;
	}

	public void setImagen(Image imagen) {
		this.imagen = imagen;
	}
	
}

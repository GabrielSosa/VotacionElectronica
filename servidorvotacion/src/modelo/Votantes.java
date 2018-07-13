package modelo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Votantes {
	private StringProperty Nombre;
	private StringProperty Id;
	private StringProperty Direccion;
	private StringProperty Genero;
    private IntegerProperty Edad;
	private StringProperty Telefono;
	private IntegerProperty Voto;
	private StringProperty fecha;
	private StringProperty Imagen;
	private StringProperty Validacion;
	
	public Votantes (String Nombre,String id,String direccion,String Genero, int edad,
			String telefono, int voto, String fecha,String Imagen,String Validacion) {
		this.Nombre = new SimpleStringProperty(Nombre);
		this.Edad = new SimpleIntegerProperty(edad);
		this.Id = new SimpleStringProperty(id);
		this.Genero= new SimpleStringProperty(Genero);
		this.Direccion = new SimpleStringProperty(direccion);
		this.Telefono = new SimpleStringProperty(telefono);
		this.fecha= new SimpleStringProperty(fecha);
		this.Imagen= new SimpleStringProperty(Imagen);
		this.Validacion= new SimpleStringProperty(Validacion);
		this.Voto= new SimpleIntegerProperty(voto);
	}
	///
	
	public StringProperty getNombreProperty(){
		return this.Nombre;
	}
	
	public StringProperty getIdProperty(){
		return this.Id;
	}
	public StringProperty getDireccionProperty() {
		return this.Direccion;
	}
	public StringProperty getGeneroProperty(){
		return this.Genero;
	}
	public IntegerProperty getEdadProperty(){
		return this.Edad;
	}
	public StringProperty getTelefonoProperty(){
		return this.Telefono;
	}
	public IntegerProperty getVotoProperty() {
		return this.Voto;
	}
	public StringProperty getFechaProperty(){
		return this.fecha;
	}
	public StringProperty getImagenProperty(){
		return this.Imagen;
	}
	public StringProperty getValidacionProperty(){
		return this.Validacion;
	}
	///
	
	
	
	public void setNombre(String NombreCompleto) {
		this.Nombre = new SimpleStringProperty(NombreCompleto);
	}
	public void setId(String id) {
		this.Id = new SimpleStringProperty(id);
	}
	
	public void setDireccion(StringProperty direccion) {
		this.Direccion = direccion;
	}
	
	public void setGenero(StringProperty genero) {
		this.Genero = genero;
	}
	public void setEdad(int edad) {
		this.Edad = new SimpleIntegerProperty(edad);
	}
	public void setTelefono(StringProperty telefono) {
		this.Telefono = telefono;
	}
	public void setVoto(Integer votos) {
		this.Voto= new SimpleIntegerProperty(votos);
	}
	public void setFecha(StringProperty fecha) {
		this.fecha = fecha;
	}public void setImagen(StringProperty imagen) {
		this.Imagen= imagen;
	}public void setValidacion(StringProperty validacion) {
		this.Validacion = validacion;
	}
	///

	
	public String getNombre() {
		return Nombre.get();
	}
	public String getId() {
		return Id.get();
	}
	public String getDireccion() {
		return Direccion.get(); 
	}
	
	public String getGenero() {
		return Genero.get();
	}
	public Integer getEdad() {
		return Edad.get();
	}
	
	public String getTelefono() {
		return Telefono.get();
	}
	public Integer getVoto() {
		return Voto.get();
	}
	public String getFecha() {
		return fecha.get();
	}
	public String getImagen() {
		return Imagen.get();
	}
	public String getValidacion() {
		return Validacion.get();
	}
}

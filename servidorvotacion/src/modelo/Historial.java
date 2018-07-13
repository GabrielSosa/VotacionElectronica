package modelo;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Historial {

	private StringProperty Nombre;
	private StringProperty Identidad;
	private StringProperty Fecha;
	private StringProperty Presidente;
	private StringProperty Alcalde;
	private StringProperty Diputado;
	
	public Historial(String Nombre,String identidad, String fecha, String presidente,  String alcalde, String diputado) {
		this.Nombre = new SimpleStringProperty(Nombre);
		this.Identidad= new SimpleStringProperty(identidad);
		this.Fecha = new SimpleStringProperty(fecha);
		this.Presidente= new SimpleStringProperty(presidente);
		this.Alcalde = new SimpleStringProperty(alcalde);
		this.Diputado = new SimpleStringProperty(diputado);
     }
	//
	public StringProperty getNombreProperty(){
		return this.Nombre;
	}
	public StringProperty getIdentidadProperty(){
		return this.Identidad;
	}
	public StringProperty getFechaProperty(){
		return this.Fecha;
	}
	public StringProperty getPresidenteProperty(){
		return this.Presidente;
	}
	public StringProperty getAlcaldeProperty(){
		return this.Alcalde;
	}
	public StringProperty getDiputadoProperty(){
		return this.Diputado;
	}
	//
	public void setNombre(String nombre) {
		this.Nombre = new SimpleStringProperty(nombre);
	}
	public void setIdentidad(String identidad) {
		this.Identidad = new SimpleStringProperty(identidad);
	}
	public void setFecha(String fecha) {
		this.Fecha = new SimpleStringProperty(fecha);
	}
	public void setPresidente(String presidente) {
		this.Presidente = new SimpleStringProperty(presidente);
	}
	public void setAlcalde(String Alcalde) {
		this.Alcalde = new SimpleStringProperty(Alcalde);
	}
	public void setDiputado(String diputado) {
		this.Diputado = new SimpleStringProperty(diputado);
	}
	//
	public String getNombre() {
		return Nombre.get();
	}
	public String getIdentidad() {
		return Identidad.get();
	}
	public String getFecha() {
		return Fecha.get();
	}
	public String getPresidente() {
		return Presidente.get();
	}
	public String getAlcalde() {
		return Alcalde.get();
	}
	public String getDiputado() {
		return Diputado.get();
	}
	
}

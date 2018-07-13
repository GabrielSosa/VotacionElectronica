package modelo;



import application.Main;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class Candidatos {
	
	//private StringProperty CodigoCandidato;
	private StringProperty Nombre;
	private IntegerProperty Edad;
	private StringProperty Genero;
	private StringProperty Id;
	private StringProperty Direccion;
	private StringProperty Partido;
	private StringProperty Rango;
	private Image imagen;
	private IntegerProperty Votos;
	//contuctor
	public Candidatos(String Nombre, int edad, String id, String Genero,
			           String direccion, String partido, String rango,Image imagen, int votos) {
		//this.CodigoCandidato = new SimpleStringProperty(CodigoCandidato);
		this.Nombre = new SimpleStringProperty(Nombre);
		this.Edad = new SimpleIntegerProperty(edad);
		this.Id = new SimpleStringProperty(id);
		this.Genero= new SimpleStringProperty(Genero);
		this.Direccion = new SimpleStringProperty(direccion);
		this.Partido = new SimpleStringProperty(partido);
		this.Rango= new SimpleStringProperty(rango);
		this.imagen=imagen;
		this.Votos= new SimpleIntegerProperty(votos);
	}
	public Candidatos(){}
	
	///
	//public StringProperty getCodigoCandidatoProperty(){
	//	return this.CodigoCandidato;
	//}
	public StringProperty getNombreProperty(){
		return this.Nombre;
	}
	public IntegerProperty getEdadProperty(){
		return this.Edad;
	}
	public StringProperty getIdProperty(){
		return this.Id;
	}
	public StringProperty getGeneroProperty(){
		return this.Genero;
	}
	public StringProperty getDireccionProperty() {
		return this.Direccion;
	}
	public StringProperty getPartidoProperty() {
	return this.Partido;
	}
	public StringProperty getRangoProperty() {
		return this.Rango;
	}
		
	public IntegerProperty getVotosProperty() {
		return this.Votos;
	}
	
	///
	
	//public void setCodigoCandidato(String CodigoCandidato) {
	//	this.CodigoCandidato = new SimpleStringProperty(CodigoCandidato);
	//}
	public void setNombre(String NombreCompleto) {
		this.Nombre = new SimpleStringProperty(NombreCompleto);
	}
	public void setEdad(int edad) {
		this.Edad = new SimpleIntegerProperty(edad);
	}
	public void setId(String id) {
		this.Id = new SimpleStringProperty(id);
	}
	public void setGenero(StringProperty genero) {
		this.Genero = genero;
	}
	public void setDireccion(StringProperty direccion) {
		this.Direccion = direccion;
	}
	public void setPartido(String partido) {
		this.Partido = new SimpleStringProperty(partido);
	}
	public void setRango(String rango) {
		this.Rango = new SimpleStringProperty(rango);
	}
	public void setVotos(Integer votos) {
		this.Votos= new SimpleIntegerProperty(votos);
	}
	///
	//public String getCodigoCandidato() {
	//	return CodigoCandidato.get();
	//}
	public String getNombre() {
		return Nombre.get();
	}
	public Integer getEdad() {
		return Edad.get();
	}
	public String getId() {
		return Id.get();
	}
	public String getGenero() {
		return Genero.get();
	}
	public String getDireccion() {
		return Direccion.get(); 
	}
	public String getPartido() {
		return Partido.get();
	}
	public String getRango() {
		return Rango.get();
	}
	public Integer getVotos() {
		return Votos.get();
	}
	//Mis referencias de main
	private Main main;
	public Main getMain() {
		return main;
	}
	public void setMain(Main main) {
		this.main = main;
	}
	//
	//solo lo de la imagen
	public Image getImagen() {
		return imagen;
	}
	public void setImagen(Image imagen) {
		this.imagen = imagen;
	}

	@Override
	public String toString(){
		return Nombre.get();
	}
	
	
	//metodos para la base de datos
/*
	public void actualizarRegistro(){
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");//Cargar el driver
			String url = "jdbc:oracle:thin:@localhost:1521:xe"; //Connection String
	        Connection conexion = DriverManager.getConnection(url, AlumnosController.USUARIO,AlumnosController.CONTRASENA); //Conectarse a la base de datos
	        //Cargar informacion de alumnos
	        PreparedStatement instruccionModificar= 
	        		conexion.prepareStatement("UPDATE ALUMNOS "+
							"SET NOMBRE = ?, "+
							    "APELLIDO = ? , "+
							    "EDAD =  ?, "+
							    "GENERO = ?, "+
							    "CODIGO_CARRERA = ? "+
							"WHERE CUENTA = ?");
	        
	        instruccionModificar.setString(1, nombre.get());
	        instruccionModificar.setString(2, apellido.get());
	        instruccionModificar.setInt(3, edad.get());
	        instruccionModificar.setString(4, genero.get());
	        instruccionModificar.setInt(5, carrera.getCodigoCarrera());
	        instruccionModificar.setString(6, cuenta.get());
	        instruccionModificar.executeUpdate();
	        conexion.close();
	        
		} catch(Exception e){
			e.printStackTrace();
		}
	}
*/
	
}

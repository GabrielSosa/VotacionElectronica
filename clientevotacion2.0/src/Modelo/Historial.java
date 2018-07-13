package Modelo;

import java.util.ArrayList;

public class Historial {

	private String id;
	private String nombre;
	private String presidente;
	private String alcalde;
	private ArrayList<String> diputados = new ArrayList<String>();

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPresidente() {
		return presidente;
	}

	public void setPresidente(String presidente) {
		this.presidente = presidente;
	}

	public String getAlcalde() {
		return alcalde;
	}

	public void setAlcalde(String alcalde) {
		this.alcalde = alcalde;
	}

	public ArrayList<String> getDiputados() {
		return diputados;
	}

	public void setDiputados(ArrayList<String> diputados) {
		this.diputados = diputados;
	}
	
	public void addDiputados(String diputado){
		diputados.add(diputado);
	}
	
	public String Diputado(int i){
		return diputados.get(i);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}

package es.urjc.code;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class Ficha {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	

	private String nombre;
	private String descripcion;	
	
	protected Ficha(){
		
	}
	
	public Ficha (String n, String d) {
		this.nombre = n;		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}

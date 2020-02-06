package es.urjc.code;

public class Ficha {
	private String nombre;
	private String descripcion;	
	
	public Ficha (String n, String d) {
		this.nombre = n;
		this.descripcion = d;
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

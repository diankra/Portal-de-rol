package es.urjc.code;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

	@Entity

public class Mensaje implements Serializable{


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne

	private Usuario autor;
	private String texto;
	@ManyToOne
	private Hilo hilo;
	
	private boolean tieneImagen = false;
	private String rutaImagen = "";
	protected Mensaje() {
		
	}
	
	public Mensaje(Usuario autor, String texto) {
		super();
		this.autor = autor;
		this.texto = texto;
		tieneImagen = false;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Hilo getHilo() {
		return hilo;
	}

	public void setHilo(Hilo hilo) {
		this.hilo = hilo;
	}

	public Usuario getAutor() {
		return autor;
	}

	public void setAutor(Usuario autor) {
		this.autor = autor;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	public void setImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}
	
	public String getImage() {
		return rutaImagen;
	}

	public boolean tieneImagen() {
		return tieneImagen;
	}

	public void setTieneImagen(boolean tieneImagen) {
		this.tieneImagen = tieneImagen;
	}
}

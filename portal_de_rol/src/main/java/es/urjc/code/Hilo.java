package es.urjc.code;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Hilo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String titulo;
	private Usuario autor;
	@OneToMany
	private List<Mensaje> mensajes = new ArrayList<Mensaje>();
	
	protected Hilo() {
		
	}
	
	public Hilo(String titulo, Usuario autor, Mensaje primerMensaje) {
		super();
		this.titulo = titulo;
		this.autor = autor;
		this.mensajes.add(primerMensaje);
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Usuario getAutor() {
		return autor;
	}

	public void setAutor(Usuario autor) {
		this.autor = autor;
	}

	public List<Mensaje> getMensajes() {
		return mensajes;
	}

	public void setMensajes(List<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}
	
	public void addMensaje(Mensaje mensaje) {
		this.mensajes.add(mensaje);
	}

	@Override
	public String toString() {
		return "Hilo [titulo=" + titulo + ", autor=" + autor + ", mensajes=" + mensajes + "]";
	}
	
	
	
}

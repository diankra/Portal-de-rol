package es.urjc.code;

import java.util.List;

public class Hilo {

	private String titulo;
	private Usuario autor;
	private List<Mensaje> mensajes;
	
	public Hilo(String titulo, Usuario autor, List<Mensaje> mensajes) {
		super();
		this.titulo = titulo;
		this.autor = autor;
		this.mensajes = mensajes;
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
	
	
}

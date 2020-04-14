package es.urjc.code;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

	@Entity
	@Inheritance(strategy = InheritanceType.JOINED)
public class Hilo implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@NotNull
	private String titulo;
	@NotNull
	@ManyToOne
	private Usuario autor;
	
	@OneToMany(mappedBy = "hilo")
	private List<Mensaje> mensajes = new ArrayList<Mensaje>();
	
	protected Hilo() {
		
	}
	
	public Hilo(String titulo, Usuario autor, Mensaje primerMensaje) {
		super();
		this.titulo = titulo;
		this.autor = autor;
		this.mensajes.add(primerMensaje);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
	
	public void removeMensaje(int index) {
		this.mensajes.remove(index);
	}
	
	public void addMensaje(Mensaje mensaje) {
		this.mensajes.add(mensaje);
	}

	@Override
	public String toString() {
		return "Hilo [titulo=" + titulo + ", autor=" + autor + ", mensajes=" + mensajes + "]";
	}
	
	
	
}

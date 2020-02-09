package es.urjc.code;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

	@Entity

public class Partida extends Hilo{

	@ManyToOne

	private Usuario master;
	private boolean privada;
	
	@ManyToMany
	private List<Usuario> jugadores = new ArrayList<Usuario>();


	@OneToMany(mappedBy = "partida")

	private List<FichaMundo> fichas = new ArrayList<FichaMundo>();
	
	protected Partida() {
		
	}
	
	public Partida(String titulo, boolean privada, Usuario master, Mensaje primerMensaje) {
		super(titulo, master, primerMensaje);
		this.master = master;
		this.privada = privada;
	}

	public Usuario getMaster() {
		return master;
	}

	public void setMaster(Usuario master) {
		this.master = master;
	}

	public List<Usuario> getJugadores() {
		return jugadores;
	}

	public void setJugadores(List<Usuario> jugadores) {
		this.jugadores = jugadores;
	}

	public List<FichaMundo> getFichas() {
		return fichas;
	}

	public void setFichas(List<FichaMundo> fichas) {
		this.fichas = fichas;
	}

	public boolean isPrivada() {
		return privada;
	}

	public void setPrivada(boolean privada) {
		this.privada = privada;
	}

	public void addJugador(Usuario u) {
		boolean existe = false;
		for (Usuario user : this.jugadores) {
			if (user.equals(u)) {
				existe = true;
				break;			
			}
		}
		if (!existe) {
			jugadores.add(u);
		}
	}
	
	public void eliminarJugador(Usuario u) {
		for (Usuario user : this.jugadores) {
			if (user.equals(u)) {
				jugadores.remove(user);
				break;			
			}
		}
	}
	
	public void addFicha(FichaMundo f) {
		boolean existe = false;
		for (FichaMundo ficha : this.fichas) {
			if (ficha.equals(f)) {
				existe = true;
				break;			
			}
		}
		if (!existe) {
			fichas.add(f);
		}
	}
	
	public void eliminarFicha(FichaMundo f) {
		for (FichaMundo ficha : this.fichas) {
			if (ficha.equals(f)) {
				fichas.remove(ficha);
				break;			
			}
		}
	}
}

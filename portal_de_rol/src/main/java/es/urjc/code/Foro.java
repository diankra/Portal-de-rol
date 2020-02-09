package es.urjc.code;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Foro {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Usuario admin;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<Hilo> hilos = new ArrayList<Hilo>();
	@OneToMany(cascade=CascadeType.ALL)
	private List<Partida> partidas = new ArrayList<Partida>();
	
	protected Foro() {
		
	}
	
	public Foro(Usuario admin) {
		this.admin = admin;
	}

	public Usuario getAdmin() {
		return admin;
	}

	public void setAdmin(Usuario admin) {
		this.admin = admin;
	}

	public List<Hilo> getHilos() {
		return hilos;
	}

	public void setHilos(List<Hilo> hilos) {
		this.hilos = hilos;
	}

	public List<Partida> getPartidas() {
		return partidas;
	}

	public void setPartidas(List<Partida> partidas) {
		this.partidas = partidas;
	}
	
	public void addHilo(Hilo h) {
		boolean existe = false;
		for (Hilo hilo : this.hilos) {
			if (hilo.equals(h)) {
				existe = true;
				break;			
			}
		}
		if (!existe) {
			hilos.add(h);
		}
	}
	
	public void eliminarHilo(Hilo h) {
		for (Hilo hilo : this.hilos) {
			if (hilo.equals(h)) {
				hilos.remove(hilo);
				break;			
			}
		}
	}
	
	public void addPartida(Partida p) {
		boolean existe = false;
		for (Partida partida : this.partidas) {
			if (partida.equals(p)) {
				existe = true;
				break;			
			}
		}
		if (!existe) {
			partidas.add(p);
		}
	}
	
	public void eliminarPartida(Partida p) {
		for (Partida partida : this.partidas) {
			if (partida.equals(p)) {
				partidas.remove(partida);
				break;			
			}
		}
	}
	
}

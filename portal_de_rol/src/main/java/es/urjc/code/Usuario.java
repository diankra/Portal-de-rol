package es.urjc.code;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
	
	@Entity
	@Component
	@SessionScope

public class Usuario {

	@Id
	private String nombre;
	@Column(unique = true)
	private String correo;
	private String password;
	private boolean isAdmin;
	
	@OneToMany(mappedBy="autor")
	private List<Mensaje> mensajes = new ArrayList<Mensaje>();
	@OneToMany(mappedBy="autor")
	private List<Hilo> hilos = new ArrayList<Hilo>();
	
	@OneToMany(mappedBy="propietario")
	private List<FichaJugador> fichas = new ArrayList<FichaJugador>();
	@OneToMany(mappedBy="master")
	private List<Partida> partidasMaster = new ArrayList<Partida>();
	@ManyToMany(mappedBy="jugadores")
	private List<Partida> partidasJugador = new ArrayList<Partida>();


	protected Usuario() {

	}

	public Usuario(String nombre, String correo, String password) {
		super();
		this.nombre = nombre;
		this.correo = correo;
		this.password = password;
	}


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<FichaJugador> getFichas() {
		return fichas;
	}

	public void setFichas(List<FichaJugador> fichas) {
		this.fichas = fichas;
	}

	public void addFicha(FichaJugador f) {
		this.fichas.add(f);
	}
	
	public List<Partida> getPartidasMaster() {
		return partidasMaster;
	}

	public void setPartidasMaster(List<Partida> partidasMaster) {
		this.partidasMaster = partidasMaster;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public List<Mensaje> getMensajes() {
		return mensajes;
	}

	public void setMensajes(List<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}

	public List<Hilo> getHilos() {
		return hilos;
	}

	public void setHilos(List<Hilo> hilos) {
		this.hilos = hilos;
	}

	public List<Partida> getPartidasJugador() {
		return partidasJugador;
	}	

	public void setPartidasJugador(List<Partida> partidasJugador) {
		this.partidasJugador = partidasJugador;
	}
	
	public void addPartidaJugador(Partida partida) {
		partidasJugador.add(partida);		
	}
	
	
}

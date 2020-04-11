package es.urjc.code;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity

public class Usuario implements Serializable{

	@Id
	private String nombre;
	@Column(unique = true)
	private String correo;
	@NotNull
	private String passwordHash;

	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles = new ArrayList<String>();

	@OneToMany(mappedBy = "autor")
	private List<Mensaje> mensajes = new ArrayList<Mensaje>();
	@OneToMany(mappedBy = "autor")
	private List<Hilo> hilos = new ArrayList<Hilo>();

	@OneToMany(mappedBy = "propietario")
	private List<FichaJugador> fichas = new ArrayList<FichaJugador>();
	@OneToMany(mappedBy = "master")
	private List<Partida> partidasMaster = new ArrayList<Partida>();
	@ManyToMany(mappedBy = "jugadores")
	private List<Partida> partidasJugador = new ArrayList<Partida>();


	protected Usuario() {

	}

	public Usuario(String nombre, String correo, String password, String role) {
		super();
		this.nombre = nombre;
		this.correo = correo;
		this.passwordHash = new BCryptPasswordEncoder().encode(password);
		this.roles.add(role);
	}

	public Usuario(String nombre, String correo, String password, List<String> roles) {
		super();
		this.nombre = nombre;
		this.correo = correo;
		this.passwordHash = new BCryptPasswordEncoder().encode(password);
		this.roles = roles;
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
		return passwordHash;
	}

	public void setPassword(String password) {
		this.passwordHash = password;
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

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

}

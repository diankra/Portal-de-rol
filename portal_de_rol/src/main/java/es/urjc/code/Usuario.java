package es.urjc.code;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Component
@SessionScope

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String nombre;
	private String correo;
	private String password;
	
	@OneToMany(mappedBy="propietario")
	private List<FichaJugador> fichas = new ArrayList<FichaJugador>();
	@OneToMany(mappedBy="master")
	private List<Partida> partidasMaster = new ArrayList<Partida>();

	public List<Partida> getPartidasMaster() {
		return partidasMaster;
	}

	public void setPartidasMaster(List<Partida> partidasMaster) {
		this.partidasMaster = partidasMaster;
	}

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
	
	
}

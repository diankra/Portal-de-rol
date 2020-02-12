package es.urjc.code;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

	@Entity

public class FichaJugador extends Ficha {

//	La ficha jugador tiene el nombre, si es NPC o no definido con tipo, su clase y su raza.
//	Por lo que necesitamos los parametros nombre, tipo, clase y raza definidos en ficha_heroes

	
	@NotNull
	@ManyToOne
	private Usuario propietario;
	@NotNull
	private boolean jugable;
	private String clase;
	private String raza;
	
	protected FichaJugador() {}

	public FichaJugador(Usuario propietario, String name, boolean jugable, String clase, String raza) {
		super(name, "");
		this.propietario = propietario;
		this.jugable = jugable;
		this.clase = clase;
		this.raza = raza;
	}

	public Usuario getPropietario() {
		return propietario;
	}

	public void setPropietario(Usuario propietario) {
		this.propietario = propietario;
	}

	public boolean isJugable() {
		return jugable;
	}

	public void setJugable(boolean type) {
		this.jugable = type;
	}

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public String getRaza() {
		return raza;
	}

	public void setRaza(String raza) {
		this.raza = raza;
	}

}

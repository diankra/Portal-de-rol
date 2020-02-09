package es.urjc.code;

import javax.persistence.Entity;

public class FichaJugador extends Ficha {

//	La ficha jugador tiene el nombre, si es NPC o no definido con tipo, su clase y su raza.
//	Por lo que necesitamos los parametros nombre, tipo, clase y raza definidos en ficha_heroes

	private Usuario propietario;
	private String name;
	private boolean type;
	private String clase;
	private String raza;

	public FichaJugador(Usuario propietario, String name, boolean type, String clase, String raza) {
		super(name);
		this.propietario = propietario;
		this.name = name;
		this.type = type;
		this.clase = clase;
		this.raza = raza;
	}

	public Usuario getPropietario() {
		return propietario;
	}

	public void setPropietario(Usuario propietario) {
		this.propietario = propietario;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isType() {
		return type;
	}

	public void setType(boolean type) {
		this.type = type;
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

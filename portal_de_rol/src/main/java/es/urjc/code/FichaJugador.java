package es.urjc.code;

import javax.persistence.Entity;



public class FichaJugador extends Ficha{

	private Usuario propietario;
	
	protected FichaJugador() {
		
	}
	
	public FichaJugador(String n, String d, Usuario p) {
		super(n, d);
		this.setPropietario(p);
	}

	public Usuario getPropietario() {
		return propietario;
	}

	public void setPropietario(Usuario propietario) {
		this.propietario = propietario;
	}

}

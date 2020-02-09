package es.urjc.code;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class FichaMundo extends Ficha{
	
	@ManyToOne
	private Partida partida;
	
	protected FichaMundo() {
		
	}
	
	public FichaMundo(String n, String d, Partida p) {
		super(n, d);
		this.setPartida(p);
	}

	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
	}

}

package es.urjc.code;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class FichaMundo extends Ficha{
	
	@ManyToOne
	private Partida partida;
	private String tipo;
	
	protected FichaMundo() {
		
	}
	
	public FichaMundo(String n, String d, Partida p, String t) {
		super(n, d);
		this.setPartida(p);
		this.tipo = t;
	}

	public Partida getPartida() {
		return partida;
	}

	public void setPartida(Partida partida) {
		this.partida = partida;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}

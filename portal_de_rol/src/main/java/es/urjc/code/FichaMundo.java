package es.urjc.code;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity

public class FichaMundo extends Ficha{
		
	@ManyToOne
	private Partida partida;
	private String tipo;
	
	protected FichaMundo() {
		
	}
	
	public FichaMundo(String n, String d, String t) {
		super(n, d);
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
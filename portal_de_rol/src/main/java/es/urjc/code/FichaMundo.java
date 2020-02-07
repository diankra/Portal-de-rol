package es.urjc.code;

import javax.persistence.Entity;

@Entity
public class FichaMundo extends Ficha{
	
	protected FichaMundo() {
		
	}
	
	public FichaMundo(String n, String d) {
		super(n, d);
	}

}

package es.urjc.code;

import javax.persistence.Entity;


public class Admin extends Usuario{
	
	protected Admin() {
		
	}
	
	public Admin(String nombre, String correo, String password) {
		super(nombre, correo, password);		
	}

}

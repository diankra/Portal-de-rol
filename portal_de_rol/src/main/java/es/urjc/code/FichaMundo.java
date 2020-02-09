package es.urjc.code;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;


public class FichaMundo extends Ficha{
	
	private String tipo;

	
	public FichaMundo(String n, String tipo, String descripcion) {
		super(n, descripcion);
	}
	
}
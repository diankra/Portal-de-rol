package es.urjc.code;

import javax.persistence.Entity;

public class FichaEnemigo extends FichaMundo {

//	La ficha del enemigo tiene el nombre, su tipo y alineamiento.
//	Por lo que necesitamos los parametros nombre, tipo y alineamiento.

	private String name;
	private String Tipo;
	private String Alineamiento;

	public FichaEnemigo(String name, String type, String alignment) {
		super(name);
		this.name = name;
		this.Tipo = type;
		this.Alineamiento = alignment;
	}

}

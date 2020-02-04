package es.urjc.code;

public class Mensaje {

	private Usuario autor;
	private String texto;
	
	public Mensaje(Usuario autor, String texto) {
		super();
		this.autor = autor;
		this.texto = texto;
	}

	public Usuario getAutor() {
		return autor;
	}

	public void setAutor(Usuario autor) {
		this.autor = autor;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	
}

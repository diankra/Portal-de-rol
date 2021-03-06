package es.urjc.code;

public class Tirada {
	private long id;
	private String usuario;
	private long partida;
	private int resultado;
	
	public Tirada(long id, String usuario, long partida, int resultado) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.partida = partida;
		this.resultado = resultado;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public long getPartida() {
		return partida;
	}

	public void setPartida(long partida) {
		this.partida = partida;
	}

	public int getResultado() {
		return resultado;
	}

	public void setResultado(int resultado) {
		this.resultado = resultado;
	}
	
	
}

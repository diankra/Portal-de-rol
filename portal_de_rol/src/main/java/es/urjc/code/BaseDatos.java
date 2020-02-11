package es.urjc.code;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

@Controller
public class BaseDatos implements CommandLineRunner{

	@Autowired
	private UsuarioRepository usuarios;
	@Autowired
	private FichaMundoRepository fichasmundo;
	@Autowired
	private FichaJugadorRepository fichasjugador;
	@Autowired
	private HiloRepository hilos;
	@Autowired
	private MensajeRepository mensajes;
	@Autowired
	private PartidaRepository partidas;
	@Autowired 
	private ForoRepository foro;

	
	public Usuario saveUsuario(Usuario u) {
		return usuarios.save(u);
	}
	
	public Usuario findUsuario(long id) {
		return usuarios.findUsuarioById(id);
	}
	public Hilo saveHilo(Hilo h) {
		return hilos.save(h);
	}
	
	public Mensaje saveMensaje(Mensaje m) {
		return mensajes.save(m);
	}
	public List<Hilo> getAllHilos(){
		return hilos.findAll();
	}
	
	public Hilo getHilo(long id) {
		return hilos.findHiloById(id);
	}
	@Override
	public void run(String... args) throws Exception {
		
		
	}

}

package es.urjc.code;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
//ELIMINAR
@Controller
public class BaseDatos implements CommandLineRunner{

	@Autowired
	private UsuarioRepository usuarios;
	@Autowired
	private FichaMundoRepository fichasMundo;
	@Autowired
	private FichaJugadorRepository fichasJugador;
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
	
	public Usuario findUsuario(String nombre) {
		return usuarios.findUsuarioByNombre(nombre);
	}
	
	public Usuario findUsuarioByCorreo(String mail) {
		return usuarios.findUsuarioByCorreo(mail);
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
	
	public void removeMensaje(Mensaje m) {
		mensajes.delete(m);
	}
	
	public Partida savePartida(Partida p) {
		return partidas.save(p);
	}
	
	public List<Partida> getPartidasPublicas() {
		return partidas.findPartidasByPrivada(false);
	}
	
	public Partida getPartida(long id)
	{
		return partidas.findPartidaById(id);
	}
	
	public List<Partida> getAllPartidas(){
		return partidas.findAll();
	}
	public FichaJugador saveFichaJugador(FichaJugador f) {
		return fichasJugador.save(f);
	}
	
	public FichaJugador findFichaJugador(long id) {
		return fichasJugador.findFichaById(id);
	}
	public FichaMundo saveFichaMundo(FichaMundo f) {
		return fichasMundo.save(f);
	}
	
	public List<FichaMundo> findFichasLibres(){
		return fichasMundo.findByPartida(null);
	}
	
	public FichaMundo findFichaMundo(long id) {
		return fichasMundo.findFichaMundoById(id);
	}
	@Override
	public void run(String... args) throws Exception {
		
		
	}

}

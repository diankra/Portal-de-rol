package es.urjc.code;

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
	
	
	@Override
	public void run(String... args) throws Exception {
		
		
	}

}

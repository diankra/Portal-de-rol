package es.urjc.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CrearPartidaController {

	@Autowired
	private UsuarioRepository usuariosBD;
	@Autowired
	private PartidaRepository partidasBD;
	@Autowired
	private MensajeRepository mensajesBD;
	
	@GetMapping("/crear_partida")
	public String crear_partida(Model model) {

		return "crear_partida";
	}

	@PostMapping("/crear_partida/aceptar_nueva_partida")
	public String aceptarNuevaPartida(Model model, @RequestParam String nombre,
			@RequestParam(required = false) String privada, @RequestParam String invitados,
			@RequestParam String descripcion) {
		Mensaje men = new Mensaje(LoginController.getUsuario(), descripcion);
		Partida partida;
		if (privada != null) {
			model.addAttribute("tipo", "privada");
			partida = new Partida(nombre, true, LoginController.getUsuario(), men);
		} else {
			model.addAttribute("tipo", "publica");
			partida = new Partida(nombre, false, LoginController.getUsuario(), men);
		}
		men.setHilo(partida);

		LoginController.getUsuario().addPartidaJugador(partida);
		String usuariosInvitados[] = invitados.split(", ");
		for (String name : usuariosInvitados) { // Se comprueba si los usuarios introducidos son validos y se añaden si
												// es el caso
			Usuario u = usuariosBD.findUsuarioByNombre(name);
			if (u != null) {
				u.addPartidaJugador(partida);
				partida.addJugador(u);
			}

		}
		partidasBD.save(partida);
		mensajesBD.save(men);
		return "aceptar_nueva_partida";
	}
}
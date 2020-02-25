package es.urjc.code;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PartidasPublicasController {

	@Autowired 
	private PartidaRepository partidasBD;
	@Autowired
	private MensajeRepository mensajesBD;
	
	@GetMapping("/partidas_publicas")
	public String partidas(Model model) {

		model.addAttribute("partidasPublicas", partidasBD.findAll());

		return "partidas_publicas";
	}

	@GetMapping("partidas_publicas/{partida}")
	public String partida(Model model, @PathVariable long partida, HttpServletRequest request) {

		Partida partidaActual = partidasBD.findPartidaById(partida);

		model.addAttribute("titulo", partidaActual.getTitulo());
		model.addAttribute("partida", partida);
		model.addAttribute("mensajes", partidaActual.getMensajes());
		model.addAttribute("admin", request.isUserInRole("ADMIN"));
		return "partida";
	}

	@GetMapping("partidas_publicas/{partida}/escribir_mensaje_partida")
	public String escribirMensajePartida(Model model, @PathVariable long partida) {

		model.addAttribute("titulo", partida);
		return "escribir_mensaje_partida";
	}

	@PostMapping("partidas_publicas/{partida}/escribir_mensaje_partida/aceptar")
	public String aceptarMensajePartida(Model model, @PathVariable long partida, @RequestParam String mensajeEscrito) {

		Partida partidaActual = partidasBD.findPartidaById(partida);
		String respuesta = "";
		respuesta = "Mensaje escrito para la partida " + partidaActual.getTitulo();
		Mensaje m = new Mensaje(LoginController.getUsuario(), "Espectador: " + mensajeEscrito);
		m.setHilo(partidaActual);

		partidaActual.addMensaje(m);
		mensajesBD.save(m);

		model.addAttribute("cadena", respuesta);
		model.addAttribute("titulo", partida);
		return "mensaje_escrito_partida";
	}

	@GetMapping("partidas_publicas/{partida}/{index}")
	public String eliminarMensajePartida(Model model, @PathVariable long partida, @PathVariable int index) {
		Partida actual = partidasBD.findPartidaById(partida);
		Mensaje m = actual.getMensajes().get(index - 1);
		actual.getMensajes().remove(index - 1);

		mensajesBD.delete(m);

		model.addAttribute("titulo", partida);
		return "mensaje_eliminado_partida";
	}

}

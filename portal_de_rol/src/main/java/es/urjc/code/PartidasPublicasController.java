package es.urjc.code;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class PartidasPublicasController {

	// Para el envio de correos
	final String username = "portalderol.dad@gmail.com";
	final String password = "DaD1234DaD";

	@Autowired
	private PartidaRepository partidasBD;
	@Autowired
	private MensajeRepository mensajesBD;
	@Autowired
	private UserComponent userComponent;

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
		Mensaje m = new Mensaje(userComponent.getLoggedUser(), "Espectador: " + mensajeEscrito);
		m.setHilo(partidaActual);

		partidaActual.addMensaje(m);
		mensajesBD.save(m);

		// Envio de correo con actualizaciones
		String subject = "Actualizacion en la partida " + partidaActual.getTitulo() + " de PortalDeRol";
		String body = "<h2>El usuario " + userComponent.getLoggedUser().getNombre() + " ha publicado "
				+ "un mensaje en una partida en la que participas o estás suscrito. Para verlo "
				+ "pulsa en el siguiente enlace:\n </h2> "
				+ "<a href= \"https://192.168.33.10:8443/partidas_publicas/" + partidaActual.getId()
				+ "\">Acceder a la partida</a>";
		String to;
		if (!partidaActual.getMaster().equals(userComponent.getLoggedUser()))
				to = partidaActual.getMaster().getCorreo() + ", ";
		else to = "";
		for (Usuario u : partidaActual.getJugadores()) {
			if (!u.equals(userComponent.getLoggedUser()))
				to += u.getCorreo() + ", ";
		}

		if (!to.equals("")) {
			Correo correo = new Correo(username, to, subject, body, username, password);
			RestTemplate restTemplate = new RestTemplate();
			String url = "http://127.0.0.1:8080/enviar_correo";
			restTemplate.postForObject(url, correo, Correo.class);
		}

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

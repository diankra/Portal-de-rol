package es.urjc.code;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class PartidasPrivadasController {

	@Autowired
	private PartidaRepository partidasBD;
	@Autowired
	private MensajeRepository mensajesBD;
	@Autowired
	private FichaJugadorRepository fichasJugadorBD;
	@Autowired
	private FichaMundoRepository fichasMundoBD;
	@Autowired
	private UserComponent userComponent;
	
	@GetMapping("/partidas_privadas")
	public String partidas_privadas(Model model) {

		model.addAttribute("partidasPrivadas", partidasBD.findAll());

		return "partidas_privadas";
	}

	@GetMapping("partidas_privadas/{partidaPrivada}")
	public String partidaPrivada(Model model, @PathVariable long partidaPrivada,  HttpServletRequest request) {

		Partida partidaActual = partidasBD.findPartidaById(partidaPrivada);

		model.addAttribute("titulo", partidaActual.getTitulo());
		model.addAttribute("partida", partidaPrivada);
		model.addAttribute("mensajes", partidaActual.getMensajes());
		model.addAttribute("admin", request.isUserInRole("ADMIN"));
		return "partidaPrivada";
	}

	@GetMapping("partidas_privadas/{id}/escribir_mensaje_partida_privada")
	public String escribirMensajePartidaPrivada(Model model, @PathVariable long id) {

		model.addAttribute("fichas", fichasJugadorBD.findAll()); //para que ponga todas las fichas de pj
		model.addAttribute("titulo", id);
		return "escribir_mensaje_partida_privada";
	}

	@PostMapping("partidas_privadas/{titulo}/escribir_mensaje_partida_privada/aceptar")
	public String aceptarMensajePartidaPrivada(Model model, @PathVariable long titulo,
			@RequestParam String mensajeEscrito, @RequestParam(required = false) String idFicha) {

		Ficha fichaActual = null;

		String inicio = "";
		if (idFicha != null) {
			fichaActual = fichasJugadorBD.findFichaById(Integer.parseInt(idFicha));
			inicio = fichaActual.getNombre() + " : ";
		}
		Partida partidaActual = partidasBD.findPartidaById(titulo);
//		Usuario userActual = baseDatos.findUsuario(usuario.getNombre());
		String respuesta = "";
//		if (userActual == null) { // Ñapa incoming. Programming the Spanish way
//			respuesta = "No se ha escrito el mensaje. Usuario inválido";
//		} else {
		respuesta = "Mensaje escrito para la partida " + partidaActual.getTitulo();
		Mensaje m = new Mensaje(userComponent.getLoggedUser(), inicio + mensajeEscrito);
		partidaActual.addMensaje(m);
		m.setHilo(partidaActual);

		mensajesBD.save(m);
		// }
		model.addAttribute("cadena", respuesta);
		model.addAttribute("titulo", titulo);
		return "mensaje_escrito_partida_privada";
	}

	@RequestMapping("partidas_privadas/{id}/add_ficha")
	public String addFichaMundo(Model model, @PathVariable long id) {

		model.addAttribute("fichas", fichasMundoBD.findByPartida(null));
		model.addAttribute("id", id);
		return "add_ficha";
	}

	@PostMapping("partidas_privadas/{id}/add_ficha/aceptar")
	public String aceptarFichaMundo(Model model, @PathVariable long id,
			@RequestParam(required = false) List<String> idFicha) {
		if (idFicha != null) {
			Partida partidaActual = partidasBD.findPartidaById(id);
			for (String s : idFicha) {
				int idMundo = Integer.parseInt(s);
				FichaMundo fm = fichasMundoBD.findFichaMundoById(idMundo);
				fichasMundoBD.save(fm);
				partidaActual.addFicha(fm);
				fm.setPartida(partidaActual);
			}

			partidasBD.save(partidaActual);
		}
		return "aceptar_add_ficha";
	}

	@RequestMapping("partidas_privadas/{id}/tirar_dado")
	public String tirarDado(Model model, @PathVariable long id) {
		Partida actual = partidasBD.findPartidaById(id);
		RestTemplate restTemplate = new RestTemplate();
		String url = "http://127.0.0.1:8082/tirada/"+actual.getId()+"/";
		Integer res = restTemplate.postForObject(url, userComponent.getLoggedUser().getNombre(), Integer.class);
		model.addAttribute("id", id);
		model.addAttribute("tirada", res.intValue());
		return "tira_dado";
	}
	
	@RequestMapping("partidas_privadas/{id}/ver_tiradas")
	public String verTiradas(Model model, @PathVariable long id) {
		String url = "http://127.0.0.1:8082/getTiradas/"+id+"/";
		RestTemplate restTemplate = new RestTemplate();
		List<Tirada> listaTiradas = restTemplate.getForObject(url, List.class);
		model.addAttribute("id", id);
		model.addAttribute("tiradas", listaTiradas);
		return "ver_tiradas";
	}
	@RequestMapping("partidas_privadas/{id}/consultar_fichas")
	public String consultarFichas(Model model, @PathVariable long id) {
		Partida partidaActual = partidasBD.findPartidaById(id);

		model.addAttribute("fichas", partidaActual.getFichas());
		model.addAttribute("idPartida", id);
		return "consultar_fichas";
	}

	@RequestMapping("partidas_privadas/{id}/consultar_fichas/{id_ficha}")
	public String verFichaMundo(Model model, @PathVariable long id, @PathVariable long id_ficha) {

		FichaMundo fActual = fichasMundoBD.findFichaMundoById(id_ficha);
		model.addAttribute("ficha", fActual);

		model.addAttribute("id", id);
		return "consultar_ficha_mundo";
	}

	@GetMapping("partidas_privadas/{id}/{index}")
	public String eliminarMensajePartidaPrivada(Model model, @PathVariable long id, @PathVariable int index) {
		Partida actual = partidasBD.findPartidaById(id);
		Mensaje m = actual.getMensajes().get(index - 1);
		actual.getMensajes().remove(m);

		mensajesBD.delete(m);

		model.addAttribute("titulo", id);
		return "mensaje_eliminado_partida_privada";
	}

}

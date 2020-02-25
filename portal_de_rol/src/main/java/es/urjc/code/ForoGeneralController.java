package es.urjc.code;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ForoGeneralController {

	@Autowired 
	private ForoRepository foroBD;
	@Autowired
	private HiloRepository hilosBD;
	@Autowired
	private MensajeRepository mensajesBD;
	@Autowired
	private UsuarioRepository usuariosBD;

	@GetMapping("/foro")
	public String foro(Model model) {

		List<Hilo> hilos = new ArrayList<Hilo>();
		for(Hilo h : hilosBD.findAll())
		{
			Partida p = new Partida();
			if(h.getClass() != p.getClass()) {
				hilos.add(h);
			}
		}
		model.addAttribute("hilos", hilos);

		return "foro_general";
	}

	@GetMapping("foro/crear_hilo")
	public String crearHilo(Model model) {

		return "crear_hilo";
	}

	@PostMapping("/foro/crear_hilo/hilo_creado")
	public String hiloCreado(Model model, @RequestParam String titulo, @RequestParam String mensajeEscrito) {
		Hilo h;
		Mensaje m;
//		Usuario userActual = baseDatos.findUsuario(usuario.getNombre());
//		if (userActual == null) { // Ñapa incoming. Programming the Spanish way
//			return "hilo_no_creado";
//		} else {
		m = new Mensaje(LoginController.getUsuario(), mensajeEscrito);
		h = new Hilo(titulo, LoginController.getUsuario(), m);
		m.setAutor(LoginController.getUsuario());
		m.setHilo(h);
		hilosBD.save(h);
		mensajesBD.save(m);
//		}
		return "hilo_creado";
	}

	@GetMapping("foro/{hilo}")
	public String hilo(Model model, @PathVariable long hilo, HttpServletRequest request) {

		// Espero que esto no haya que cambiarlo mucho con la database pero quien sabe

		Hilo hiloActual = hilosBD.findHiloById(hilo);

		model.addAttribute("hilo", hiloActual);

		model.addAttribute("admin", request.isUserInRole("ADMIN"));
		model.addAttribute("mensajes", hiloActual.getMensajes());
		return "hilo_foro";
	}

	@GetMapping("foro/{hilo}/escribir_mensaje")
	public String escribirMensaje(Model model, @PathVariable long hilo) {
		Hilo hiloActual = hilosBD.findHiloById(hilo);

		model.addAttribute("hilo", hiloActual);
		return "escribir_mensaje";
	}

	@PostMapping("foro/{hilo}/escribir_mensaje/aceptar")
	public String aceptarMensaje(Model model, @PathVariable long hilo, @RequestParam String mensajeEscrito) {

		Hilo hiloActual = hilosBD.findHiloById(hilo);
//		Usuario userActual = baseDatos.findUsuario(usuario.getNombre());
//		if (userActual == null) { // Si el usuario actual no está en la BD es que no está registrado
//			model.addAttribute("hilo", hiloActual);
//			return "mensaje_error";
//
//		} else {
		Mensaje m = new Mensaje(LoginController.getUsuario(), mensajeEscrito);
		m.setHilo(hiloActual);
		hiloActual.addMensaje(m);

		mensajesBD.save(m);

		model.addAttribute("hilo", hiloActual);
		return "mensaje_escrito";
		// }
	}

	@GetMapping("foro/{hilo}/{index}")
	public String eliminarMensaje(Model model, @PathVariable long hilo, @PathVariable int index) {
		Hilo actual = hilosBD.findHiloById(hilo);
		Mensaje m = actual.getMensajes().get(index - 1);
		actual.removeMensaje(index - 1);

		mensajesBD.delete(m);

		model.addAttribute("hilo", actual.getId());
		return "mensaje_eliminado";
	}
}

package es.urjc.code;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
	@Autowired
	private UserComponent userComponent;

	@Autowired
	private imageService imgService;
	
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
		m = new Mensaje(userComponent.getLoggedUser(), mensajeEscrito);
		h = new Hilo(titulo, userComponent.getLoggedUser(), m);
		m.setAutor(userComponent.getLoggedUser());
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
	public String aceptarMensaje(Model model, @PathVariable long hilo, @RequestParam String mensajeEscrito, @RequestParam(required = false) MultipartFile imagenFile) throws IOException {

		Hilo hiloActual = hilosBD.findHiloById(hilo);
		Mensaje m = new Mensaje(userComponent.getLoggedUser(), mensajeEscrito);
		m.setHilo(hiloActual);
		hiloActual.addMensaje(m);

		if(!imagenFile.isEmpty()) {
			m.setTieneImagen(true);
			m.setImagen(imgService.saveImage("mensajes", m.getId(), imagenFile));
		}
		mensajesBD.save(m);

		model.addAttribute("hilo", hiloActual);
		return "mensaje_escrito";
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

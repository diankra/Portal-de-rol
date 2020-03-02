package es.urjc.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itextpdf.text.Document;

@Controller
public class FichasController {

	@Autowired
	private UsuarioRepository usuariosBD;
	@Autowired
	private FichaJugadorRepository fichasJugadorBD;
	@Autowired
	private FichaMundoRepository fichasMundoBD;

	@GetMapping("/crear_ficha")
	public String fichas(Model model) {

		return "crear_ficha";
	}

	@GetMapping("/crear_ficha/ficha_heroes")
	public String fichas_heroes(Model model) {

		return "ficha_heroes";
	}

	@GetMapping("/crear_ficha/ficha_enemigos")
	public String fichas_enemigos(Model model) {

		return "ficha_enemigos";
	}

	@GetMapping("/crear_ficha/ficha_objetos")
	public String fichas_objetos(Model model) {

		return "ficha_objetos";
	}

	@GetMapping("/crear_ficha/ficha_habilidades")
	public String fichas_habilidades(Model model) {

		return "ficha_habilidades";
	}

	@GetMapping("/crear_ficha/ficha_loc")
	public String fichas_localizaciones(Model model) {

		return "ficha_loc";
	}

	@PostMapping("/ficha_heroes/aceptar_ficha")
	public String aceptarFicha(Model model, @RequestParam String name, @RequestParam(required = false) String Jugador,
			@RequestParam(required = false)  String Clase, @RequestParam(required = false)  String Raza) {

//		Los siguientes parametros son para mandarlos por moustache y mandarlos por pantalla.
//		Seran el nombre del personaje, el tipo, clase y raza.

		model.addAttribute("name", name);
		model.addAttribute("Jugador", Jugador);
		model.addAttribute("Clase", Clase);
		model.addAttribute("Raza", Raza);

//		Los anteriores parametros y el ID del usuario seran los necesarios para crear la ficha y guardar en la base de datos.
//		ID del usuario, nombre del personaje, tipo del personaje pasado a boolean, clase del personaje y raza del personaje.

		boolean type = false;

		if (Jugador.equals("Jugador")) {
			type = true;
		}

		FichaJugador f = new FichaJugador(LoginController.getUsuario(), name, type, Clase, Raza);
		f = fichasJugadorBD.save(f);
		if (usuariosBD.findUsuarioByNombre(LoginController.getUsuario().getNombre()) != null) {
			LoginController.getUsuario().addFicha(f);
		}

		return "aceptar_ficha";
	}
	
	@PostMapping("/ficha_heroes/aceptar_ficha/convertir_PDF")
	public String convertirFichaPDF(Model model) {
		RestTemplate restTemplate = new RestTemplate();

		String url="http://127.0.0.1:8080/";
		Document fichaPDF =
		restTemplate.getForObject(url, Document.class);
		model.addAttribute("fichaPDF", fichaPDF);
		return "convertir_PDF";
	}

	@PostMapping("/ficha_enemigos/aceptar_ficha_enemigo")
	public String aceptarFichaEnemigo(Model model, @RequestParam String name, @RequestParam(required = false)  String Tipo,
			@RequestParam(required = false)  String Alineamiento) {

		// Los anteriores parametros y el ID del usuario seran los necesarios para crear
		// la ficha y guardar en la base de datos.
//		ID del usuario, nombre del personaje, tipo del personaje pasado a boolean, clase del personaje y raza del personaje.

		FichaMundo f = new FichaMundo(name, "Enemigo", " Alineamiento: " + Alineamiento + " Tipo de enemigo: " + Tipo);
		f = fichasMundoBD.save(f);

		model.addAttribute("ficha", f);
		f = fichasMundoBD.save(f);
		
		return "aceptar_ficha_mundo";
	}

	@PostMapping("/ficha_objetos/aceptar_ficha_objeto")
	public String aceptarFichaObjeto(Model model, @RequestParam String name, @RequestParam(required = false)  String Tipo,
			@RequestParam(required = false)  String Descripcion) {

		FichaMundo f = new FichaMundo(name, "Objeto", " Tipo:" + Tipo + " Descripcion:" + Descripcion);
		f = fichasMundoBD.save(f);
		model.addAttribute("ficha", f);
		return "aceptar_ficha_objeto";
	}

	@PostMapping("/ficha_habilidades/aceptar_ficha_habilidades")
	public String aceptarFichaHabilidades(Model model, @RequestParam String name, @RequestParam(required = false)  String Tipo,
			@RequestParam(required = false)  String Descripcion) {

		FichaMundo f = new FichaMundo(name, "Habilidad", " Tipo:" + Tipo + " Descripcion:" + Descripcion);
		f = fichasMundoBD.save(f);
		model.addAttribute("ficha", f);
		return "aceptar_ficha_objeto";
	}

	@PostMapping("/ficha_loc/aceptar_ficha_loc")
	public String aceptarFichaLoc(Model model, @RequestParam String name, @RequestParam String Tipo,
			@RequestParam String Temperatura, @RequestParam String Descripcion) {

		FichaMundo f = new FichaMundo(name, "Localizacion",
				"Tipo: " + Tipo + " Temperatura: " + Temperatura + " Descripcion: " + Descripcion);
		f = fichasMundoBD.save(f);
		model.addAttribute("ficha", f);
		return "aceptar_ficha_objeto";
	}
	
	/* TENGO QUE HACER EL HTML
	@PostMapping("/ver_fichas")
	public String verFichas(Model model) {
		
		model.addAttribute("fichasJugador", fichasJugadorBD.findAll());
		model.addAttribute("fichasMundo", fichasMundoBD.findAll());
		return "ver_fichas";
	}
	*/

}

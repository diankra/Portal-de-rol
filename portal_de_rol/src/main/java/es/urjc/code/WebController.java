package es.urjc.code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {

	// Estos son datos a cholon que se tienen que quitar, estan solo para el diseño

	Usuario u1 = new Usuario("Bob", "bob@noimporta.com", "contraseña1");
	Mensaje m1 = new Mensaje(u1, "Hola como estan");
	Usuario u2 = new Usuario("Alice", "alice@noimporta.com", "contraseña2");
	Mensaje m2 = new Mensaje(u2, "Mensaje de ejemplo 2");

	Hilo h1 = new Hilo("Ejemplo 1", u1, m1);
	Hilo h2 = new Hilo("Ejemplo 2", u2, m2);
	List<Hilo> hilos = new ArrayList<Hilo>();
	List<Usuario> usuarios = new ArrayList<Usuario>(); // Sería más un set pero como va a ser en bd pues da igual
	boolean empezado = false; // Otra ñapa, espero quitarla con la database
	@Autowired
	Usuario usuario;

	// Hasta aqui zona de datos a cholon

	@GetMapping("/foro")
	public String foro(Model model) {

		// Zona de datos a cholon numero 2
		if (!empezado) {
			hilos.add(h1);
			hilos.add(h2);
			empezado = true;
		}
		List<String> titulos = new ArrayList<String>();
		for (Hilo h : hilos) {
			titulos.add(h.getTitulo());
		}

		model.addAttribute("hilos", titulos);
		// Fin de zona de datos a cholon numero 2

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
		if (usuario.getNombre() == null) { //PREGUNTAR AL PROFE QUE HACER CUANDO NO SE HA IDENTIFICADO NADIE
			m = new Mensaje(u1, mensajeEscrito);
			h = new Hilo(titulo, u1, m); // Esto es para probar y eso
		} else {
			m = new Mensaje(usuario, mensajeEscrito);
			h = new Hilo(titulo, usuario, m); 
		}
		hilos.add(h); //no permitir hilos repetidos, para luego
		return "hilo_creado";
	}

	@GetMapping("foro/{hilo}")
	public String hilo(Model model, @PathVariable String hilo) {

		// Espero que esto no haya que cambiarlo mucho con la database pero quien sabe

		Hilo hiloActual = getHiloActual(hilo);
		model.addAttribute("titulo", hiloActual.getTitulo());
		List<String> mensajes = new ArrayList<String>();
		for (Mensaje m : hiloActual.getMensajes()) {

			mensajes.add(m.getAutor().getNombre().toUpperCase() + ":  " + m.getTexto()); // Un poco ñapa
		}
		model.addAttribute("mensajes", mensajes);
		return "hilo_foro";
	}

	@GetMapping("foro/{hilo}/escribir_mensaje")
	public String escribirMensaje(Model model, @PathVariable String hilo) {

		model.addAttribute("titulo", hilo);
		return "escribir_mensaje";
	}

	@PostMapping("foro/{hilo}/escribir_mensaje/aceptar")
	public String aceptarMensaje(Model model, @PathVariable String hilo, @RequestParam String mensajeEscrito) {

		Hilo hiloActual = getHiloActual(hilo);
		if (usuario == null) // Ñapa incoming. Programming the Spanish way
			hiloActual.addMensaje(
					new Mensaje(u1 /* lo pongo como ejemplo, mejorara al añadir la sesion */, mensajeEscrito));
		else
			hiloActual.addMensaje(new Mensaje(usuario, mensajeEscrito));
		model.addAttribute("titulo", hilo);
		return "mensaje_escrito";
	}

	@GetMapping("foro/{hilo}/{index}")
	public String eliminarMensaje(Model model, @PathVariable String hilo, @PathVariable int index) {
		Hilo actual = getHiloActual(hilo);
		actual.getMensajes().remove(index - 1);

		model.addAttribute("titulo", hilo);
		return "mensaje_eliminado";
	}

	@GetMapping("/partidas_publicas")
	public String partidas(Model model) {

		return "partidas_publicas";
	}

	@GetMapping("/crear_usuario")
	public String crear_usuario(Model model) {

		return "crear_usuario";
	}

	@PostMapping("/crear_usuario/aceptar")
	public String aceptarUsuario(Model model, @RequestParam String user, @RequestParam String mail,
			@RequestParam String password) {

		usuario = new Usuario(user, mail, password);
		if (!usuarios.contains(usuario)) {
			usuarios.add(usuario);
		}
		return "aceptar_usuario";
	}

	@GetMapping("/inicia_sesion")
	public String inicia_sesion(Model model) {

		return "inicia_sesion";
	}

	@GetMapping("/crear_partida")
	public String crear_partida(Model model) {

		return "crear_partida";
	}

	@GetMapping("/partidas_privadas")
	public String partidas_privadas(Model model) {

		return "partidas_privadas";
	}

	@GetMapping("/crear_ficha")
	public String fichas(Model model) {

		return "crear_ficha";
	}

	public Hilo getHiloActual(String titulo) {
		Hilo hiloActual = null;
		int index = 0;
		while (hiloActual == null) {
			if (hilos.get(index).getTitulo().equals(titulo)) {
				hiloActual = hilos.get(index);
			}
			index++;
		}
		return hiloActual;
	}

}

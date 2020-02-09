package es.urjc.code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

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


	@Autowired
	Usuario usuario;

	List<Partida> partidasPublicas = new ArrayList<Partida>(); // ESTARIA BIEN GUARDAR LAS PARTIDAS PUBLICAS EN UN REPO
																// APARTE

	Partida p1 = new Partida("p1", false, u1, m1);
	Partida p2 = new Partida("p2", false, u2, m2);

	// Hasta aqui zona de datos a cholon

	@PostConstruct
	public void init() {
		hilos.add(h1);
		hilos.add(h2);
		partidasPublicas.add(p1);
		partidasPublicas.add(p2);
	}

	@GetMapping("/foro")
	public String foro(Model model) {

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
		if (usuario.getNombre() == null) { // PREGUNTAR AL PROFE QUE HACER CUANDO NO SE HA IDENTIFICADO NADIE
			m = new Mensaje(usuario, mensajeEscrito);
			h = new Hilo(titulo, usuario, m); // Esto es para probar y eso
		} else {
			m = new Mensaje(usuario, mensajeEscrito);
			h = new Hilo(titulo, usuario, m);
		}
		// hilos.add(h); //no permitir hilos repetidos, para luego
		return "hilo_creado";
	}

	@GetMapping("foro/{hilo}")
	public String hilo(Model model, @PathVariable String hilo) {

		// Espero que esto no haya que cambiarlo mucho con la database pero quien sabe

		Hilo hiloActual = getHiloActual(hilo);

		model.addAttribute("titulo", hiloActual.getTitulo());
		model.addAttribute("mensajes", hiloActual.getMensajes());
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

					new Mensaje(usuario /* lo pongo como ejemplo, mejorara al añadir la sesion */, mensajeEscrito));

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

		model.addAttribute("partidasPublicas", partidasPublicas);

		return "partidas_publicas";
	}

	@GetMapping("partidas_publicas/{partida}")
	public String partida(Model model, @PathVariable String partida) {

		Partida partidaActual = getPartidaActual(partida);

		model.addAttribute("titulo", partidaActual.getTitulo());
		model.addAttribute("mensajes", partidaActual.getMensajes());
		return "partida";
	}

	@GetMapping("partidas_publicas/{titulo}/escribir_mensaje_partida")
	public String escribirMensajePartida(Model model, @PathVariable String titulo) {

		model.addAttribute("titulo", titulo);
		return "escribir_mensaje_partida";
	}

	@PostMapping("partidas_publicas/{titulo}/escribir_mensaje_partida/aceptar")
	public String aceptarMensajePartida(Model model, @PathVariable String titulo, @RequestParam String mensajeEscrito) {

		Partida partidaActual = getPartidaActual(titulo);
		if (usuario == null) // Ñapa incoming. Programming the Spanish way
			partidaActual.addMensaje(
					new Mensaje(usuario /* lo pongo como ejemplo, mejorara al añadir la sesion */, mensajeEscrito));
		else
			partidaActual.addMensaje(new Mensaje(usuario, mensajeEscrito));
		model.addAttribute("titulo", titulo);
		return "mensaje_escrito_partida";
	}

	@GetMapping("partidas_publicas/{titulo}/{index}")
	public String eliminarMensajePartida(Model model, @PathVariable String titulo, @PathVariable int index) {
		Partida actual = getPartidaActual(titulo);
		actual.getMensajes().remove(index - 1);

		model.addAttribute("titulo", titulo);
		return "mensaje_eliminado_partida";
	}

	@GetMapping("/crear_usuario")
	public String crear_usuario(Model model) {

		return "crear_usuario";
	}

	@PostMapping("/crear_usuario/aceptar")
	public String aceptarUsuario(Model model, @RequestParam String user, @RequestParam String mail,
			@RequestParam String password) {

		usuario = new Usuario(user, mail, password);
		/*
		 * if (!usuarios.contains(usuario)) { usuarios.add(usuario); }
		 */
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

	@PostMapping("/crear_partida/aceptar")
	public String aceptarNuevaPartida(Model model, @RequestParam String nombre,
			@RequestParam(required = false) String privada, @RequestParam String invitados,
			@RequestParam String descripcion) {
		Mensaje men = new Mensaje(usuario, descripcion);
		Partida partida;
		if (privada != null) {
			model.addAttribute("tipo", "privada");
			partida = new Partida(nombre, true, usuario, men);
		} else {
			model.addAttribute("tipo", "publica");
			partida = new Partida(nombre, false, usuario, men);
			partidasPublicas.add(partida);
		}
		usuario.addPartidaJugador(partida);
		String usuariosInvitados[] = invitados.split(", ");
		for (String name : usuariosInvitados) { // Se comprueba si los usuarios introducidos son validos y se añaden si
												// es el caso
			for (Usuario user : usuarios) {
				if (name.equals(user.getNombre())) {
					partida.addJugador(user);
					user.addPartidaJugador(partida);
				}
			}
			// LAS COMPROBACIONES SE HACEN CON LA BD YA EN FUNCIONAMIENTO
		}
		return "aceptar_nueva_partida";
	}

	@GetMapping("/partidas_privadas")
	public String partidas_privadas(Model model) {

		return "partidas_privadas";
	}

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
	public String aceptarFicha(Model model, @RequestParam String name, @RequestParam("Jugador") String Jugador,
			@RequestParam("Clase") String Clase, @RequestParam("Raza") String Raza) {

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

		FichaJugador f = new FichaJugador(usuario, name, type, Clase, Raza);

		return "aceptar_ficha";
	}

	@PostMapping("/ficha_enemigos/aceptar_ficha_enemigo")
	public String aceptarFicha(Model model, @RequestParam String name, @RequestParam String type,
			@RequestParam String alignment) {

//		Los siguientes parametros son para mandarlos por moustache y mandarlos por pantalla.
//		Seran el nombre del personaje, el tipo, clase y raza.

		model.addAttribute("name", name);
		model.addAttribute("type", type);
		model.addAttribute("alignment", alignment);

		// Los anteriores parametros y el ID del usuario seran los necesarios para crear
		// la ficha y guardar en la base de datos.
//		ID del usuario, nombre del personaje, tipo del personaje pasado a boolean, clase del personaje y raza del personaje.

		FichaMundo f = new FichaMundo(name, "Enemigo", "Alineamiento: "+alignment+" Tipo de enemigo: "+type);

		return "aceptar_ficha_enemigo";
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

	public Partida getPartidaActual(String partida) {
		Partida partidaActual = null;
		int index = 0;
		while (partidaActual == null) {
			if (partidasPublicas.get(index).getTitulo().equals(partida)) {
				partidaActual = partidasPublicas.get(index);
			}
			index++;
		}
		return partidaActual;
	}

}

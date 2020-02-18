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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//PARTIR
@Controller
public class WebController {

	@Autowired
	private BaseDatos baseDatos;
	@Autowired
	Usuario usuario;
	
	Usuario vacio = new Usuario("", "", "");
	// Estos son datos a cholon que se ponen la primera vez

	/*Usuario u1 = new Usuario("Bob", "bob@noimporta.com", "contraseña1");
	Mensaje m1 = new Mensaje(u1, "Hola como estan");
	Usuario u2 = new Usuario("Alice", "alice@noimporta.com", "contraseña2");
	Mensaje m2 = new Mensaje(u2, "Mensaje de ejemplo 2");

	Mensaje m3 = new Mensaje(u1, "Esto es una nueva partida publica");
	Mensaje m4 = new Mensaje(u2, "Esto es una partida privada");
	Hilo h1 = new Hilo("Ejemplo 1", u1, m1);
	Hilo h2 = new Hilo("Ejemplo 2", u2, m2);

	
	FichaJugador f1 = new FichaJugador(u1, "Dave el Bárbaro", true, "Guerrero", "Humano");
	FichaJugador f2 = new FichaJugador(u1, "Conan el Bárbaro", true, "Guerrero", "Humano");

	FichaMundo fm1 = new FichaMundo("Esqueleto", "Alineamiento: caótico malvado Tipo: No muerto", "Enemigo");
	FichaMundo fm2 = new FichaMundo("Kikimora", "Alineamiento: caótico neutral Tipo: Insectoide", "Enemigo");


	Partida p1 = new Partida("p1", false, u1, m3);
	Partida p2 = new Partida("p2", true, u2, m4);
//*/
	// Hasta aqui zona de datos a cholon

	@PostConstruct
	public void init() {
		vacio = baseDatos.saveUsuario(vacio);
		usuario = vacio;
		
		/*u1 = baseDatos.saveUsuario(u1);
		u2 = baseDatos.saveUsuario(u2);
		
		h1 = baseDatos.saveHilo(h1);
		h2 = baseDatos.saveHilo(h2);

		
		p1 = baseDatos.savePartida(p1);
		p2 = baseDatos.savePartida(p2);
		// Para guardar en la bd
		m1.setHilo(h1);
		m2.setHilo(h2);
		m3.setHilo(p1);
		m4.setHilo(p2);
		f1 = baseDatos.saveFichaJugador(f1);

		u1.addFicha(f1);
		f2 = baseDatos.saveFichaJugador(f2);

		fm1 = baseDatos.saveFichaMundo(fm1);
		fm2 = baseDatos.saveFichaMundo(fm2);

		u1.addFicha(f2);
		u1.addPartidaJugador(p1);
		u1.addPartidaJugador(p2);
		u2.addPartidaJugador(p1);
		u2.addPartidaJugador(p2);

		m1 = baseDatos.saveMensaje(m1);
		m2 = baseDatos.saveMensaje(m2);
		m3 = baseDatos.saveMensaje(m3);
		m4 = baseDatos.saveMensaje(m4);

//*/
	}

	@GetMapping("/foro")
	public String foro(Model model) {

		List<Hilo> hilos = new ArrayList<Hilo>();
		for(Hilo h : baseDatos.getAllHilos())
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
		m = new Mensaje(usuario, mensajeEscrito);
		h = new Hilo(titulo, usuario, m);
		m.setAutor(usuario);
		m.setHilo(h);
		baseDatos.saveHilo(h);
		baseDatos.saveMensaje(m);
//		}
		return "hilo_creado";
	}

	@GetMapping("foro/{hilo}")
	public String hilo(Model model, @PathVariable long hilo) {

		// Espero que esto no haya que cambiarlo mucho con la database pero quien sabe

		Hilo hiloActual = baseDatos.getHilo(hilo);

		model.addAttribute("hilo", hiloActual);

		model.addAttribute("mensajes", hiloActual.getMensajes());
		return "hilo_foro";
	}

	@GetMapping("foro/{hilo}/escribir_mensaje")
	public String escribirMensaje(Model model, @PathVariable long hilo) {
		Hilo hiloActual = baseDatos.getHilo(hilo);

		model.addAttribute("hilo", hiloActual);
		return "escribir_mensaje";
	}

	@PostMapping("foro/{hilo}/escribir_mensaje/aceptar")
	public String aceptarMensaje(Model model, @PathVariable long hilo, @RequestParam String mensajeEscrito) {

		Hilo hiloActual = baseDatos.getHilo(hilo);
//		Usuario userActual = baseDatos.findUsuario(usuario.getNombre());
//		if (userActual == null) { // Si el usuario actual no está en la BD es que no está registrado
//			model.addAttribute("hilo", hiloActual);
//			return "mensaje_error";
//
//		} else {
		Mensaje m = new Mensaje(usuario, mensajeEscrito);
		m.setHilo(hiloActual);
		hiloActual.addMensaje(m);

		baseDatos.saveMensaje(m);

		model.addAttribute("hilo", hiloActual);
		return "mensaje_escrito";
		// }
	}

	@GetMapping("foro/{hilo}/{index}")
	public String eliminarMensaje(Model model, @PathVariable long hilo, @PathVariable int index) {
		Hilo actual = baseDatos.getHilo(hilo);
		Mensaje m = actual.getMensajes().get(index - 1);
		actual.removeMensaje(index - 1);

		baseDatos.removeMensaje(m);

		model.addAttribute("hilo", actual.getId());
		return "mensaje_eliminado";
	}

	@GetMapping("/partidas_publicas")
	public String partidas(Model model) {

		model.addAttribute("partidasPublicas", baseDatos.getAllPartidas());

		return "partidas_publicas";
	}

	@GetMapping("partidas_publicas/{partida}")
	public String partida(Model model, @PathVariable long partida) {

		Partida partidaActual = baseDatos.getPartida(partida);

		model.addAttribute("titulo", partidaActual.getTitulo());
		model.addAttribute("partida", partida);
		model.addAttribute("mensajes", partidaActual.getMensajes());
		return "partida";
	}

	@GetMapping("partidas_publicas/{partida}/escribir_mensaje_partida")
	public String escribirMensajePartida(Model model, @PathVariable long partida) {

		model.addAttribute("titulo", partida);
		return "escribir_mensaje_partida";
	}

	@PostMapping("partidas_publicas/{partida}/escribir_mensaje_partida/aceptar")
	public String aceptarMensajePartida(Model model, @PathVariable long partida, @RequestParam String mensajeEscrito) {

		Partida partidaActual = baseDatos.getPartida(partida);
		String respuesta = "";
		respuesta = "Mensaje escrito para la partida " + partidaActual.getTitulo();
		Mensaje m = new Mensaje(usuario, "Espectador: " + mensajeEscrito);
		m.setHilo(partidaActual);

		partidaActual.addMensaje(m);
		baseDatos.saveMensaje(m);

		model.addAttribute("cadena", respuesta);
		model.addAttribute("titulo", partida);
		return "mensaje_escrito_partida";
	}

	@GetMapping("partidas_publicas/{partida}/{index}")
	public String eliminarMensajePartida(Model model, @PathVariable long partida, @PathVariable int index) {
		Partida actual = baseDatos.getPartida(partida);
		Mensaje m = actual.getMensajes().get(index - 1);
		actual.getMensajes().remove(index - 1);

		baseDatos.removeMensaje(m);

		model.addAttribute("titulo", partida);
		return "mensaje_eliminado_partida";
	}

	@GetMapping("/crear_usuario")
	public String crear_usuario(Model model) {

		return "crear_usuario";
	}

	@PostMapping("/crear_usuario/aceptar")
	public String aceptarUsuario(Model model, @RequestParam String user, @RequestParam String mail,
			@RequestParam String password) {

		String respuesta = "";
//		Usuario uActualNombre = baseDatos.findUsuario(user);
//		Usuario uActualMail = baseDatos.findUsuarioByCorreo(mail);
//		if (uActualNombre != null) {
//			respuesta = "Usuario no válido. Nombre repetido";
//		} else if (uActualMail != null) {
//			respuesta = "Usuario no válido. Correo electrónico repetido";
//		} else {
		respuesta = "Usuario aceptado";
		usuario = baseDatos.saveUsuario(new Usuario(user, mail, password));
		// }
		model.addAttribute("cadena", respuesta);
		return "aceptar_usuario";

	}

	@GetMapping("/inicia_sesion")
	public String inicia_sesion(Model model) {

		return "inicia_sesion";
	}

	@PostMapping("/inicia_sesion/aceptar")
	public String aceptarSesion(Model model, @RequestParam String user, @RequestParam String password) {
//		Usuario uActual = baseDatos.findUsuario(user);
//		String respuesta = "";
//		if (uActual == null) {
//			respuesta = "El nombre de usuario no existe";
//		} else if (!password.equals(uActual.getPassword())) {
//			respuesta = "Contraseña incorrecta";
//		} else {
//			respuesta = "Bienvenido, " + user;
//			usuario = uActual;
//		}
		model.addAttribute("cadena", "Bienvenido");
		return "aceptar_usuario";
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
		}
		men.setHilo(partida);

		usuario.addPartidaJugador(partida);
		String usuariosInvitados[] = invitados.split(", ");
		for (String name : usuariosInvitados) { // Se comprueba si los usuarios introducidos son validos y se añaden si
												// es el caso
			Usuario u = baseDatos.findUsuario(name);
			if (u != null) {
				u.addPartidaJugador(partida);
				partida.addJugador(u);
			}

		}
		baseDatos.savePartida(partida);
		baseDatos.saveMensaje(men);
		return "aceptar_nueva_partida";
	}

	@GetMapping("/partidas_privadas")
	public String partidas_privadas(Model model) {

		model.addAttribute("partidasPrivadas", baseDatos.getAllPartidas());

		return "partidas_privadas";
	}

	@GetMapping("partidas_privadas/{partidaPrivada}")
	public String partidaPrivada(Model model, @PathVariable long partidaPrivada) {

		Partida partidaActual = baseDatos.getPartida(partidaPrivada);

		model.addAttribute("titulo", partidaActual.getTitulo());
		model.addAttribute("partida", partidaPrivada);
		model.addAttribute("mensajes", partidaActual.getMensajes());
		return "partidaPrivada";
	}

	@GetMapping("partidas_privadas/{id}/escribir_mensaje_partida_privada")
	public String escribirMensajePartidaPrivada(Model model, @PathVariable long id) {

		model.addAttribute("fichas", usuario.getFichas());
		model.addAttribute("titulo", id);
		return "escribir_mensaje_partida_privada";
	}

	@PostMapping("partidas_privadas/{titulo}/escribir_mensaje_partida_privada/aceptar")
	public String aceptarMensajePartidaPrivada(Model model, @PathVariable long titulo,
			@RequestParam String mensajeEscrito, @RequestParam(required = false) String idFicha) {

		Ficha fichaActual = null;

		String inicio = "";
		if (idFicha != null) {
			fichaActual = baseDatos.findFichaJugador(Integer.parseInt(idFicha));
			inicio = fichaActual.getNombre() + " : ";
		}
		Partida partidaActual = baseDatos.getPartida(titulo);
//		Usuario userActual = baseDatos.findUsuario(usuario.getNombre());
		String respuesta = "";
//		if (userActual == null) { // Ñapa incoming. Programming the Spanish way
//			respuesta = "No se ha escrito el mensaje. Usuario inválido";
//		} else {
		respuesta = "Mensaje escrito para la partida " + partidaActual.getTitulo();
		Mensaje m = new Mensaje(usuario, inicio + mensajeEscrito);
		partidaActual.addMensaje(m);
		m.setHilo(partidaActual);

		baseDatos.saveMensaje(m);
		// }
		model.addAttribute("cadena", respuesta);
		model.addAttribute("titulo", titulo);
		return "mensaje_escrito_partida_privada";
	}

	@RequestMapping("partidas_privadas/{id}/add_ficha")
	public String addFichaMundo(Model model, @PathVariable long id) {

		model.addAttribute("fichas", baseDatos.findFichasLibres());
		model.addAttribute("id", id);
		return "add_ficha";
	}

	@PostMapping("partidas_privadas/{id}/add_ficha/aceptar")
	public String aceptarFichaMundo(Model model, @PathVariable long id,
			@RequestParam(required = false) List<String> idFicha) {
		if (idFicha != null) {
			Partida partidaActual = baseDatos.getPartida(id);
			for (String s : idFicha) {
				int idMundo = Integer.parseInt(s);
				FichaMundo fm = baseDatos.findFichaMundo(idMundo);
				baseDatos.saveFichaMundo(fm);
				partidaActual.addFicha(fm);
				fm.setPartida(partidaActual);
			}

			baseDatos.savePartida(partidaActual);
		}
		return "aceptar_add_ficha";
	}

	@RequestMapping("partidas_privadas/{id}/consultar_fichas")
	public String consultarFichas(Model model, @PathVariable long id) {
		Partida partidaActual = baseDatos.getPartida(id);

		model.addAttribute("fichas", partidaActual.getFichas());
		model.addAttribute("idPartida", id);
		return "consultar_fichas";
	}

	@RequestMapping("partidas_privadas/{id}/consultar_fichas/{id_ficha}")
	public String verFichaMundo(Model model, @PathVariable long id, @PathVariable long id_ficha) {

		FichaMundo fActual = baseDatos.findFichaMundo(id_ficha);
		model.addAttribute("ficha", fActual);

		model.addAttribute("id", id);
		return "consultar_ficha_mundo";
	}

	@GetMapping("partidas_privadas/{id}/{index}")
	public String eliminarMensajePartidaPrivada(Model model, @PathVariable long id, @PathVariable int index) {
		Partida actual = baseDatos.getPartida(id);
		Mensaje m = actual.getMensajes().get(index - 1);
		actual.getMensajes().remove(m);

		baseDatos.removeMensaje(m);

		model.addAttribute("titulo", id);
		return "mensaje_eliminado_partida_privada";
	}

//	

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

		FichaJugador f = new FichaJugador(usuario, name, type, Clase, Raza);
		f = baseDatos.saveFichaJugador(f);
		if (baseDatos.findUsuario(usuario.getNombre()) != null) {
			usuario.addFicha(f);
		}

		return "aceptar_ficha";
	}

	@PostMapping("/ficha_enemigos/aceptar_ficha_enemigo")
	public String aceptarFichaEnemigo(Model model, @RequestParam String name, @RequestParam(required = false)  String Tipo,
			@RequestParam(required = false)  String Alineamiento) {

		// Los anteriores parametros y el ID del usuario seran los necesarios para crear
		// la ficha y guardar en la base de datos.
//		ID del usuario, nombre del personaje, tipo del personaje pasado a boolean, clase del personaje y raza del personaje.

		FichaMundo f = new FichaMundo(name, "Enemigo", " Alineamiento: " + Alineamiento + " Tipo de enemigo: " + Tipo);
		f = baseDatos.saveFichaMundo(f);

		model.addAttribute("ficha", f);
		f = baseDatos.saveFichaMundo(f);

		return "aceptar_ficha_mundo";
	}

	@PostMapping("/ficha_objetos/aceptar_ficha_objeto")
	public String aceptarFichaObjeto(Model model, @RequestParam String name, @RequestParam(required = false)  String Tipo,
			@RequestParam(required = false)  String Descripcion) {

		FichaMundo f = new FichaMundo(name, "Objeto", " Tipo:" + Tipo + " Descripcion:" + Descripcion);
		f = baseDatos.saveFichaMundo(f);
		model.addAttribute("ficha", f);
		return "aceptar_ficha_objeto";
	}

	@PostMapping("/ficha_habilidades/aceptar_ficha_habilidades")
	public String aceptarFichaHabilidades(Model model, @RequestParam String name, @RequestParam(required = false)  String Tipo,
			@RequestParam(required = false)  String Descripcion) {

		FichaMundo f = new FichaMundo(name, "Habilidad", " Tipo:" + Tipo + " Descripcion:" + Descripcion);
		f = baseDatos.saveFichaMundo(f);
		model.addAttribute("ficha", f);
		return "aceptar_ficha_objeto";
	}

	@PostMapping("/ficha_loc/aceptar_ficha_loc")
	public String aceptarFichaLoc(Model model, @RequestParam String name, @RequestParam String Tipo,
			@RequestParam String Temperatura, @RequestParam String Descripcion) {

		FichaMundo f = new FichaMundo(name, "Localizacion",
				"Tipo: " + Tipo + " Temperatura: " + Temperatura + " Descripcion: " + Descripcion);
		f = baseDatos.saveFichaMundo(f);
		model.addAttribute("ficha", f);
		return "aceptar_ficha_objeto";
	}

}

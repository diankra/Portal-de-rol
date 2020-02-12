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

	@Autowired
	private BaseDatos baseDatos;
	// Estos son datos a cholon que se tienen que quitar, estan solo para el diseño

	Usuario u1 = new Usuario("Bob", "bob@noimporta.com", "contraseña1");
	Mensaje m1 = new Mensaje(u1, "Hola como estan");
	Usuario u2 = new Usuario("Alice", "alice@noimporta.com", "contraseña2");
	Mensaje m2 = new Mensaje(u2, "Mensaje de ejemplo 2");

	Mensaje m3 = new Mensaje(u1, "Esto es una nueva partida publica");
	Mensaje m4 = new Mensaje(u2, "Esto es una partida privada");
	Hilo h1 = new Hilo("Ejemplo 1", u1, m1);
	Hilo h2 = new Hilo("Ejemplo 2", u2, m2);

	@Autowired
	Usuario usuario;


	List<Partida> partidasPublicas = new ArrayList<Partida>(); 

	Partida p1 = new Partida("p1", false, u1, m3);
	Partida p2 = new Partida("p2", true, u2, m4);


	// Hasta aqui zona de datos a cholon

	@PostConstruct
	public void init() {
		u1 = baseDatos.saveUsuario(u1);
		u2 = baseDatos.saveUsuario(u2);
		h1 = baseDatos.saveHilo(h1);
		h2 = baseDatos.saveHilo(h2);
		p1.addJugador(u2);
		p2.addJugador(u1);
		p1 = baseDatos.savePartida(p1);
		p2 = baseDatos.savePartida(p2);
		// Para guardar en la bd
		m1.setHilo(h1);
		m2.setHilo(h2);
		m3.setHilo(p1);
		m4.setHilo(p2);
		u1.addPartidaJugador(p1);
		u1.addPartidaJugador(p2);
		u2.addPartidaJugador(p1);
		u2.addPartidaJugador(p2);
		m1 = baseDatos.saveMensaje(m1);
		m2 = baseDatos.saveMensaje(m2);
		m3 = baseDatos.saveMensaje(m3);
		m4 = baseDatos.saveMensaje(m4);

	}

	@GetMapping("/foro")
	public String foro(Model model) {

		model.addAttribute("hilos", baseDatos.getAllHilos());

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
		Usuario userActual = baseDatos.findUsuario(usuario.getNombre());
		if (userActual == null) { // Ñapa incoming. Programming the Spanish way
			return "hilo_no_creado";
		} else {
			m = new Mensaje(usuario, mensajeEscrito);
			h = new Hilo(titulo, usuario, m);
			m.setAutor(usuario);
			m.setHilo(h);
			baseDatos.saveHilo(h);
			baseDatos.saveMensaje(m);
		}
		// hilos.add(h); //no permitir hilos repetidos, para luego
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
		Usuario userActual = baseDatos.findUsuario(usuario.getNombre());
		if (userActual == null) { // Si el usuario actual no está en la BD es que no está registrado
			model.addAttribute("hilo", hiloActual);
			return "mensaje_error";

		} else {
			Mensaje m = new Mensaje(usuario, mensajeEscrito);
			m.setHilo(hiloActual);
			hiloActual.addMensaje(m);

			baseDatos.saveMensaje(m);

			model.addAttribute("hilo", hiloActual);
			return "mensaje_escrito";
		}
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

		model.addAttribute("partidasPublicas", baseDatos.getPartidasPublicas());

		return "partidas_publicas";
	}

	@GetMapping("partidas_publicas/{partida}")
	public String partida(Model model, @PathVariable long partida) {

		Partida partidaActual = baseDatos.getPartida(partida);

		model.addAttribute("titulo", partidaActual.getId());
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
		Usuario usuarioActual = baseDatos.findUsuario(usuario.getNombre());
		String respuesta = "";
		if (usuarioActual == null) {
			respuesta = "Usuario no identificado";
		}else {
			respuesta = "Mensaje escrito para la partida "+partidaActual.getTitulo();
			Mensaje m = new Mensaje(usuario, mensajeEscrito);
			m.setHilo(partidaActual);

			partidaActual.addMensaje(m);
			baseDatos.saveMensaje(m);
		}
		model.addAttribute("cadena", respuesta);
		model.addAttribute("titulo", partida);
		return "mensaje_escrito_partida";
	}

	@GetMapping("partidas_publicas/{partida}/{index}")
	public String eliminarMensajePartida(Model model, @PathVariable long partida, @PathVariable int index) {
		Partida actual = baseDatos.getPartida(partida);
		Mensaje m = actual.getMensajes().get(index-1);
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
		Usuario uActualNombre = baseDatos.findUsuario(user);
		Usuario uActualMail = baseDatos.findUsuarioByCorreo(mail);
		if (uActualNombre != null) {
			respuesta = "Usuario no válido. Nombre repetido";
		}else if(uActualMail != null) {
			respuesta = "Usuario no válido. Correo electrónico repetido";
		}else {
			respuesta = "Usuario aceptado";
			usuario = baseDatos.saveUsuario(new Usuario(user, mail, password));
		}
			model.addAttribute("cadena", respuesta);
			return "aceptar_usuario";
		
	}

	@GetMapping("/inicia_sesion")
	public String inicia_sesion(Model model) {

		return "inicia_sesion";
	}

	@PostMapping("/inicia_sesion/aceptar")
	public String aceptarSesion(Model model, @RequestParam String user, @RequestParam String password) {
		Usuario uActual = baseDatos.findUsuario(user);
		String respuesta = "";
		if(uActual == null) {
			respuesta = "El nombre de usuario no existe";
		}else if(!password.equals(uActual.getPassword()))
		{
			respuesta = "Contraseña incorrecta";
		}else {
			respuesta = "Bienvenido, "+user;
			usuario = uActual;
		}
		model.addAttribute("cadena", respuesta);
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
			partidasPublicas.add(partida);
		}
		men.setHilo(partida);
		
		usuario.addPartidaJugador(partida);
		String usuariosInvitados[] = invitados.split(", ");
		for (String name : usuariosInvitados) { // Se comprueba si los usuarios introducidos son validos y se añaden si
												// es el caso
			Usuario u = baseDatos.findUsuario(name);
			if(u != null) {
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

		model.addAttribute("partidasPrivadas", usuario.getPartidasJugador());

		return "partidas_privadas";
	}


	@GetMapping("partidas_privadas/{partidaPrivada}")
	public String partidaPrivada(Model model, @PathVariable long partidaPrivada) {

		Partida partidaActual = baseDatos.getPartida(partidaPrivada);

		model.addAttribute("titulo", partidaActual.getId());
		model.addAttribute("mensajes", partidaActual.getMensajes());
		return "partidaPrivada";
	}
		

	@GetMapping("partidas_privadas/{id}/escribir_mensaje_partida_privada")
	public String escribirMensajePartidaPrivada(Model model, @PathVariable long id) {

		model.addAttribute("titulo", id);
		return "escribir_mensaje_partida_privada";
	}

	@PostMapping("partidas_privadas/{titulo}/escribir_mensaje_partida_privada/aceptar")
	public String aceptarMensajePartidaPrivada(Model model, @PathVariable long titulo, @RequestParam String mensajeEscrito) {

		Partida partidaActual = baseDatos.getPartida(titulo);
		Usuario userActual = baseDatos.findUsuario(usuario.getNombre());
		String respuesta = "";
		if (userActual == null) { // Ñapa incoming. Programming the Spanish way
			respuesta = "No se ha escrito el mensaje. Usuario inválido";
		}else {
			respuesta = "Mensaje escrito para la partida "+partidaActual.getTitulo();
			Mensaje m = new Mensaje(usuario, mensajeEscrito);
			partidaActual.addMensaje(m);
			m.setHilo(partidaActual);

			baseDatos.saveMensaje(m);
		}
		model.addAttribute("cadena", respuesta);
		model.addAttribute("titulo", titulo);
		return "mensaje_escrito_partida_privada";
	}

	@GetMapping("partidas_privadas/{id}/{index}")
	public String eliminarMensajePartidaPrivada(Model model, @PathVariable long id, @PathVariable int index) {
		Partida actual = baseDatos.getPartida(id);
		Mensaje m = actual.getMensajes().get(index-1);
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

		FichaMundo f = new FichaMundo(name, "Enemigo", "Alineamiento: " + alignment + " Tipo de enemigo: " + type);

		return "aceptar_ficha_enemigo";
	}


	
	public Partida getPartidaPrivadaActual(String partida) {
		Partida partidaActual = null;
		int index = 0;
		while (partidaActual == null) {
			if (usuario.getPartidasJugador().get(index).getTitulo().equals(partida)) {
				partidaActual = usuario.getPartidasJugador().get(index);
			}
			index++;
		}
		return partidaActual;
	}	

}

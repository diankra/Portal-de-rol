package es.urjc.code;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	
	@Autowired
	private UsuarioRepository usuariosBD;
	@Autowired
	static Usuario usuario;
	//Para el usuario por defecto
	
	public static Usuario getUsuario() {
		/*Chapucilla para acceder al usuario correcto desde todos los controller,
		Invoca este metodo "LoginController.getUsuario()" para utilizar el user en otra clase
		 */
		return usuario;
	}

	@PostConstruct
	public void init() {
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
		usuario = usuariosBD.save(new Usuario(user, mail, password));
		// }
		model.addAttribute("cadena", respuesta);
		return "aceptar_usuario";

	}

	@GetMapping("/inicia_sesion")
	public String inicia_sesion(Model model) {

		return "inicia_sesion";
	}

	/*@PostMapping("/inicia_sesion/aceptar")
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
	}*/
	
	@GetMapping("/")
    public String index() {
        return "index";
    }

}

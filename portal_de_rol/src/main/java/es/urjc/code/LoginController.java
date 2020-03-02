package es.urjc.code;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
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
	private UserComponent userComponent;
	
	@PostConstruct
	public void init() {
	}
	
	@GetMapping("/crear_usuario")
	public String crear_usuario(Model model) {

		return "crear_usuario";
	}

	@GetMapping("/crear_usuario/aceptar")
	public String aceptarUsuario(Model model) {

		String respuesta = "";
		respuesta = "Usuario aceptado: "+ userComponent.getLoggedUser().getNombre();
		model.addAttribute("cadena", respuesta);
		return "aceptar_usuario";

	}

	@GetMapping("/inicia_sesion/aceptar")
	public String aceptarSesion(Model model) {
		
		String respuesta = "Usuario aceptado: "+ userComponent.getLoggedUser().getNombre();
		model.addAttribute("cadena", respuesta);
		return "aceptar_usuario";

	}
	@GetMapping("/fallo_inicia_sesion")
	public String fallarSesion(Model model) {

		String respuesta = "Credenciales erróneas";
		model.addAttribute("cadena", respuesta);
		return "aceptar_usuario";

	}
	@GetMapping("/inicia_sesion")
	public String inicia_sesion(Model model) {

		return "inicia_sesion";
	}
	
	@GetMapping("/cierra_sesion")
	public String cierra_sesion(Model model) {
		
		return "cierra_sesion";
	}
	
	@GetMapping("/cierra_sesion/aceptar")
	public String cierra_sesion_aceptar(Model model) {
		String respuesta = "Usuario desconectado";
		model.addAttribute("cadena", respuesta);
		return "aceptar_usuario";
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

package es.urjc.code;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {
	
	@GetMapping("/foro")
	public String foro(Model model) {
		
	return "foro_general";
	}
	
	@GetMapping("/partidas_publicas")
	public String partidas(Model model) {
		
	return "partidas_publicas";
	}
	
	@GetMapping("/crear_usuario")
	public String crear_usuario(Model model) {
		
	return "crear_usuario";
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
	
	
}



package es.urjc.code;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {
	
	@GetMapping("/foro")
	public String greeting(Model model) {
		
	return "foro_general";
	}
	
	@GetMapping("/partidas_publicas")
	public String saludo_partidas(Model model) {
		
	return "partidas_publicas";
	}

}


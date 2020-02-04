package es.urjc.code;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {
	
	@GetMapping("/hola")
	public String greeting(Model model, @RequestParam String nombre, @RequestParam String asunto,
			@RequestParam String comentario) {
		
	return "main_class";
	}

}


package es.urjc.code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {
	
	//Estos son datos a cholon que se tienen que quitar, estan solo para el diseño
	
	Usuario u1 = new Usuario("Bob");
	Mensaje m1 = new Mensaje(u1, "Hola como estan");
	Mensaje m2 = new Mensaje(u1, "Esto es un mensaje de ejemplo");
	Usuario u2 = new Usuario("Alice");
	Mensaje m3 = new Mensaje(u2, "Mensaje de ejemplo 3");
	Mensaje m4 = new Mensaje(u2, "Mensaje de ejemplo 4");
	List<Hilo> hilos = new ArrayList<Hilo>();
	
	//Hasta aqui zona de datos a cholon
	
	@GetMapping("/foro")
	public String foro(Model model) {
		
		//Zona de datos a cholon numero 2
		List<Mensaje> mens = new ArrayList<Mensaje>();
		mens.add(m1);
		mens.add(m2);
		Hilo h1 = new Hilo("Ejemplo 1", u1, mens);
		List<Mensaje> mens2 = new ArrayList<Mensaje>();
		mens2.add(m3);
		mens2.add(m4);
		Hilo h2 = new Hilo("Ejemplo 2", u2, mens2);
		hilos.add(h1);
		hilos.add(h2);
		List<String> titulos = new ArrayList<String>();
		for(Hilo h:hilos)
		{
			titulos.add(h.getTitulo());
		}
		
		model.addAttribute("hilos", titulos);
		//Fin de zona de datos a cholon numero 2
		
		
	return "foro_general";
	}
	
	@GetMapping("foro/{hilo}")
	public String hilo(Model model, @PathVariable String hilo) {
		
		//Espero que esto no haya que cambiarlo mucho con la database pero quien sabe
		
		Hilo hiloActual = null;
		int index = 0;
		while(hiloActual == null)
		{
			if(hilos.get(index).getTitulo().equals(hilo))
			{
				hiloActual = hilos.get(index);
			}
			index++;
		}
		model.addAttribute("titulo", hiloActual.getTitulo());
		List<String> mensajes = new ArrayList<String>();
		for(Mensaje m:hiloActual.getMensajes())
		{

			mensajes.add(m.getAutor().getNombre().toUpperCase()+":  "+m.getTexto()); //Un poco ñapa
		}
		model.addAttribute("mensajes", mensajes);
		return "hilo_foro";
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



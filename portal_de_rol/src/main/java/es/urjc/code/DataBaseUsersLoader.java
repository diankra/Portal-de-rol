package es.urjc.code;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataBaseUsersLoader {
	@Autowired
	private UsuarioRepository userRepository;

	@PostConstruct
	private void initDatabase() {

		userRepository.save(new Usuario("user", "pass", "...", "USER"));

		List<String> listaRoles = new ArrayList<String>();
		listaRoles.add("USER");
		listaRoles.add("ADMIN");
		userRepository.save(new Usuario("admin", "adminpass", "...", listaRoles));
	}
}

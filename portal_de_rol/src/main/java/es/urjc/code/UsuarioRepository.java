package es.urjc.code;

import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Long>{

	Usuario findUsuarioByNombre(String nombre);
	
	Usuario findUsuarioByCorreo(String mail);
}

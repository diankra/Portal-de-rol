package es.urjc.code;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	Usuario findUsuarioById(long id);
}

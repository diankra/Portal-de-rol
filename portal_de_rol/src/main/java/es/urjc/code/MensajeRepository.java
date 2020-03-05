package es.urjc.code;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
	List<Mensaje> findMensajesByAutor(Usuario user);
	}

package es.urjc.code;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FichaMundoRepository extends JpaRepository<FichaMundo, Long>{

	List<FichaMundo> findByPartida(Partida partida);
	FichaMundo findFichaMundoById(long id);
}

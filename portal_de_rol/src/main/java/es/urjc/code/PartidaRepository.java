package es.urjc.code;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PartidaRepository extends JpaRepository<Partida, Long>{

	List<Partida> findPartidasByPrivada(boolean privada);
	Partida findPartidaById(long id);
}

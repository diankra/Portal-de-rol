package es.urjc.code;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FichaJugadorRepository extends JpaRepository<FichaJugador, Long>{

	FichaJugador findFichaById(long id);
}

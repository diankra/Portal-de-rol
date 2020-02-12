package es.urjc.code;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface HiloRepository extends JpaRepository<Hilo, Long>{

	List<Hilo> findAll();
	
	Hilo findHiloById(long id);
}

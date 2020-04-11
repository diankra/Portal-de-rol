package es.urjc.code;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

@CacheConfig(cacheNames="portalderol")
public interface FichaMundoRepository extends JpaRepository<FichaMundo, Long>{

	@SuppressWarnings("unchecked")
	@CacheEvict(allEntries=true)
	FichaMundo save (FichaMundo entity);
	
	@Cacheable
	List<FichaMundo> findByPartida(Partida partida);
	@Cacheable
	FichaMundo findFichaMundoById(long id);
}

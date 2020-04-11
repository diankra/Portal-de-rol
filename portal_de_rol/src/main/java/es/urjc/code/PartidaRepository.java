package es.urjc.code;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

@CacheConfig(cacheNames="portalderol")
public interface PartidaRepository extends JpaRepository<Partida, Long>{

	@SuppressWarnings("unchecked")
	@CacheEvict(allEntries=true)
	Partida save (Partida entity);
	
	List<Partida> findPartidasByPrivada(boolean privada);
	@Cacheable
	Partida findPartidaById(long id);
	List<Partida> findPartidasByMaster(Usuario user);
}

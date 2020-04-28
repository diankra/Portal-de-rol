package es.urjc.code;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

@CacheConfig(cacheNames="portalderol")
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
	
	@SuppressWarnings("unchecked")
	@CacheEvict(allEntries=true)
	Mensaje save (Mensaje entity);
	@Cacheable
	List<Mensaje> findMensajesByAutor(Usuario user);
	}

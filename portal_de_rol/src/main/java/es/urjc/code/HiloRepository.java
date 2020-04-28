package es.urjc.code;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

@CacheConfig(cacheNames="portalderol")
public interface HiloRepository extends JpaRepository<Hilo, Long>{

	@SuppressWarnings("unchecked")
	@CacheEvict(allEntries=true)
	Hilo save (Hilo entity);
	
	List<Hilo> findAll();
	@Cacheable
	Hilo findHiloById(long id);
}

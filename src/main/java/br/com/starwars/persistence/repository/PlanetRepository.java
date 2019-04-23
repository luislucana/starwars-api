package br.com.starwars.persistence.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.starwars.persistence.model.Planet;

/**
 * Repository da entidade Planet.
 * 
 * @author Luis Lucana (luislucana@gmail.com)
 *
 */
@Repository
public interface PlanetRepository extends PagingAndSortingRepository<Planet, Integer> {
	
	public Optional<Planet> findByName(String name);
}

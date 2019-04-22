package br.com.starwars.persistence.repository;

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
	
	public Planet findByName(String name);
}

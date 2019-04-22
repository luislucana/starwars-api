package br.com.starwars.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import br.com.starwars.persistence.model.Planet;
import br.com.starwars.persistence.repository.PlanetRepository;

/**
 * Classe de servico para a entidade Planet.
 * 
 * @author Luis Lucana (luislucana@gmail.com)
 *
 */
@Service
public class PlanetService extends AbstractService<Planet> {
	
	@Autowired
	private PlanetRepository planetRepository;
	
	public PlanetService() {
		super();
	}
	
	@Override
    protected PagingAndSortingRepository<Planet, Long> getDao() {
        return planetRepository;
    }
	
	@Transactional
	public Planet createPlanet(Planet planet) {
		return create(planet);
	}
	
	public Page<Planet> getAllPlanetsFromDatabase(Pageable pageable) {
		return getDao().findAll(pageable);
	}
	
	public Page<Planet> getAllPlanetsFromRemoteAPI(Pageable pageable) {
		return null;
	}
	
	public Planet getPlanet(String name) {
		return planetRepository.findByName(name);
	}
	
	public Planet getPlanet(Integer id) {
		return findOne(id);
	}
	
	public void deletePlanet(Integer id) {
		deleteById(id);
	}
}

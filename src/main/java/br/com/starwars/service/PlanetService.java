package br.com.starwars.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import br.com.starwars.persistence.model.Planet;
import br.com.starwars.persistence.repository.PlanetRepository;
import br.com.starwars.service.dto.PlanetList;
import br.com.starwars.service.dto.Result;
import br.com.starwars.service.util.SwapiRestUtil;

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
    protected PagingAndSortingRepository<Planet, Integer> getDao() {
        return planetRepository;
    }
	
	@Transactional
	public Planet create(Planet planet) {
		
		if (planet == null) {
			throw new RuntimeException("Objeto planet nao pode ser nulo");
		}
		
		// verificar se existe na API do swapi
		try {
			PlanetList planets = SwapiRestUtil.getPlanetsFromSwapiAPI();
			
			if (planets == null) {
				throw new RuntimeException("Swapi API indisponivel no momento. Tente novamente mais tarde.");
			}
			
			List<Result> planetList = planets.getResults();
			
			boolean validName = false;
			String informedName = planet.getName();
			
			for (Result planetFromAPI : planetList) {
				String name = planetFromAPI.getName();
				
				if (name.equals(informedName)) {
					validName = true;
					break;
				}
			}
			
			if (!validName) {
				throw new RuntimeException("");
			}
		} catch (IOException e) {
			throw new RuntimeException("Swapi API indisponivel no momento. Tente novamente mais tarde.");
		}
		
		return create(planet);
	}
	
	public Page<Planet> getAllPlanetsFromDatabase(Pageable pageable) {
		return getDao().findAll(pageable);
	}
	
	public Page<Planet> getAllPlanetsFromRemoteAPI(Pageable pageable) {
		return null;
	}
	
	public Planet findByName(String name) {
		return planetRepository.findByName(name);
	}
	
	public Planet getPlanet(Integer id) {
		return findOne(id);
	}
	
	public void deletePlanet(Integer id) {
		deleteById(id);
	}
}

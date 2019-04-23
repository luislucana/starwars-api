package br.com.starwars.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import br.com.starwars.persistence.model.Planet;
import br.com.starwars.persistence.repository.PlanetRepository;
import br.com.starwars.service.dto.Result;
import br.com.starwars.service.util.SwapiRestUtil;
import br.com.starwars.web.exception.InvalidPlanetNameException;
import br.com.starwars.web.exception.ResourceNotFoundException;

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
		Planet createdPlanet = null;
		Result swapiApiPlanet = null;
		
		if (planet == null) {
			throw new RuntimeException("Objeto planet nao pode ser nulo");
		}
		
		// verificar se existe na API do swapi
		try {
			List<Result> planetsFromSwapiAPI = SwapiRestUtil.getPlanetsFromSwapiAPI();
			
			if (planetsFromSwapiAPI == null || planetsFromSwapiAPI.isEmpty()) {
				throw new RuntimeException("Swapi API indisponivel no momento. Tente novamente mais tarde.");
			}
			
			boolean validName = false;
			String informedName = planet.getName();
			
			for (Result planetFromAPI : planetsFromSwapiAPI) {
				String name = planetFromAPI.getName();
				
				if (name != null && name.equals(informedName)) {
					validName = true;
					swapiApiPlanet = planetFromAPI;
					break;
				}
			}
			
			if (!validName) {
				throw new InvalidPlanetNameException("Nome de planeta invalido.");
			}
			
			planet.setTerrain(swapiApiPlanet.getTerrain());
			planet.setClimate(swapiApiPlanet.getClimate());
			
			Integer filmsQuantity = Integer.valueOf(0);
			List<String> films = swapiApiPlanet.getFilms();
			
			if (films != null && !films.isEmpty()) {
				filmsQuantity = films.size();
			}
			
			planet.setFilmsQuantity(filmsQuantity);
			
			createdPlanet = super.create(planet);
			
		} catch (IOException e) {
			throw new RuntimeException("Swapi API indisponivel no momento. Tente novamente mais tarde.");
		}
		
		return createdPlanet;
	}
	
	public Page<Planet> getAllPlanetsFromDatabase(Pageable pageable) {
		return getDao().findAll(pageable);
	}
	
	public PageImpl<Result> getAllPlanetsFromRemoteAPI(Pageable pageable) {
		
		PageImpl<Result> results = null;
		
		try {
			results = SwapiRestUtil.getPlanetsFromSwapiAPI(pageable);
		} catch (IOException e) {
			throw new RuntimeException("Swapi API indisponivel no momento. Tente novamente mais tarde.");
		}
		
		return results;
	}
	
	public Planet findByName(String name) {
		return planetRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Planet nao encontrado."));
	}
	
	public Planet getPlanet(Integer id) {
		return findOne(id);
	}
	
	public void deletePlanet(Integer id) {
		boolean exists = planetRepository.existsById(id);
		
		if (!exists) {
			throw new ResourceNotFoundException("Planet com ID=" + String.valueOf(id) + " nao encontrado.");
		}
		
		deleteById(id);
	}
}

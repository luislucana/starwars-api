package br.com.starwars.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.starwars.persistence.model.Planet;
import br.com.starwars.service.PlanetService;
import br.com.starwars.web.event.PaginatedResultsRetrievedEvent;
import br.com.starwars.web.event.ResourceCreatedEvent;
import br.com.starwars.web.event.SingleResourceRetrievedEvent;
import br.com.starwars.web.util.RestPreconditions;

import com.google.common.base.Preconditions;

/**
 * Classe controller para operacoes da entidade Planet.
 * 
 * @author Luis Lucana (luislucana@gmail.com)
 *
 */
@RestController
@RequestMapping(value = "/planets")
public class PlanetController {
	
	@Autowired
    private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	private PlanetService planetService;
	
	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public Planet create(@RequestBody final Planet planet, final HttpServletResponse response) {
		Preconditions.checkNotNull(planet);
		
		final Planet newPlanet = planetService.createPlanet(planet);
        final Integer newPlanetId = newPlanet.getId();
        
        eventPublisher.publishEvent(new ResourceCreatedEvent(this, response, newPlanetId));
		
		return newPlanet;
	}
	
	@GetMapping(value = "/listFromDatabase", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<Planet> listFromDatabase(Pageable pageable, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
		
        final Page<Planet> resultPage = planetService.findPaginated(pageable.getPageNumber(), pageable.getPageSize());
        
        if (pageable.getPageNumber() > resultPage.getTotalPages()) {
        	throw new RuntimeException("MyResourceNotFoundException");
        }
        
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<Planet>(Planet.class, uriBuilder, response,
            pageable.getPageNumber(), resultPage.getTotalPages(), pageable.getPageSize()));

        return resultPage.getContent();
	}
	
	@GetMapping(value = "/listFromWeb", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<Planet> listFromWeb(Pageable pageable, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
		
		final Page<Planet> resultPage = planetService.getAllPlanetsFromRemoteAPI(pageable);
        
        if (pageable.getPageNumber() > resultPage.getTotalPages()) {
        	throw new RuntimeException("MyResourceNotFoundException");
        }
        
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<Planet>(Planet.class, uriBuilder, response,
            pageable.getPageNumber(), resultPage.getTotalPages(), pageable.getPageSize()));

        return resultPage.getContent();
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Planet getById(@PathVariable final String id, final HttpServletResponse response) {
		
		final Planet planet = RestPreconditions.checkFound(planetService.findOne(Long.valueOf(id)));

        eventPublisher.publishEvent(new SingleResourceRetrievedEvent(this, response));
        
        return planet;
	}
	
	@GetMapping(value = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Planet getByName(@PathVariable final String name, final HttpServletResponse response) {
		
		final Planet planet = RestPreconditions.checkFound(planetService.findOne(Long.valueOf(name)));

        eventPublisher.publishEvent(new SingleResourceRetrievedEvent(this, response));
        
        return planet;
	}
	
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable final String id) {
		planetService.deleteById(Long.valueOf(id));
	}
}
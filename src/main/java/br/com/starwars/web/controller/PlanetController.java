package br.com.starwars.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.base.Preconditions;

import br.com.starwars.persistence.model.Planet;
import br.com.starwars.service.PlanetService;
import br.com.starwars.service.dto.Result;
import br.com.starwars.web.event.PaginatedResultsRetrievedEvent;
import br.com.starwars.web.event.ResourceCreatedEvent;
import br.com.starwars.web.event.SingleResourceRetrievedEvent;
import br.com.starwars.web.exception.ResourceNotFoundException;
import br.com.starwars.web.util.RestPreconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Classe controller para operacoes da entidade Planet.
 * 
 * @author Luis Lucana (luislucana@gmail.com)
 *
 */
@RestController
@RequestMapping(value = "/planets")
@Api(tags = {"Planets API"})
public class PlanetController {
	
	@Autowired
    private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	private PlanetService planetService;
	
	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value="Create planet", notes="Creates a new planet.")
	@ApiResponses(value ={@ApiResponse(code = 201, message = "Planet created"/*, response = Response.class*/),
						  @ApiResponse(code = 400, message = "Bad Request"),
			  			  @ApiResponse(code = 403, message = "Forbidden"),
			  			  @ApiResponse(code = 404, message = "Not Found"),
			  			  @ApiResponse(code = 500, message = "Error"/*, response = Exception.class*/)})
	public Planet create(@RequestBody final Planet planet, final HttpServletResponse response) {
		Preconditions.checkNotNull(planet);
		
		final Planet newPlanet = planetService.create(planet);
        final Integer newPlanetId = newPlanet.getId();
        
        eventPublisher.publishEvent(new ResourceCreatedEvent(this, response, newPlanetId));
		
		return newPlanet;
	}
	
	@GetMapping(value = "/listFromDatabase", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value="Get planets", notes = "Gets a list of planets from database.")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK"),
							@ApiResponse(code = 400, message = "Bad Request"),
							@ApiResponse(code = 403, message = "Forbidden"),
							@ApiResponse(code = 500, message = "Error"/*, response = Exception.class*/)})
	public Page<Planet> listFromDatabase(Pageable pageable, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
		
        final Page<Planet> resultPage = planetService.findPaginated(pageable.getPageNumber(), pageable.getPageSize());
        
        if (pageable.getPageNumber() > resultPage.getTotalPages()) {
        	throw new RuntimeException("MyResourceNotFoundException");
        }
        
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<Planet>(Planet.class, uriBuilder, response,
            pageable.getPageNumber(), resultPage.getTotalPages(), pageable.getPageSize()));

        return resultPage;
	}
	
	@GetMapping(value = "/listFromWeb", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value="Get planets", notes = "Gets a list of planets from de Swapi API.")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK"),
							@ApiResponse(code = 400, message = "Bad Request"),
							@ApiResponse(code = 403, message = "Forbidden"),
							@ApiResponse(code = 500, message = "Error"/*, response = Exception.class*/)})
	public Page<Result> listFromWeb(Pageable pageable, final UriComponentsBuilder uriBuilder, final HttpServletResponse response) {
		
		PageImpl<Result> allPlanetsFromRemoteAPI = planetService.getAllPlanetsFromRemoteAPI(pageable);
        
        if (pageable.getPageNumber() > allPlanetsFromRemoteAPI.getTotalPages()) {
        	throw new ResourceNotFoundException("Recurso nao encontrado.");
        }
        
        eventPublisher.publishEvent(new PaginatedResultsRetrievedEvent<Planet>(Planet.class, uriBuilder, response,
            pageable.getPageNumber(), allPlanetsFromRemoteAPI.getTotalPages(), pageable.getPageSize()));

		return allPlanetsFromRemoteAPI;
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value="Get planet by ID", notes = "Get a planet from an informed ID.")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK"),
							@ApiResponse(code = 400, message = "Bad Request"),
							@ApiResponse(code = 403, message = "Forbidden"),
							@ApiResponse(code = 500, message = "Error"/*, response = Exception.class*/)})
	public Planet getById(@PathVariable final Integer id, final HttpServletResponse response) {
		
		final Planet planet = RestPreconditions.checkFound(planetService.findOne(id));

        eventPublisher.publishEvent(new SingleResourceRetrievedEvent(this, response));
        
        return planet;
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value="Get planet by Name", notes = "Get a planet from an informed Name.")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "OK"),
							@ApiResponse(code = 400, message = "Bad Request"),
							@ApiResponse(code = 403, message = "Forbidden"),
							@ApiResponse(code = 500, message = "Error"/*, response = Exception.class*/)})
	public Planet getByName(@RequestParam(required = true) final String name, final HttpServletResponse response) {
		
		final Planet planet = RestPreconditions.checkFound(planetService.findByName(name));

        eventPublisher.publishEvent(new SingleResourceRetrievedEvent(this, response));
        
        return planet;
	}
	
	@DeleteMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.ACCEPTED)
	@ApiOperation(value="Delete planet", notes = "Deletes a planet given an ID.")
	@ApiResponses(value = {@ApiResponse(code = 202, message = "Accepted"),
							@ApiResponse(code = 400, message = "Bad Request"),
							@ApiResponse(code = 403, message = "Forbidden"),
							@ApiResponse(code = 500, message = "Error"/*, response = Exception.class*/)})
	public void delete(@PathVariable final Integer id) {
		planetService.deleteById(id);
	}
}
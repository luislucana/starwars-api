package br.com.starwars.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.starwars.service.PlanetService;

/**
 * Classe controller para operacoes da entidade Planet.
 * 
 * @author Luis Lucana (luislucana@gmail.com)
 *
 */
@RestController
public class PlanetController {
	
	@Autowired
	private PlanetService playerService;
	
	@PostMapping("/createPlanet")
	@ResponseStatus(HttpStatus.OK)
	public String createPlanet() {
		return null;
	}
	
	@GetMapping("/listPlanetsFromDatabase")
	@ResponseStatus(HttpStatus.OK)
	public String listPlanetsFromDatabase(Pageable pageable) {
		return null;
	}
}
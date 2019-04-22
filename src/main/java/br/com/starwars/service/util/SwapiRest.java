package br.com.starwars.service.util;

import java.util.Arrays;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Classe utilitaria para fazer chamada remota para a Swapi API.
 * 
 * @author Luis Lucana (luislucana@gmail.com)
 *
 */
public class SwapiRest {

	private static final String SWAPI_API_URL = "https://swapi.co/api/planets";

	public ResponseEntity<String> getPlanetsFromSwapiAPI(Pageable pageable) {
		ResponseEntity<String> response = null;

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		try {
			response = new RestTemplate().exchange(SWAPI_API_URL + "?page=" + pageable.getPageNumber(), 
					HttpMethod.GET,
					new HttpEntity<String>("parameters", headers), String.class);

		} catch (Exception exception) {
			throw new RuntimeException("Swapi API nao esta disponivel no momento. Tente novamente mais tarde.",
					exception);
		}

		return response;
	}
}

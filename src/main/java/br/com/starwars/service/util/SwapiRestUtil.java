package br.com.starwars.service.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;

import br.com.starwars.service.dto.PlanetList;
import br.com.starwars.service.dto.Result;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Classe utilitaria para fazer chamada remota para a Swapi API.
 * 
 * @author Luis Lucana (luislucana@gmail.com)
 *
 */
public class SwapiRestUtil {

	private static final String SWAPI_API_URL = "https://swapi.co/api/planets?format=json";

	/*public static ResponseEntity<String> getPlanetsFromSwapiAPI(Pageable pageable) {
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
	}*/
	
	public static PlanetList getPlanetsFromSwapiAPI() throws IOException {
		PlanetList planets = null;
		
		String content = null;

		HttpGet httpGet = new HttpGet(SWAPI_API_URL);
		HttpEntity httpEntity = null;

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(httpGet)) {
			
			if (response.getStatusLine().getStatusCode() != HttpStatus.OK.value()) {
				throw new RuntimeException("Não foi possivel obter o conteudo do endereco: " + SWAPI_API_URL);
			}

			httpEntity = response.getEntity();
			content = EntityUtils.toString(httpEntity);
		} finally {
			EntityUtils.consume(httpEntity);
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		planets = gson.fromJson(content, PlanetList.class);
		
		return planets;
	}
}

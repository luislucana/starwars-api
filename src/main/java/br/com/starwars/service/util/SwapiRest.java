package br.com.starwars.service.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;

/**
 * Classe utilitaria para fazer chamada remota para a Swapi API.
 * 
 * @author Luis Lucana (luislucana@gmail.com)
 *
 */
public class SwapiRest {

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
	
	private static String getPlanetsFromSwapiAPI() throws IOException {
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

		return content;
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println("INICIO");
		
		String responseBody = getPlanetsFromSwapiAPI();
		
		System.out.println(String.valueOf(responseBody));
	}
}

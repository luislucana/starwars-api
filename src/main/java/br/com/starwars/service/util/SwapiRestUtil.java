package br.com.starwars.service.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.starwars.service.dto.PlanetList;
import br.com.starwars.service.dto.Result;
import br.com.starwars.web.exception.ResourceNotFoundException;

/**
 * Classe utilitaria para fazer chamada remota para a Swapi API.
 * 
 * @author Luis Lucana (luislucana@gmail.com)
 *
 */
public class SwapiRestUtil {

	private static final String SWAPI_API_URL = "https://swapi.co/api/planets";

	public static PageImpl<Result> getPlanetsFromSwapiAPI(Pageable pageable) throws IOException {
		PlanetList planets = null;
		String content = null;
		
		URI uri = buildURIWithParameters(SWAPI_API_URL, pageable);

		HttpGet httpGet = new HttpGet(uri);
		HttpEntity httpEntity = null;

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(httpGet)) {
			
			if (response.getStatusLine().getStatusCode() != HttpStatus.OK.value()) {
				throw new ResourceNotFoundException("Nao foi possivel obter o conteudo do endereco: " + SWAPI_API_URL);
			}

			httpEntity = response.getEntity();
			content = EntityUtils.toString(httpEntity);
		} finally {
			EntityUtils.consume(httpEntity);
		}
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		planets = gson.fromJson(content, PlanetList.class);
		
		List<Result> results = planets.getResults();
		
		int start = (int) pageable.getOffset();
		int end = (start + pageable.getPageSize()) > results.size() ? results.size() : (start + pageable.getPageSize());
		return new PageImpl<Result>(results.subList(start, end), pageable, results.size());
	}
	
	public static URI buildURIWithParameters(final String url, final Pageable pageable) {
		URI uri = null;
		URIBuilder builder = null;
		
		try {
			builder = new URIBuilder(url);
			builder.setParameter("format", "json");
			
			if (pageable != null) {
				builder.setParameter("page", String.valueOf(pageable.getPageNumber()));
				builder.setParameter("size", String.valueOf(pageable.getPageSize()));
				
				Sort sort = pageable.getSort();
				
				if (sort != null && !sort.isEmpty()) {
					List<Order> sortList = sort.stream().collect(Collectors.toList());
					
					for (Order order : sortList) {
						String property = order.getProperty();
						String directionName = order.getDirection().name();
						
						builder.setParameter(property, directionName);
					}
				}
			}
			
			uri = builder.build();
		} catch (URISyntaxException e) {
			throw new RuntimeException("Erro ao construir os parametros para request.");
		}
		
		return uri;
	}
}

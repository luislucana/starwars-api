package br.com.starwars.web.controller;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriTemplate;

import br.com.starwars.web.util.LinkUtil;

@Controller
public class RootController {
	
	@GetMapping("/")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void adminRoot(final HttpServletRequest request, final HttpServletResponse response) {
		final String rootUri = request.getRequestURL().toString();

		final URI starWarsUri = new UriTemplate("{rootUri}{resource}").expand(rootUri, "starwars");
		final String linkToStarWarsResources = LinkUtil.createLinkHeader(starWarsUri.toASCIIString(), "collection");
		response.addHeader("Link", linkToStarWarsResources);
	}
}

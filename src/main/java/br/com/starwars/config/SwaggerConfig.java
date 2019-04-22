package br.com.starwars.config;

import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger Configuration.
 * 
 * @author Luis Lucana (luislucana@gmail.com)
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Autowired
    private Environment env;

	@Bean
	public Docket getApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("br.com.starwars.web.controller")).paths(PathSelectors.any())
				.build().apiInfo(apiInfo())
				//.securitySchemes(Arrays.asList(securityScheme()))
				//.securityContexts(Arrays.asList(securityContext()))
				.directModelSubstitute(XMLGregorianCalendar.class, String.class)
				.directModelSubstitute(XMLGregorianCalendar.class, Date.class)
				.useDefaultResponseMessages(false)
				.tags(new Tag("Star Wars Planets API", "Star Wars Planets API operations."));
	}

    private ApiInfo apiInfo() {
    	Contact contact = null;
    	return new ApiInfo("Star Wars Planets API", "Star Wars Planets API.", env.getRequiredProperty("build.version"), "", contact, "", "");
    }
}
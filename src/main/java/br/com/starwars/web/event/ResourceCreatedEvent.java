package br.com.starwars.web.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

/**
 * 
 * 
 * @author Luis Lucana (luislucana@gmail.com)
 *
 */
@SuppressWarnings("serial")
public class ResourceCreatedEvent extends ApplicationEvent {
	
    private final HttpServletResponse response;
    
    private final long idOfNewResource;

    public ResourceCreatedEvent(final Object source, final HttpServletResponse response, final long idOfNewResource) {
        super(source);

        this.response = response;
        this.idOfNewResource = idOfNewResource;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public long getIdOfNewResource() {
        return idOfNewResource;
    }
}
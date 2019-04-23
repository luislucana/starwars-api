package br.com.starwars.web.exception;

/**
 * Classe de excecao para quando um recurso nao e encontrado.
 * 
 * @author Luis Lucana (luislucana@gmail.com)
 *
 */
@SuppressWarnings("serial")
public class ResourceNotFoundException extends RuntimeException {
	
	public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(final String message) {
        super(message);
    }

    public ResourceNotFoundException(final Throwable cause) {
        super(cause);
    }
}

package br.com.starwars.web.exception;

/**
 * Classe de excecao para quando for informado um nome de Planet que nao existe na Swapi API.
 * 
 * @author Luis Lucana (luislucana@gmail.com)
 *
 */
@SuppressWarnings("serial")
public class InvalidPlanetNameException extends RuntimeException {
	
	public InvalidPlanetNameException() {
        super();
    }

    public InvalidPlanetNameException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InvalidPlanetNameException(final String message) {
        super(message);
    }

    public InvalidPlanetNameException(final Throwable cause) {
        super(cause);
    }
}

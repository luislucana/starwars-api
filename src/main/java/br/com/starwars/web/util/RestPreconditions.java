package br.com.starwars.web.util;

/**
 * 
 * 
 * @author Luis Lucana (luislucana@gmail.com)
 *
 */
public final class RestPreconditions {

    private RestPreconditions() {
        throw new AssertionError();
    }

    /**
     * Check if some value was found, otherwise throw exception.
     * 
     * @param expression
     *            has value true if found, otherwise false
     * @throws MyResourceNotFoundException
     *             if expression is false, means value not found.
     */
    public static void checkFound(final boolean expression) {
        if (!expression) {
            //throw new MyResourceNotFoundException();
        }
    }

    /**
     * Check if some value was found, otherwise throw exception.
     * 
     * @param expression
     *            has value true if found, otherwise false
     * @throws MyResourceNotFoundException
     *             if expression is false, means value not found.
     */
    public static <T> T checkFound(final T resource) {
        if (resource == null) {
            //throw new MyResourceNotFoundException();
        }

        return resource;
    }

}
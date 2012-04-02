package de.paluch.burndown.sync.jira;

/**
 * <br>
 * <br>
 * Project: burdnown-chart <br>
 * Autor: mark <br>
 * Created: 25.03.2012 <br>
 * <br>
 */
public class JiraSyncException extends Exception {

    private static final long serialVersionUID = 5652588949612633135L;

    /**
	 *
	 */
    public JiraSyncException() {

    }

    /**
     * @param message
     */
    public JiraSyncException(String message) {

        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public JiraSyncException(String message, Throwable cause) {

        super(message, cause);
    }

    /**
     * @param cause
     */
    public JiraSyncException(Throwable cause) {

        super(cause);
    }

}

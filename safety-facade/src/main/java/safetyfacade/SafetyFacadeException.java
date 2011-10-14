package safetyfacade;

/**
 * Exception for wrapping Reflection-Exceptions during Safety-Facade calls.
 *
 *<br>
 *<br>Project: safety-facade
 *<br>Autor: mark
 *<br>Created: 14.10.2011
 *<br>
 *<br>
 */
public class SafetyFacadeException extends RuntimeException {

    private static final long serialVersionUID = 4375536516602726654L;

    /**
	 *
	 */
    public SafetyFacadeException() {
        super();
    }

    /**
     * @param cause
     */
    public SafetyFacadeException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     */
    public SafetyFacadeException(String message) {
        super(message);
    }

}

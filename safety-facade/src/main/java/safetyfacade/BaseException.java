package safetyfacade;

/**
 * <br>
 * <br>
 * Project: safety-facade <br>
 * Autor: mark <br>
 * Created: 14.10.2011 <br>
 * <br>
 */
public class BaseException extends Exception {

    private static final long serialVersionUID = -8357152004589590151L;

    /**
     *
     */
    public BaseException() {
        super();
    }

    /**
     * @param arg0
     * @param arg1
     */
    public BaseException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    /**
     * @param arg0
     */
    public BaseException(String arg0) {
        super(arg0);
    }

    /**
     * @param arg0
     */
    public BaseException(Throwable arg0) {
        super(arg0);
    }

}

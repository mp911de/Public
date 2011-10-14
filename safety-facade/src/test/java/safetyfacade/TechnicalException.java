package safetyfacade;

/**
 * <br>
 * <br>
 * Project: safety-facade <br>
 * Autor: mark <br>
 * Created: 14.10.2011 <br>
 * <br>
 */
public class TechnicalException extends BaseException
{

	/**
	 *
	 */
	public TechnicalException()
	{

	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public TechnicalException(String arg0, Throwable arg1)
	{

		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public TechnicalException(String arg0)
	{

		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public TechnicalException(Throwable arg0)
	{

		super(arg0);
	}

}

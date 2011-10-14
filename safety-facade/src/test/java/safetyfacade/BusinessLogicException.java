package safetyfacade;

/**
 * <br>
 * <br>
 * Project: safety-facade <br>
 * Autor: mark <br>
 * Created: 14.10.2011 <br>
 * <br>
 */
public class BusinessLogicException extends BaseException
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1902854081981916026L;

	/**
	 *
	 */
	public BusinessLogicException()
	{

		super();
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public BusinessLogicException(String arg0, Throwable arg1)
	{

		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public BusinessLogicException(String arg0)
	{

		super(arg0);
	}

	/**
	 * @param arg0
	 */
	public BusinessLogicException(Throwable arg0)
	{

		super(arg0);
	}

}

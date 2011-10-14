package safetyfacade;

import java.util.logging.Logger;

/**
 * Safety-Facade. This Facade is used to execute unsafe Code (Code with Exceptions) and replace native Exception-Classes
 * by Exception-Proxies. This preserves full Stack-Trace without needing 3rd Party Libs across VMs. <br>
 * <br>
 * Project: safety-facade <br>
 * Autor: mark <br>
 * Created: 14.10.2011 <br>
 * <br>
 */
public final class SafetyFacade
{

	private final static Logger LOG = Logger.getLogger(SafetyFacade.class.getName());

	/**
	 * Utility Constructor.
	 */
	private SafetyFacade()
	{

		super();
	}

	/**
	 * Execute and log Exception.
	 * 
	 * @param unsafe
	 */
	public static void execute(IUnsafeVoid unsafe)
	{

		try
		{
			unsafe.run();
		}
		catch (Exception e)
		{
			LOG.severe(e.getMessage());
		}
	}

	/**
	 * Execute, log Exception and return result.
	 * 
	 * @param <T>
	 *            Unsafe Result Type
	 * @param unsafe
	 * @return Result of unsafe
	 */
	public static <T> T execute(IUnsafe<T> unsafe)
	{

		try
		{
			return unsafe.run();
		}
		catch (Exception e)
		{
			LOG.severe(e.getMessage());
			return null;
		}
	}

	/**
	 * Execute unsafe and wrap into Exception if necessary.
	 * 
	 * @param <X>
	 *            Wrap in Exception-Type
	 * @param unsafe
	 * @param wrapInException
	 *            Exception-Type
	 * @throws X
	 *             (Type of wrapInException)
	 */
	public static <X extends BaseException> void execute(IUnsafeVoid unsafe, Class<X> wrapInException) throws X
	{

		try
		{
			unsafe.run();
		}
		catch (Exception e)
		{
			throw new ExceptionUtil().getSafeException(e, wrapInException);
		}
	}

	/**
	 * Execute, return result and wrap into Exception if necessary.
	 * 
	 * @param <T>
	 *            Unsafe Result Type
	 * @param <X>
	 *            Wrap in Exception-Type
	 * @param unsafe
	 * @param wrapInException
	 *            Exception-Type
	 * @return Result of unsafe
	 * @throws X
	 *             (Type of wrapInException)
	 */
	public static <T, X extends BaseException> T execute(IUnsafe<T> unsafe, Class<X> wrapInException) throws X
	{

		try
		{
			return unsafe.run();
		}
		catch (Exception e)
		{
			throw new ExceptionUtil().getSafeException(e, wrapInException);
		}
	}

}

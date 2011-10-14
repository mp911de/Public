package safetyfacade;

/**
 * Exception-Proxy for nested Exceptions. <br>
 * <br>
 * Project: safety-facade <br>
 * Autor: mark <br>
 * Created: 14.10.2011 <br>
 * <br>
 */
public class NestedProxyException extends BaseException
{

	private static final long serialVersionUID = -2199716409853893266L;

	private final String className;

	/**
	 * @param message
	 * @param className
	 */
	public NestedProxyException(String message, String className)
	{

		super(message);
		this.className = className;
	}

	/**
	 * @param message
	 * @param cause
	 * @param className
	 */
	public NestedProxyException(String message, Throwable cause, String className)
	{

		super(message, cause);
		this.className = className;
	}

	/**
	 * Empty, Stack-Trace is set external. {@inheritDoc}
	 */
	@Override
	public synchronized Throwable fillInStackTrace()
	{ // NOPMD

		return this;
	}

	/**
	 * Use className-Field as Class-Name. {@inheritDoc}
	 */
	@Override
	public String toString()
	{

		String s = className;
		String message = getLocalizedMessage();
		return (message != null) ? (s + ": " + message) : s;
	}

	/**
	 * @return className
	 */
	public String getClassName()
	{

		return className;
	}
}

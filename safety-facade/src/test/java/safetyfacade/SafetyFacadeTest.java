package safetyfacade;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * @author <a href="mailto:mark.paluch@1und1.de">Mark Paluch</a>
 */
public class SafetyFacadeTest
{

	@Test
	public void execute()
	{

		final String input = "1234";

		String result = SafetyFacade.execute(new IUnsafe<String>()
		{

			/**
			 * {@inheritDoc}
			 */
			@Override
			public String run() throws Exception
			{

				return input;

			}
		});

		assertSame(input, result);

	}

	@Test
	public void executeWithException()
	{

		try
		{
			String result = SafetyFacade.execute(new IUnsafe<String>()
			{

				/**
				 * {@inheritDoc}
				 */
				@Override
				public String run() throws Exception
				{

					throw new IllegalStateException("blubb");

				}
			}, TechnicalException.class);

			fail("no Exception.");
		}
		catch (TechnicalException e)
		{
			assertNotNull("java.lang.IllegalStateException: blubb", e.getMessage());

			assertEquals(e.getCause().getClass(), NestedProxyException.class);
			assertEquals("blubb", e.getCause().getMessage());
		}

	}

	@Test
	public void executeWithWrapSameTypeLevel1()
	{

		try
		{
			String result = SafetyFacade.execute(new IUnsafe<String>()
			{

				/**
				 * {@inheritDoc}
				 */
				@Override
				public String run() throws Exception
				{

					throw new TechnicalException("blubb");

				}
			}, TechnicalException.class);

			fail("no Exception.");
		}
		catch (TechnicalException e)
		{
			assertEquals("blubb", e.getMessage());
			assertNull(e.getCause());
		}

	}

	@Test
	public void executeWithWrapSameTypeLevel2()
	{

		try
		{
			String result = SafetyFacade.execute(new IUnsafe<String>()
			{

				/**
				 * {@inheritDoc}
				 */
				@Override
				public String run() throws Exception
				{

					throw new TechnicalException(new IllegalStateException("blubb"));

				}
			}, TechnicalException.class);

			fail("no Exception.");
		}
		catch (TechnicalException e)
		{
			assertNotNull(e.getMessage());

			assertEquals(e.getCause().getClass(), NestedProxyException.class);
			assertEquals("blubb", e.getCause().getMessage());
		}

	}

	@Test
	public void getStackTrace()
	{

		BusinessLogicException exception = new ExceptionUtil().getSafeException(new TechnicalException(),
				BusinessLogicException.class);
		StackTraceElement result = exception.getStackTrace()[0];
		assertEquals("getStackTrace", result.getMethodName());
	}

	@Test
	public void getMessage()
	{

		TechnicalException exception = new TechnicalException(new IllegalStateException("blubb"));
		assertNotNull(exception.getMessage());
	}

}

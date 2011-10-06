package example;

/**
 * Long running Task-Dummy Bean.
 * Created: 22.08.2011 <br>
 * <br>
 */
public class LongRunningTask extends Thread
{

	private Model model;

	/**
	 * @param model
	 */
	public LongRunningTask(Model model)
	{

		this.model = model;
	}

	/**
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run()
	{
		try
		{
			model.setRunning(true);

			Thread.sleep(2000);
			model.addMessage("first");
			Thread.sleep(2000);
			model.addMessage("second");
			Thread.sleep(2000);
			model.addMessage("third");
			Thread.sleep(2000);
			model.addMessage("last");
			model.setSuccess(true);
		}
		catch (Exception e)
		{
			model.setSuccess(false);
		}
		finally
		{
			model.setRunning(false);
		}
	}
}

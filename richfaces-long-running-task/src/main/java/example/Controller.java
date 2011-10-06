package example;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 * Controller Bean. Created: 22.08.2011 <br>
 * <br>
 */
@ManagedBean
@RequestScoped
public class Controller implements Serializable
{

	private static final long serialVersionUID = -4160088442207082094L;

	@ManagedProperty(value = "#{model}")
	private Model model;

	/**
	 * Start a long running task.
	 */
	public void startLongRunningTask()
	{

		// Beware of this in real J2EE envs as Threads are a bit risky. Use EJB
		// Timers od JMS instead.
		LongRunningTask task = new LongRunningTask(model);
		task.start();

	}

	/**
	 * @return the model
	 */
	public Model getModel()
	{

		return model;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(Model model)
	{

		this.model = model;
	}

}

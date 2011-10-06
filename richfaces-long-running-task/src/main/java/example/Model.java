package example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Model Bean. Created: 22.08.2011 <br>
 * <br>
 */
@ManagedBean
@SessionScoped
public class Model implements Serializable
{

	private static final long serialVersionUID = -6712635521042543903L;

	private boolean running = false;
	private boolean success = false;
	private List<String> messages = new ArrayList<String>();

	/**
	 * @return the running
	 */
	public boolean isRunning()
	{

		return running;
	}

	/**
	 * @param running
	 *            the running to set
	 */
	public void setRunning(boolean running)
	{

		this.running = running;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess()
	{

		return success;
	}

	/**
	 * @param success
	 *            the success to set
	 */
	public void setSuccess(boolean success)
	{

		this.success = success;
	}

	/**
	 * @return the messages
	 */
	public List<String> getMessages()
	{

		synchronized (messages)
		{
			List<String> copy = new ArrayList<String>();
			copy.addAll(messages);
			return copy;
		}
	}

	/**
	 * @return the last Message.
	 */
	public String getLastMessage()
	{

		synchronized (messages)
		{
			if (messages.isEmpty())
			{
				return "";
			}
			return messages.get(messages.size() - 1);
		}

	}

	public void addMessage(String message)
	{

		synchronized (messages)
		{
			messages.add(message);
		}
	}

}

package de.paluch.burndown.jsf.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 *<br>
 *<br>Project: burdnown-chart
 *<br>Autor: mark
 *<br>Created: 21.03.2012
 *<br>
 *<br>
 */
public abstract class AbstractJSFModel extends AbstractModel
{

	/**
	 *
	 */
	private static final long serialVersionUID = 9004259037762117695L;
	private boolean disabled;
	private Map<String, Serializable> properties = new HashMap<String, Serializable>();

	public void clear()
	{

	}

	public Map<String, Serializable> getProperties()
	{

		return properties;
	}

	public boolean isDisabled()
	{

		return disabled;
	}

	public void setDisabled(boolean disabled)
	{

		this.disabled = disabled;
	}

	public void setProperty(String key, Serializable value)
	{

		properties.put(key, value);

	}

}

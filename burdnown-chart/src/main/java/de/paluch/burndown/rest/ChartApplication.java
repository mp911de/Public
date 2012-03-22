package de.paluch.burndown.rest;

import java.util.Collections;
import java.util.Set;

import javax.ws.rs.core.Application;

/**
 *
 *<br>
 *<br>Project: burdnown-chart
 *<br>Autor: mark
 *<br>Created: 22.03.2012
 *<br>
 *<br>
 */
public class ChartApplication extends Application
{

	/**
	 *
	 */
	public ChartApplication()
	{

	}

	/**
	 * @see javax.ws.rs.core.Application#getClasses()
	 */
	@Override
	public Set<Class< ? >> getClasses()
	{

		return (Set) Collections.singleton(TeamsResource.class);
	}
}

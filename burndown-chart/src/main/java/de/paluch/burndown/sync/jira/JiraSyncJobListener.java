package de.paluch.burndown.sync.jira;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import de.paluch.burndown.sync.jira.model.JiraSync;
import de.paluch.burndown.sync.jira.model.UpdateMode;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * Context-Listener for Sync-Runner.
 *<br>
 *<br>Project: burdnown-chart
 *<br>Autor: mark
 *<br>Created: 25.03.2012
 *<br>
 *<br>
 */
public class JiraSyncJobListener implements ServletContextListener
{

	private Scheduler scheduler;

	/**
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce)
	{

		try
		{
			scheduler.shutdown();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce)
	{

		try
		{
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();

			JiraSync syncSettings = new JiraSyncDataAccess().getJiraSync();
			if (syncSettings.getUpdateMode() != null && syncSettings.getUpdateMode() != UpdateMode.OFF)
			{
				JobDetail job = newJob(JiraSyncJob.class)
						.withIdentity("JiraSyncJob", "none")
						.build();

				// Trigger the job to run now, and then repeat every 40 seconds
				TriggerBuilder<Trigger> triggerBuilder = newTrigger()
						.withIdentity("JiraSyncTrigger", "none")
						.startNow();

				if (syncSettings.getUpdateMode() == UpdateMode.DAILY)
				{
					triggerBuilder.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(24));
				}

				if (syncSettings.getUpdateMode() == UpdateMode.HOURLY)
				{
					triggerBuilder.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(1));
				}

				// Tell quartz to schedule the job using our trigger
				scheduler.scheduleJob(job, triggerBuilder.build());
			}

		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}

	}

}

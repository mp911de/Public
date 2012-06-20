package de.paluch.burndown.sync.jira;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import de.paluch.burndown.sync.jira.model.JiraSync;
import de.paluch.burndown.sync.jira.model.UpdateMode;

/**
 * Context-Listener for Sync-Runner. <br>
 * <br>
 * Project: burdnown-chart <br>
 * Autor: mark <br>
 * Created: 25.03.2012 <br>
 * <br>
 */
public class JiraSyncJobListener implements ServletContextListener {

    private Scheduler scheduler;

    /**
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        try {
            scheduler.shutdown();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();

            JiraSync syncSettings = new JiraSyncDataAccess().getJiraSync();
            if (syncSettings.getUpdateMode() != null && syncSettings.getUpdateMode() != UpdateMode.OFF) {
                scheduleJob(syncSettings);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void scheduleJob(JiraSync syncSettings) throws SchedulerException {
        JobDetail job = newJob(JiraSyncJob.class).withIdentity("JiraSyncJob", "none").build();

        // Trigger the job to run now, and then repeat every 40 seconds
        TriggerBuilder<Trigger> triggerBuilder = newTrigger().withIdentity("JiraSyncTrigger", "none");

        switch (syncSettings.getUpdateMode()) {
            case DAILY:
                triggerBuilder.withSchedule(SimpleScheduleBuilder.repeatHourlyForever(24));
                break;
            case HOURLY:
                triggerBuilder.withSchedule(SimpleScheduleBuilder.repeatHourlyForever(1));
                break;
            case FOUR_HOURLY:
                triggerBuilder.withSchedule(SimpleScheduleBuilder.repeatHourlyForever(4));
                break;
            case TWELVE_HOURLY:
                triggerBuilder.withSchedule(SimpleScheduleBuilder.repeatHourlyForever(12));
                break;

            case OFF:
                return;
        }

        // Tell quartz to schedule the job using our trigger
        scheduler.scheduleJob(job, triggerBuilder.build());
    }

}

package de.paluch.powerflare.command;

import de.paluch.powerflare.channel.ICommunicationChannel;

import java.util.concurrent.ScheduledExecutorService;

/**
 * Created with IntelliJ IDEA. User: mark Date: 25.04.12 Time: 08:11 To change this template use File | Settings | File
 * Templates.
 */
public interface ICommand {

    void execute(ScheduledExecutorService executorService, ICommunicationChannel channel);
}

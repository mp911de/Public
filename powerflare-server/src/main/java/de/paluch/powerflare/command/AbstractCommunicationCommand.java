package de.paluch.powerflare.command;

import de.paluch.powerflare.Multiplexer;
import de.paluch.powerflare.channel.ICommunicationChannel;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA. User: mark Date: 29.04.12 Time: 19:58 To change this template use File | Settings | File
 * Templates.
 */
public abstract class AbstractCommunicationCommand extends AbstractCommand {

    private int port;

    protected AbstractCommunicationCommand(int port) {
        this.port = port;
    }


    protected abstract List<? extends RelayCommunicationCallable> getCommunicationCommands();

    public void execute(ICommunicationChannel channel, ScheduledExecutorService executor) {

        channel.waitForFreeChannel(port, this);
        channel.lock(port, this);

        List<? extends RelayCommunicationCallable> callables = getCommunicationCommands();

        for (RelayCommunicationCallable callable : callables) {
            callable.setChannel(channel);
            callable.setCommand(this);
            channel.lock(callable.getPort(), this);

        }

        for (RelayCommunicationCallable callable : callables) {
            executor.schedule(callable, callable.getDelay(), TimeUnit.MILLISECONDS);
        }

    }


    public int getPort() {
        return port;
    }
}

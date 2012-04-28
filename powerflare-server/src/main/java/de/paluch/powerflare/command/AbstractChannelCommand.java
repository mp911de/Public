package de.paluch.powerflare.command;

import de.paluch.powerflare.channel.ICommunicationChannel;
import de.paluch.powerflare.channel.Mutex;

import java.util.concurrent.ScheduledExecutorService;

/**
 * Created with IntelliJ IDEA. User: mark Date: 27.04.12 Time: 17:31 To change this template use File | Settings | File
 * Templates.
 */
public abstract class AbstractChannelCommand implements ICommand {

    private Mutex lock = new Mutex();
    private int port;

    protected AbstractChannelCommand(int port) {
        this.port = port;
    }

    public Mutex getLock() {
        return lock;
    }

    @Override
    public void execute(ScheduledExecutorService executorService, ICommunicationChannel channel) {
        channel.waitForFreeChannel(port);
        synchronized (channel) {
            channel.waitForFreeChannel(port);
            executeImpl(executorService, channel);
        }
    }

    protected abstract void executeImpl(ScheduledExecutorService executorService, ICommunicationChannel channel);

    public int getPort() {
        return port;
    }
}

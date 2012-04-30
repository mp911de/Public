package de.paluch.powerflare;

import de.paluch.powerflare.channel.ICommunicationChannel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created with IntelliJ IDEA. User: mark Date: 25.04.12 Time: 08:09 To change this template use File | Settings | File
 * Templates.
 */
public class Multiplexer {

    private static Multiplexer instance = new Multiplexer();

    private ScheduledExecutorService exec = Executors.newScheduledThreadPool(10);
    private ICommunicationChannel channel;

    public ScheduledExecutorService getExec() {
        return exec;
    }

    public ICommunicationChannel getChannel() {
        return channel;
    }

    public void setChannel(ICommunicationChannel channel) {
        this.channel = channel;
    }

    public static Multiplexer getInstance() {
        return instance;
    }
}

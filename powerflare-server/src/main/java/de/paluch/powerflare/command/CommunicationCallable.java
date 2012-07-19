package de.paluch.powerflare.command;

import de.paluch.powerflare.channel.ICommunicationChannel;

import java.util.concurrent.Callable;

/**
 * Created with IntelliJ IDEA. User: mark Date: 29.04.12 Time: 19:46 To change this template use File | Settings | File
 * Templates.
 */
public abstract class CommunicationCallable implements Callable<Void> {


    private ICommunicationChannel channel;

    @Override
    public Void call() throws Exception {
        sendData(channel);
        return null;
    }

    protected abstract void sendData(ICommunicationChannel channel);

    public ICommunicationChannel getChannel() {
        return channel;
    }

    public void setChannel(ICommunicationChannel channel) {
        this.channel = channel;
    }

}

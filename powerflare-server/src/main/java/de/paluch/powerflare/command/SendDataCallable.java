package de.paluch.powerflare.command;

import de.paluch.powerflare.channel.ICommunicationChannel;

import java.util.concurrent.Callable;

/**
 * Created with IntelliJ IDEA. User: mark Date: 25.04.12 Time: 08:14 To change this template use File | Settings | File
 * Templates.
 */
public class SendDataCallable implements Callable<Void> {
    private ICommunicationChannel channel;
    private byte[] data;
    private int port;
    private boolean lock;
    private boolean unlock;

    public SendDataCallable(ICommunicationChannel controller, byte[] data, int port, boolean lock, boolean unlock) {
        this.channel = controller;
        this.data = data;
        this.port = port;
        this.lock = lock;
        this.unlock = unlock;
    }

    @Override
    public Void call() throws Exception {

        if (lock) {
            channel.lock(port);
        }

        channel.sendData(data);

        if (unlock) {
            channel.unlock(port);
        }


        return null;
    }
}

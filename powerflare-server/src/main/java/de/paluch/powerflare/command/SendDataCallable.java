package de.paluch.powerflare.command;

import de.paluch.powerflare.channel.ICommunicationChannel;
import de.paluch.powerflare.channel.Mutex;

import java.util.concurrent.Callable;

/**
 * Created with IntelliJ IDEA. User: mark Date: 25.04.12 Time: 08:14 To change this template use File | Settings | File
 * Templates.
 */
public class SendDataCallable implements Callable<Void> {
    private ICommunicationChannel channel;
    private byte[] data;
    private int port;
    private boolean performUnlock;
    private Mutex lock;

    public SendDataCallable(ICommunicationChannel controller, byte[] data, int port, boolean performUnlock,
                            Mutex lock) {
        this.channel = controller;
        this.data = data;
        this.port = port;
        this.performUnlock = performUnlock;
        this.lock = lock;
    }

    @Override
    public Void call() throws Exception {

        long now = System.currentTimeMillis();
        channel.lock(lock, port);

        channel.sendData(data);

        if (performUnlock) {
            channel.unlock(lock, port);
        }

        return null;
    }
}

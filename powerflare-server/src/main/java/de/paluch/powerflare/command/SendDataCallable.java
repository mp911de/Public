package de.paluch.powerflare.command;

import de.paluch.powerflare.channel.ICommunicationChannel;

/**
 * Created with IntelliJ IDEA. User: mark Date: 25.04.12 Time: 08:14 To change this template use File | Settings | File
 * Templates.
 */
public class SendDataCallable extends RelayCommunicationCallable {
    private byte[] data;

    public SendDataCallable(byte[] data, int port, int delay, boolean unlock ) {
        super(port, delay, unlock);
        this.data = data;
    }

    @Override
    protected void sendData(ICommunicationChannel channel) {
        channel.sendData(data);
    }
}

package de.paluch.powerflare.channel;

/**
 * Created with IntelliJ IDEA. User: mark Date: 27.04.12 Time: 08:29 To change this template use File | Settings | File
 * Templates.
 */
public class DummyCommChannel extends AbstractCommunicationChannel implements ICommunicationChannel {
    @Override
    public void sendData(byte[] data) {
        System.out.println("Sending: " + new String(data));
    }

    @Override
    public void close() {
    }
}

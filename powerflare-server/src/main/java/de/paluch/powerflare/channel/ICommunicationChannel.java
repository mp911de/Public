package de.paluch.powerflare.channel;

/**
 * Created with IntelliJ IDEA. User: mark Date: 27.04.12 Time: 08:27 To change this template use File | Settings | File
 * Templates.
 */
public interface ICommunicationChannel {

    void sendData(byte[] data);

    void close();

    void lock(int port, Object owner);

    void unlock(int port, Object owner);

    public void waitForFreeChannel(int port, Object owner);
}

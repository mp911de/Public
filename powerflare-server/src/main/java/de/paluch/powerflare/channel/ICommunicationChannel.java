package de.paluch.powerflare.channel;

/**
 * Created with IntelliJ IDEA. User: mark Date: 27.04.12 Time: 08:27 To change this template use File | Settings | File
 * Templates.
 */
public interface ICommunicationChannel {

    void sendData(byte[] data);

    void close();

    void lock(Mutex lock, int port);

    void unlock(Mutex lock, int port);

    public void waitForFreeChannel(int port);
}

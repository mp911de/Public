package de.paluch.powerflare.channel;

/**
 * Created with IntelliJ IDEA. User: mark Date: 27.04.12 Time: 08:27 To change this template use File | Settings | File
 * Templates.
 */
public interface ICommunicationChannel {

    /**
     * Send Data to Channel.
     * @param data
     */
    void sendData(byte[] data);

    /**
     * Close Channel.
     */
    void close();

    /**
     * Lock port of Channel.
     * @param port
     * @param owner Lock-Owner
     */
    void lock(int port, Object owner);


    /**
     * Unlock port of Channel.
     * @param port
     * @param owner Lock-Owner
     */
    void unlock(int port, Object owner);

    /**
     * Wait until channel is free to send.
     * @param port
     * @param owner Lock-Owner
     */
    public void waitForFreeChannel(int port, Object owner);
}

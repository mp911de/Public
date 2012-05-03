package de.paluch.powerflare.channel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: mark Date: 27.04.12 Time: 08:29 To change this template use File | Settings | File
 * Templates.
 */
public class DummyCommChannel extends AbstractCommunicationChannel implements ICommunicationChannel {
    private List<byte[]> history = Collections.synchronizedList(new ArrayList<byte[]>());

    @Override
    public void sendData(byte[] data) {
        System.out.println("Sending: " + new String(data));
        history.add(data);
    }

    @Override
    public void close() {
    }

    public List<byte[]> getHistory() {
        return history;
    }
}

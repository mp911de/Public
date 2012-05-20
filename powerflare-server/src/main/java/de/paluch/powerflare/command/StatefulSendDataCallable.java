package de.paluch.powerflare.command;

import de.paluch.powerflare.channel.ICommunicationChannel;
import de.paluch.powerflare.state.PortState;
import de.paluch.powerflare.state.StateStore;

/**
 * User: mark Date: 20.05.12 Time: 20:04
 */
public class StatefulSendDataCallable extends SendDataCallable {
    private PortState state;
    public StatefulSendDataCallable(byte[] data, int port, int delay, boolean unlock, PortState state) {
        super(data, port, delay, unlock);
        this.state = state;
    }

    @Override
    protected void sendData(ICommunicationChannel channel) {
        super.sendData(channel);
        StateStore.getInstance().setState(getPort(), state);
    }
}

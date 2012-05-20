package de.paluch.powerflare.command;

import de.paluch.powerflare.state.PortState;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: mark Date: 25.04.12 Time: 08:14 To change this template use File | Settings | File
 * Templates.
 */
public class PowerflareSwitchOffCommand extends ConnectDisconnectCommand {

    private final static int RELAY_CONNECT_DELAY = 0;
    private final static int RELAY_DISCONNECT_DELAY = 1800;


    public PowerflareSwitchOffCommand(byte port) {
        super(port);
    }

    @Override
    protected List<? extends RelayCommunicationCallable> getCommunicationCommands() {
        byte dataOn[] = new byte[] { (byte) (RelayCommands.BASE_COMMAND_CONNECT + getPort()) };
        byte dataOff[] = new byte[] { (byte) (RelayCommands.BASE_COMMAND_DISCONNECT + getPort()) };

        SendDataCallable connect = new SendDataCallable(dataOn, getPort(), getConnectDelay(), false);
        SendDataCallable disconnect = new StatefulSendDataCallable(dataOff, getPort(), getDisconnectDelay(),
                                                                   true, PortState.OFF);

        return Arrays.asList(connect, disconnect);
    }


    @Override
    protected int getConnectDelay() {
        return RELAY_CONNECT_DELAY;
    }

    @Override
    protected int getDisconnectDelay() {
        return RELAY_DISCONNECT_DELAY;
    }
}

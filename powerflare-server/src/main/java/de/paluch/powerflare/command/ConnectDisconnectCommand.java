package de.paluch.powerflare.command;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: mark Date: 25.04.12 Time: 08:14 To change this template use File | Settings | File
 * Templates.
 */
public class ConnectDisconnectCommand extends AbstractCommunicationCommand {

    private final static int RELAY_CONNECT_DELAY = 0;
    private final static int RELAY_DISCONNECT_DELAY = 200;


    public ConnectDisconnectCommand(byte port) {
        super(port);
    }

    @Override
    protected List<? extends RelayCommunicationCallable> getCommunicationCommands() {
        byte dataOn[] = new byte[] { (byte) (RelayCommands.BASE_COMMAND_CONNECT + getPort()) };
        byte dataOff[] = new byte[] { (byte) (RelayCommands.BASE_COMMAND_DISCONNECT + getPort()) };

        SendDataCallable connect = new SendDataCallable(dataOn, getPort(), getConnectDelay(), false);
        SendDataCallable disconnect = new SendDataCallable(dataOff, getPort(), getDisconnectDelay(), true);

        return Arrays.asList(connect, disconnect);
    }

    protected int getConnectDelay() {
        return RELAY_CONNECT_DELAY;
    }

    protected int getDisconnectDelay() {
           return RELAY_DISCONNECT_DELAY;
       }
}


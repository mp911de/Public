package de.paluch.powerflare.command;

import de.paluch.powerflare.state.PortState;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: mark Date: 25.04.12 Time: 08:14 To change this template use File | Settings | File
 * Templates.
 */
public class PowerflareUsbPowerSwitchOnCommand extends ConnectDisconnectCommand {

    public final static int RELAY_CONNECT_DELAY = 200;
    public final static int RELAY_DISCONNECT_DELAY = 400;
    public final int controlPortOffset = 4;
    public final int powerPortOffset = 0;

    public PowerflareUsbPowerSwitchOnCommand(byte port) {

        super(port);
    }

    @Override
    protected List<? extends RelayCommunicationCallable> getCommunicationCommands() {

        byte dataPower[] = new byte[] { (byte) (RelayCommands.BASE_COMMAND_CONNECT + getPort() + powerPortOffset) };
        byte dataOn[] = new byte[] { (byte) (RelayCommands.BASE_COMMAND_CONNECT + getPort() + controlPortOffset) };
        byte dataOff[] = new byte[] { (byte) (RelayCommands.BASE_COMMAND_DISCONNECT + getPort() + controlPortOffset) };

        SendDataCallable powerConnect = new StatefulSendDataCallable(dataPower, getPort() + powerPortOffset, 0, true,
                                                                     PortState.ON);

        SendDataCallable connect = new SendDataCallable(dataOn, getPort() + controlPortOffset, getConnectDelay(), false);
        SendDataCallable disconnect = new SendDataCallable(dataOff, getPort() + controlPortOffset,
                                                           getDisconnectDelay(),
                                                           true);

        return Arrays.asList(powerConnect, connect, disconnect);
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

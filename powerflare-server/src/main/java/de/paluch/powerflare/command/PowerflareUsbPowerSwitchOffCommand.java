package de.paluch.powerflare.command;

import de.paluch.powerflare.state.PortState;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: mark Date: 25.04.12 Time: 08:14 To change this template use File | Settings | File
 * Templates.
 */
public class PowerflareUsbPowerSwitchOffCommand extends PowerflareUsbPowerSwitchOnCommand {

    public PowerflareUsbPowerSwitchOffCommand(byte port) {

        super(port);
    }

    @Override
    protected List<? extends RelayCommunicationCallable> getCommunicationCommands() {

        byte dataPowerOff[] = new byte[] { (byte) (RelayCommands.BASE_COMMAND_DISCONNECT + getPort() + powerPortOffset) };

        SendDataCallable powerDisconnect = new StatefulSendDataCallable(dataPowerOff, getPort() + powerPortOffset,
                                                                        getDisconnectDelay(),
                                                                        true, PortState.OFF);

        return Arrays.asList(powerDisconnect);
    }

}

package de.paluch.powerflare.command;

import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: mark Date: 25.04.12 Time: 08:14 To change this template use File | Settings | File
 * Templates.
 */
public class ConnectCommand extends AbstractCommunicationCommand {


    public ConnectCommand(byte port) {
        super(port);
    }

    @Override
    public List<? extends RelayCommunicationCallable> getCommunicationCommands() {
        byte dataOn[] = new byte[] { (byte) (RelayCommands.BASE_COMMAND_CONNECT + getPort()) };

        SendDataCallable callable = new SendDataCallable(dataOn, getPort(), 0, true);

        return Arrays.asList(callable);
    }

}

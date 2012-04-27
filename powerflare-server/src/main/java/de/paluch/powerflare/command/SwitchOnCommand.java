package de.paluch.powerflare.command;

import de.paluch.powerflare.channel.ICommunicationChannel;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA. User: mark Date: 25.04.12 Time: 08:14 To change this template use File | Settings | File
 * Templates.
 */
public class SwitchOnCommand implements ICommand {

    public final static int RELAY_CONNECT_DELAY = 0;
    public final static int RELAY_DISCONNECT_DELAY = 200;

    private byte port = 0;

    public SwitchOnCommand(byte port) {
        this.port = port;
    }

    @Override
    public void execute(ScheduledExecutorService executorService, ICommunicationChannel channel) {
        byte dataOn[] = new byte[] { (byte) (RelayCommands.BASE_COMMAND_CONNECT + port) };
        byte dataOff[] = new byte[] { (byte) (RelayCommands.BASE_COMMAND_DISCONNECT + port) };

        SendDataCallable connect = new SendDataCallable(channel, dataOn, port, true, false);
        SendDataCallable disconnect = new SendDataCallable(channel, dataOff, port, false, true);
        executorService.schedule(connect, RELAY_CONNECT_DELAY, TimeUnit.MILLISECONDS);
        executorService.schedule(disconnect, RELAY_DISCONNECT_DELAY, TimeUnit.MILLISECONDS);
    }
}

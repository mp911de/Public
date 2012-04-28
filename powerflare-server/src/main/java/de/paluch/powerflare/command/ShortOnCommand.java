package de.paluch.powerflare.command;

import de.paluch.powerflare.channel.ICommunicationChannel;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA. User: mark Date: 25.04.12 Time: 08:14 To change this template use File | Settings | File
 * Templates.
 */
public class ShortOnCommand extends AbstractChannelCommand implements ICommand {

    public final static int RELAY_CONNECT_DELAY = 0;
    public final static int RELAY_DISCONNECT_DELAY = 200;


    public ShortOnCommand(byte port) {
        super(port);
    }

    @Override
    public void executeImpl(ScheduledExecutorService executorService, ICommunicationChannel channel) {
        byte dataOn[] = new byte[] { (byte) (RelayCommands.BASE_COMMAND_CONNECT + getPort()) };
        byte dataOff[] = new byte[] { (byte) (RelayCommands.BASE_COMMAND_DISCONNECT + getPort()) };

        SendDataCallable connect = new SendDataCallable(channel, dataOn, getPort(), false, getLock());
        SendDataCallable disconnect = new SendDataCallable(channel, dataOff, getPort(), true, getLock());
        executorService.schedule(connect, RELAY_CONNECT_DELAY, TimeUnit.MILLISECONDS);
        executorService.schedule(disconnect, RELAY_DISCONNECT_DELAY, TimeUnit.MILLISECONDS);
    }
}

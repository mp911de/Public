package de.paluch.powerflare.command;

import de.paluch.powerflare.channel.ICommunicationChannel;

import java.util.concurrent.ScheduledExecutorService;

/**
 * Created with IntelliJ IDEA. User: mark Date: 25.04.12 Time: 08:14 To change this template use File | Settings | File
 * Templates.
 */
public class DisconnectCommand extends AbstractChannelCommand implements ICommand {


    public DisconnectCommand(byte port) {
        super(port);
    }


    @Override
    public void executeImpl(ScheduledExecutorService executorService, ICommunicationChannel channel) {
        byte dataOn[] = new byte[] { (byte) (RelayCommands.BASE_COMMAND_DISCONNECT + getPort()) };

        SendDataCallable connect = new SendDataCallable(channel, dataOn, getPort(), true, getLock());
        executorService.submit(connect);
    }
}

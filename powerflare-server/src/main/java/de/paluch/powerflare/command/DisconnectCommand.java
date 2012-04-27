package de.paluch.powerflare.command;

import de.paluch.powerflare.channel.ICommunicationChannel;

import java.util.concurrent.ScheduledExecutorService;

/**
 * Created with IntelliJ IDEA. User: mark Date: 25.04.12 Time: 08:14 To change this template use File | Settings | File
 * Templates.
 */
public class DisconnectCommand implements ICommand {

    private byte port = 0;

    public DisconnectCommand(byte port) {
        this.port = port;
    }


    @Override
    public void execute(ScheduledExecutorService executorService, ICommunicationChannel channel) {
        byte dataOn[] = new byte[] { (byte) (RelayCommands.BASE_COMMAND_DISCONNECT + port) };

        SendDataCallable connect = new SendDataCallable(channel, dataOn, port, true, true);
        executorService.submit(connect);
    }
}

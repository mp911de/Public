package de.paluch.powerflare.command;

/**
 * Created with IntelliJ IDEA. User: mark Date: 25.04.12 Time: 08:14 To change this template use File | Settings | File
 * Templates.
 */
public class PowerflareSwitchOnCommand extends ConnectDisconnectCommand {

    private final static int RELAY_CONNECT_DELAY = 0;
    private final static int RELAY_DISCONNECT_DELAY = 200;


    public PowerflareSwitchOnCommand(byte port) {
        super(port);
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

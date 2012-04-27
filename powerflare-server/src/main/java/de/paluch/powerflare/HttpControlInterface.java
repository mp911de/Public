package de.paluch.powerflare;


import de.paluch.powerflare.command.*;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * Created with IntelliJ IDEA. User: mark Date: 25.04.12 Time: 08:02 To change this template use File | Settings | File
 * Templates.
 */
@Path("/")
public class HttpControlInterface {

    @Path("powerflare/{port:[0-8]}/{state:(ON|OFF)}")
    public void controlPowerflare(@PathParam("port") byte port, @PathParam("state") String state) {
        System.out.println("switchPort port|" + port + "|state|" + state);

        if (state == null) {
            throw new IllegalArgumentException("state is null");
        }

        Server server = Server.getInstance();
        ICommand command = null;
        if (state.equalsIgnoreCase("ON")) {
            command = new SwitchOnCommand(port);
        }

        if (state.equalsIgnoreCase("OFF")) {
            command = new SwitchOffCommand(port);
        }

        if (command != null) {
            Multiplexer.getInstance().execute(command);
        }

    }

    @Path("relay/{port:[0-8]}/{state:(CONNECT|DISCONNECT)}")
    public void controlPort(@PathParam("port") byte port, @PathParam("state") String state) {
        System.out.println("controlPort port|" + port + "|state|" + state);

        if (state == null) {
            throw new IllegalArgumentException("state is null");
        }

        Server server = Server.getInstance();
        ICommand command = null;
        if (state.equalsIgnoreCase("CONNECT")) {
            command = new ConnectCommand(port);
        }

        if (state.equalsIgnoreCase("DISCONNECT")) {
            command = new DisconnectCommand(port);
        }

        if (command != null) {
            Multiplexer.getInstance().execute(command);
        }

    }

}

package de.paluch.powerflare;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import de.paluch.powerflare.command.ConnectCommand;
import de.paluch.powerflare.command.DisconnectCommand;
import de.paluch.powerflare.command.ICommand;
import de.paluch.powerflare.command.SwitchOffCommand;
import de.paluch.powerflare.command.SwitchOnCommand;

/**
 * Created with IntelliJ IDEA. User: mark Date: 25.04.12 Time: 08:02 To change this template use File | Settings | File
 * Templates.
 */
@Path("/")
public class HttpControlInterface {

    @Path("powerflare/{port:[0-8]}/{state:(ON|OFF)}")
    @GET
    public String controlPowerflare(@PathParam("port") byte port, @PathParam("state") String state) {
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
        return "OK";
    }

    @Path("relay/{port:[0-8]}/{state:(CONNECT|DISCONNECT)}")
    @GET
    public String controlPort(@PathParam("port") byte port, @PathParam("state") String state) {
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

        return "OK";

    }

}

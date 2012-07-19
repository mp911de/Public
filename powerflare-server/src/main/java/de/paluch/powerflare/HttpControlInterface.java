package de.paluch.powerflare;

<<<<<<< HEAD
=======

import de.paluch.powerflare.command.*;
import de.paluch.powerflare.state.StateStore;

>>>>>>> c212593d264438bd917dccd61b62279fb5e4664b
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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

    public static final String RESULT_FAIL = "FAIL";
    public static final String RESULT_OK = "OK";

    /**
     * Switch Power-Flares.
     *
     * @param port
     * @param state
     * @return OK/FAIL
     */
    @Path("powerflare/{port:[0-8]}/{state:(ON|OFF)}")
    @GET
<<<<<<< HEAD
    public String controlPowerflare(@PathParam("port") byte port, @PathParam("state") String state) {
        System.out.println("switchPort port|" + port + "|state|" + state);
=======
    @Produces(MediaType.TEXT_PLAIN)
    public String controlPowerflare(@PathParam("port") byte port, @PathParam("state") String state) {
        System.out.println("controlPowerflare port|" + port + "|state|" + state);
>>>>>>> c212593d264438bd917dccd61b62279fb5e4664b

        if (state == null) {
            throw new IllegalArgumentException("state is null");
        }

        AbstractCommunicationCommand command = null;
        if (state.equalsIgnoreCase("ON")) {
            command = new PowerflareSwitchOnCommand(port);
        }

        if (state.equalsIgnoreCase("OFF")) {
            command = new PowerflareSwitchOffCommand(port);
        }

        if (command != null) {
            command.execute(Multiplexer.getInstance().getChannel(), Multiplexer.getInstance().getExec());
            return RESULT_OK;
        }
<<<<<<< HEAD
        return "OK";
=======
        return RESULT_FAIL;
    }

    /**
     * Get Powerflare State.
     *
     * @param port
     * @return ON/OFF
     */
    @Path("powerflare/{port:[0-8]}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String controlPowerflare(@PathParam("port") byte port) {
           return StateStore.getInstance().getState(port).name();
>>>>>>> c212593d264438bd917dccd61b62279fb5e4664b
    }

    /**
     * Connect-Disconnect Port with 100ms Connect Delay.
     *
     * @param port
     * @return OK
     */
    @Path("switch/{port:[0-8]}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String controlSwitch(@PathParam("port") byte port) {
        System.out.println("controlSwitch port|" + port);


        AbstractCommunicationCommand command = new ConnectDisconnectCommand(port);
        command.execute(Multiplexer.getInstance().getChannel(), Multiplexer.getInstance().getExec());
        return RESULT_OK;
    }

    /**
     * Connect or Disconnect Port.
     *
     * @param port
     * @param state
     * @return OK/FAIL
     */
    @Path("relay/{port:[0-8]}/{state:(CONNECT|DISCONNECT)}")
    @GET
<<<<<<< HEAD
=======
    @Produces(MediaType.TEXT_PLAIN)
>>>>>>> c212593d264438bd917dccd61b62279fb5e4664b
    public String controlPort(@PathParam("port") byte port, @PathParam("state") String state) {
        System.out.println("controlPort port|" + port + "|state|" + state);

        if (state == null) {
            throw new IllegalArgumentException("state is null");
        }

        AbstractCommunicationCommand command = null;
        if (state.equalsIgnoreCase("CONNECT")) {
            command = new ConnectCommand(port);
        }

        if (state.equalsIgnoreCase("DISCONNECT")) {
            command = new DisconnectCommand(port);
        }

        if (command != null) {
            command.execute(Multiplexer.getInstance().getChannel(), Multiplexer.getInstance().getExec());
            return RESULT_OK;
        }
<<<<<<< HEAD

        return "OK";

=======
        return RESULT_FAIL;
>>>>>>> c212593d264438bd917dccd61b62279fb5e4664b
    }

}

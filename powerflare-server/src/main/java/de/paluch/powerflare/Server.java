package de.paluch.powerflare;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.jersey.api.core.ClassNamesResourceConfig;
import com.sun.jersey.core.impl.provider.entity.StringProvider;
import com.sun.net.httpserver.HttpServer;
import de.paluch.powerflare.channel.DummyCommChannel;
import de.paluch.powerflare.channel.ICommunicationChannel;
import de.paluch.powerflare.channel.SerialPortCommunicationChannel;
import de.paluch.powerflare.state.StateStore;

/**
 * Server Initiator.
 */
public class Server {

    /**
     * Uses a Dummy-Channel instead real Comm-Channel.
     */
    public static final String DUMMY_MODE = "dummyMode";

    private static Server instance;

    private ICommunicationChannel channel;
    private boolean active = true;
    private boolean shutdown = false;


    public static void main(String args[]) throws Exception {

        if (args.length == 0) {
            System.out.println("Usage: Server port (device path/name)");
            return;
        }
        System.out.println("using port " + args[0]);

        instance = new Server(args[0]);
        instance.installShutdownHook();
        instance.run();
        instance.close();
    }

    private void installShutdownHook() {

        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                instance.active = false;

                System.out.println("Stopping server");
                try {
                    while (!instance.shutdown) {
                        Thread.sleep(100);
                    }
                } catch (InterruptedException e) {

                }
            }
        });
    }

    private void run() throws Exception {

        ClassNamesResourceConfig config = new ClassNamesResourceConfig(HttpControlInterface.class);
        config.getProviderClasses().add(StringProvider.class);


        HttpServer server = HttpServerFactory.create("http://localhost:9998/", config);
        server.start();

        System.out.println("Server running");
        System.out.println("Visit:");
        System.out.println("http://localhost:9998/powerflare/{port:[0-8]}/{state:(ON|OFF)} to change " +
                                   "PowerFlare-Status");
        System.out.println("http://localhost:9998/powerflare/{port:[0-8]}/ to retrieve PowerFlare-Status");
        System.out.println("http://localhost:9998/switch/{port:[0-8]} to switch a Port");
        System.out.println("http://localhost:9998/relay/{port:[0-8]}/{state:(CONNECT|DISCONNECT)} for Low-Level " +
                                   "Relay Control");

        while (active) {
            Thread.sleep(500);
        }


        server.stop(0);
        shutdown = true;
        System.out.println("Server stopped");
    }

    private void close() {

        channel.close();
    }

    private Server(String port) throws Exception {

        if (Boolean.getBoolean(DUMMY_MODE)) {
            channel = new DummyCommChannel();
        } else {
            channel = new SerialPortCommunicationChannel(port, 19200);
        }
        Multiplexer.getInstance().setChannel(channel);
        StateStore.getInstance().initialize(8);
    }

    public static Server getInstance() {
        return instance;
    }

}

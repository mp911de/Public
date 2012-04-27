package de.paluch.powerflare;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.jersey.api.core.ClassNamesResourceConfig;
import com.sun.jersey.core.impl.provider.entity.StringProvider;
import com.sun.net.httpserver.HttpServer;
import de.paluch.powerflare.channel.ICommunicationChannel;
import de.paluch.powerflare.channel.SerialPortCommunicationChannel;

/**
 * Created with IntelliJ IDEA. User: mark Date: 25.04.12 Time: 08:31 To change this template use File | Settings | File
 * Templates.
 */
public class Server {


    private static Server instance;
    private ICommunicationChannel channel;


    public static void main(String args[]) throws Exception {

        if (args.length == 0) {
            System.out.println("Usage: Server port (device path/name)");
            return;
        }
        System.out.println("using port " + args[0]);

        instance = new Server(args[0]);
        instance.run();
        instance.close();
    }

    private void run() throws Exception {

        ClassNamesResourceConfig config = new ClassNamesResourceConfig(HttpControlInterface.class);
        config.getProviderClasses().add(StringProvider.class);


        HttpServer server = HttpServerFactory.create("http://localhost:9998/", config);
        server.start();

        System.out.println("Server running");
        System.out.println("Visit: http://localhost:9998/powerflare/{port:[0-8]}/{state:(ON|OFF)}");
        System.out.println("Hit return to stop...");
        System.in.read();
        System.out.println("Stopping server");
        server.stop(0);
        System.out.println("Server stopped");
    }

    private void close() {

        channel.close();
    }

    public Server(String port) throws Exception {
        channel = new SerialPortCommunicationChannel(port, 19200);
        //channel = new DummyCommChannel();
        Multiplexer.getInstance().setChannel(channel);
    }

    public static Server getInstance() {
        return instance;
    }

}

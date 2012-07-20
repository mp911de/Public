package de.paluch.jira.compare;

import java.util.Arrays;

import org.jboss.resteasy.plugins.server.tjws.TJWSEmbeddedJaxrsServer;

import de.paluch.jira.compare.rest.QueryResource;

/**
 * Server Initiator.
 */
public class Server {

    /**
     * Uses a Dummy-Channel instead real Comm-Channel.
     */

    private static Server instance;

    private boolean active = true;
    private boolean shutdown = false;

    public static void main(String args[]) throws Exception {

        instance = new Server();
        instance.installShutdownHook();
        instance.run();
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

        TJWSEmbeddedJaxrsServer server;

        server = new TJWSEmbeddedJaxrsServer();
        server.setPort(9995);
        server.getDeployment().setResourceClasses(Arrays.asList(QueryResource.class.getName()));
        server.start();

        System.out.println("Server running");
        System.out.println("Visit:");
        System.out.println("http://localhost:9995/query/news/{queryId}/");

        while (active) {
            Thread.sleep(500);
        }

        server.stop();
        shutdown = true;
        System.out.println("Server stopped");
    }

    public static Server getInstance() {
        return instance;
    }

}

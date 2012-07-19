package de.paluch.test;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * <br>
 * <br>
 * Project: diy-mw-core-clients <br>
 * Autor: mark <br>
 * Created: 21.05.2012 <br>
 * <br>
 */
public class RemoteUtil {

    public static int findFreePort() throws IOException {
        ServerSocket server = new ServerSocket(0);
        int port = server.getLocalPort();
        server.close();
        return port;
    }
}

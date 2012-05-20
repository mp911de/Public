package de.paluch.powerflare.state;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: mark Date: 20.05.12 Time: 19:56
 */
public class StateStore {

    private static StateStore instance = new StateStore();
    private Map<Integer, PortState> states = new ConcurrentHashMap<Integer, PortState>();


    public void initialize(int portCount) {
        for (int i = 0; i < portCount; i++) {
            states.put(i, PortState.OFF);
        }
    }

    public static StateStore getInstance() {
        return instance;
    }

    public PortState getState(int port) {
        return states.get(port);
    }

    public void setState(int port, PortState state) {
        states.put(port, state);
    }
}

package de.paluch.powerflare.channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA. User: mark Date: 27.04.12 Time: 08:39 To change this template use File | Settings | File
 * Templates.
 */
public abstract class AbstractCommunicationChannel implements ICommunicationChannel {
    private Map<Integer, Lock> locks = new ConcurrentHashMap<Integer, Lock>();

    @Override
    public void lock(int port) {
        if (port != 0) {
            lockInternal(0);
        }
        lockInternal(port);
    }

    private void lockInternal(int port) {
        if (!locks.containsKey(port)) {
            locks.put(port, new ReentrantLock());
        }

        Lock lock = locks.get(port);
        lock.lock();
    }

    @Override
    public void unlock(int port) {
        if (port != 0) {
            unlockInternal(0);
        }
        unlockInternal(port);
    }

    private void unlockInternal(int port) {
        if (!locks.containsKey(port)) {
            locks.put(port, new ReentrantLock());
        }

        Lock lock = locks.get(port);
        lock.unlock();
    }
}
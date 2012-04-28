package de.paluch.powerflare.channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA. User: mark Date: 27.04.12 Time: 08:39 To change this template use File | Settings | File
 * Templates.
 */
public abstract class AbstractCommunicationChannel implements ICommunicationChannel {
    private final Map<Integer, Mutex> locks = new ConcurrentHashMap<Integer, Mutex>();


    public void lock(Mutex lock, int port) {
        lockInternal(lock, port);
    }

    private void lockInternal(Mutex lock, int port) {
        Mutex otherLock = null;


        do {
            synchronized (locks) {
                otherLock = locks.get(port);
            }
            if (otherLock != null) {
                otherLock.waitForUnlock(lock);
            }
        }
        while (otherLock != null && otherLock != lock);

        synchronized (locks) {
            lock.lock(otherLock);
            locks.put(port, lock);
        }

    }

    public void unlock(Mutex lock, int port) {
        synchronized (locks) {
            unlockInternal(lock, port);
        }

    }

    private void unlockInternal(Mutex lock, int port) {
        Mutex otherLock = locks.get(port);
        lock.unlock(otherLock);
        locks.remove(port);
    }

    public boolean isLocked(int port) {

        if (locks.containsKey(port)) {
            return true;
        }
        return false;
    }

    public void waitForFreeChannel(int port) {

        while (isLocked(port)) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                return;
            }
        }

    }

}
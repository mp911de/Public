package de.paluch.powerflare.channel;

import de.paluch.powerflare.locking.Lock;
import de.paluch.powerflare.locking.LockManager;

/**
 * Created with IntelliJ IDEA. User: mark Date: 27.04.12 Time: 08:39 To change this template use File | Settings | File
 * Templates.
 */
public abstract class AbstractCommunicationChannel implements ICommunicationChannel {

    private LockManager<Integer> lockManager = new LockManager<Integer>();

    @Override
    public void lock(int port, Object owner) {

        if (port != 0) {
            lockManager.lock(0, Lock.LockLevel.shared, owner);
        }

        lockManager.lock(port, Lock.LockLevel.exclusive, owner);

    }

    @Override
    public void unlock(int port, Object owner) {
        if (port != 0) {
            lockManager.unlock(0, owner);
        }

        lockManager.unlock(port, owner);
    }

    @Override
    public void waitForFreeChannel(int port, Object owner) {
        boolean locked;
        do {
            locked = false;
            if (port != 0) {
                if (!lockManager.canLock(0, Lock.LockLevel.shared, owner)) {
                    locked = true;
                }
            }

            if (!lockManager.canLock(port, Lock.LockLevel.exclusive, owner)) {
                locked = true;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                return;
            }

        } while (locked);
    }
}
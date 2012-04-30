package de.paluch.powerflare.locking;

import java.util.*;

/**
 * Created with IntelliJ IDEA. User: mark Date: 29.04.12 Time: 12:34 To change this template use File | Settings | File
 * Templates.
 */
public class LockManager<T> {

    private final Map<T, List<Lock>> locks = new HashMap<T, List<Lock>>();

    public LockManager() {
    }


    public boolean canLock(T resource, Lock.LockLevel level, Object owner) {
        synchronized (locks) {
            return canLockImpl(resource, level, owner);
        }

    }

    private boolean canLockImpl(T resource, Lock.LockLevel level, Object owner) {
        List<Lock> resourceLocks = getLocks(resource);

        for (Lock lock : resourceLocks) {
            if (lock.getOwner() == owner) {
                continue;
            }

            if (lock.getLockLevel().equals(Lock.LockLevel.exclusive) || level.equals(Lock.LockLevel.exclusive)) {
                return false;
            }
        }
        return true;
    }


    public Lock tryLock(T resource, Lock.LockLevel level, Object owner) {

        synchronized (locks) {
            if (canLockImpl(resource, level, owner)) {
                return createOrGetLock(resource, level, owner);
            }
        }

        return null;
    }

    public Lock lock(T resource, Lock.LockLevel level, Object owner) {

        Lock lock = null;

        while ((lock = tryLock(resource, level, owner)) == null) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                return null;
            }
        }

        return lock;
    }

    public void unlock(T resource, Object owner) {
        synchronized (locks) {
            List<Lock> resourceLocks = getLocks(resource);

            List<Lock> toRemove = new ArrayList<Lock>();

            for (Lock lock : resourceLocks) {
                if (lock.getOwner() == owner) {
                    toRemove.add(lock);
                }
            }

            resourceLocks.removeAll(toRemove);

            System.out.println("Unlock " + resource+", locks: " + resourceLocks);
        }

    }

    private Lock createOrGetLock(T resource, Lock.LockLevel level, Object owner) {
        List<Lock> resourceLocks = getLocks(resource);

        for (Lock lock : resourceLocks) {
            if (lock.getOwner() == owner) {
                return lock;
            }
        }

        Lock lock = new Lock(level, owner);
        resourceLocks.add(lock);

        System.out.println("Lock " + resource+", locks: " + resourceLocks);
        return lock;
    }


    private List<Lock> getLocks(T resource) {
        List<Lock> resourceLocks = locks.get(resource);
        if (resourceLocks == null) {
            resourceLocks = new ArrayList<Lock>();
            locks.put(resource, resourceLocks);
        }
        return resourceLocks;
    }
}
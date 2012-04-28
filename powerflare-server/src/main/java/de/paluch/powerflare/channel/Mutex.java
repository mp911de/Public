package de.paluch.powerflare.channel;

/**
 * Created with IntelliJ IDEA. User: mark Date: 28.04.12 Time: 21:44 To change this template use File | Settings | File
 * Templates.
 */
public class Mutex {
    private boolean locked;

    public void lock(Mutex other) {
        waitForUnlock(other);
        locked = true;
    }

    public void waitForUnlock(Mutex other) {
        if (other == null || other == this) {
            return;
        }

        try {
            while (locked) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            return;
        }
    }

    public void unlock(Mutex other) {
        if (other != this) {
            throw new RuntimeException("Unlock from other Mutex not allowed");
        }

        locked = false;
    }

}

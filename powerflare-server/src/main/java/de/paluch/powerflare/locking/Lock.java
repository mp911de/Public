package de.paluch.powerflare.locking;

/**
 * Created with IntelliJ IDEA. User: mark Date: 29.04.12 Time: 12:34 To change this template use File | Settings | File
 * Templates.
 */
public class Lock {
    public enum LockLevel
    {
        shared, exclusive
    }

    private LockLevel lockLevel;
    private Object owner;

    public Lock(LockLevel lockLevel, Object owner) {
        this.lockLevel = lockLevel;
        this.owner = owner;
    }

    public LockLevel getLockLevel() {
        return lockLevel;
    }

    public Object getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append(getClass().getSimpleName());
        sb.append(" [lockLevel=").append(lockLevel);
        sb.append(", owner=").append(owner);
        sb.append(']');
        return sb.toString();
    }
}

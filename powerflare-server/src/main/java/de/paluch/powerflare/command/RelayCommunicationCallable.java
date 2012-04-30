package de.paluch.powerflare.command;

/**
 * Created with IntelliJ IDEA. User: mark Date: 29.04.12 Time: 19:49 To change this template use File | Settings | File
 * Templates.
 */
public abstract class RelayCommunicationCallable extends CommunicationCallable {

    private int port;
    private int delay;
    private boolean unlock;
    private AbstractCommand command;

    protected RelayCommunicationCallable(int port, int delay, boolean unlock) {
        this.port = port;
        this.delay = delay;
        this.unlock = unlock;
    }

    @Override
    public Void call() throws Exception {

        getChannel().lock(port, command);

        sendData(getChannel());

        if (unlock) {
            getChannel().unlock(port, command);
        }

        return null;
    }

    public void setCommand(AbstractCommand command) {
        this.command = command;
    }

    public int getDelay() {
        return delay;
    }

    public int getPort() {
        return port;
    }
}

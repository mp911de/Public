package de.paluch.powerflare;

import de.paluch.powerflare.channel.DummyCommChannel;
import de.paluch.powerflare.command.ConnectDisconnectCommand;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.*;

/**
 * Created with IntelliJ IDEA. User: mark Date: 03.05.12 Time: 17:12 To change this template use File | Settings | File
 * Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConcurrencyTest {

    private DummyCommChannel channel = new DummyCommChannel();

    @Before
    public void before() {
        Multiplexer.getInstance().setChannel(channel);
    }

    @Test(timeout = 20000)
    public void testSendData() throws Exception {

        List<Thread> threads = new ArrayList<Thread>();
        for (int i = 0; i < 30; i++) {

            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {

                        long randomSleep = (long) (Math.random() * 300);
                        Thread.sleep(randomSleep);

                        ConnectDisconnectCommand cd = new ConnectDisconnectCommand(Byte.valueOf("2"));
                        ConnectDisconnectCommand cd2 = new ConnectDisconnectCommand(Byte.valueOf("0"));
                        cd.execute(channel, Multiplexer.getInstance().getExec());
                        cd2.execute(channel, Multiplexer.getInstance().getExec());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };

            threads.add(thread);
        }

        for (Thread t : threads) {
            t.start();
        }
        Thread.sleep(100);

        for (Thread t : threads) {
            if (t.isAlive()) {
                t.join();
            }
        }


        Multiplexer.getInstance().getExec().shutdown();
        while (!Multiplexer.getInstance().getExec().isTerminated()) {
            Thread.sleep(100);
        }


        List<byte[]> history = channel.getHistory();

        boolean wasConnect = false;
        boolean wasDisconnect = false;

        for (byte[] data : history) {

            assertEquals(1, data.length);

            assertTrue(data[0] >= 100 && data[0] <= 120);


            if (data[0] >= 100 && data[0] <= 109) {

                if (wasConnect) {
                    fail("Two following Connects");
                }

                wasConnect = true;
                wasDisconnect = false;
            }

            if (data[0] >= 110 && data[0] <= 119) {

                if (wasDisconnect) {
                    fail("Two following Disconnects");
                }
                wasConnect = false;
                wasDisconnect = true;
            }

        }


    }
}

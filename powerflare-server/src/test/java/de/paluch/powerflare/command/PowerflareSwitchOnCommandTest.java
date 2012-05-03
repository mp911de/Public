package de.paluch.powerflare.command;

import de.paluch.powerflare.channel.ICommunicationChannel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.*;

/**
 * Created with IntelliJ IDEA. User: mark Date: 03.05.12 Time: 08:18 To change this template use File | Settings | File
 * Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class PowerflareSwitchOnCommandTest {
    private PowerflareSwitchOnCommand sut = new PowerflareSwitchOnCommand(Byte.valueOf("2"));

    @Mock
    private ICommunicationChannel channel;

    @Mock
    private ScheduledExecutorService executor;


    @Test
    public void testGetCommunicationCommands() throws Exception {
        List<? extends RelayCommunicationCallable> result = sut.getCommunicationCommands();
        assertEquals(2, result.size());

        RelayCommunicationCallable on = result.get(0);
        RelayCommunicationCallable off = result.get(1);


        assertEquals(0, on.getDelay());
        assertEquals(200, off.getDelay());
    }


    @Test
    public void testSend() throws Exception {
        List<? extends RelayCommunicationCallable> result = sut.getCommunicationCommands();
        assertEquals(2, result.size());

        RelayCommunicationCallable on = result.get(0);
        RelayCommunicationCallable off = result.get(1);

        on.sendData(channel);
        ArgumentCaptor<byte[]> captor = ArgumentCaptor.forClass(byte[].class);

        verify(channel).sendData(captor.capture());

        byte[] data = captor.getValue();

        assertNotNull(data);
        assertEquals(1, data.length);
        assertEquals(102, data[0]);

    }

    @Test
    public void testExecute() throws Exception {
        sut.execute(channel, executor);

        verify(channel).waitForFreeChannel(Byte.valueOf("2"), sut);
        verify(channel, times(3)).lock(Byte.valueOf("2"), sut);

        verify(executor).schedule(any(Callable.class), eq(0L), eq(TimeUnit.MILLISECONDS));
        verify(executor).schedule(any(Callable.class), eq(200L), eq(TimeUnit.MILLISECONDS));

    }

    @Test
    public void testGetPort() throws Exception {
        assertEquals(2, sut.getPort());
    }
}

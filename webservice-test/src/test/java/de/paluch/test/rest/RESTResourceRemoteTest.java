package de.paluch.test.rest;

import java.util.Arrays;
import java.util.List;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.plugins.server.tjws.TJWSEmbeddedJaxrsServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.paluch.test.ICalculator;
import de.paluch.test.RemoteUtil;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RESTResourceRemoteTest {

    private static int port;
    private static TJWSEmbeddedJaxrsServer server;

    @InjectMocks
    private final static TestRESTResource sut = new TestRESTResource();

    @BeforeClass
    public static void beforeClass() throws Exception {

        port = RemoteUtil.findFreePort();

        server = new TJWSEmbeddedJaxrsServer();
        server.setPort(port);
        server.getDeployment().setResources((List) Arrays.asList(sut));
        server.start();

    }

    @AfterClass
    public static void afterClass() throws Exception {
        server.stop();
    }

    @Mock
    private ICalculator calc;

    @Test
    public void testBasicGet() throws Exception {

        ClientRequest request = new ClientRequest("http://localhost:" + port + "/test");
        ClientResponse<String> response = request.get(String.class);
        assertEquals("hello world", response.getEntity());
    }

    @Test
    public void testAddNoMockSetup() throws Exception {

        ClientRequest request = new ClientRequest("http://localhost:" + port + "/test");
        request.formParameter("a", 1).formParameter("b", 2);

        ClientResponse<TestModel> result = request.post(TestModel.class);

        assertEquals(0, result.getEntity().getResult());
    }

    @Test
    public void testAdd() throws Exception {
        when(calc.add(1, 2)).thenReturn(3);
        ClientRequest request = new ClientRequest("http://localhost:" + port + "/test");
        request.formParameter("a", 1).formParameter("b", 2);

        ClientResponse<TestModel> result = request.post(TestModel.class);

        assertEquals(3, result.getEntity().getResult());
    }
}

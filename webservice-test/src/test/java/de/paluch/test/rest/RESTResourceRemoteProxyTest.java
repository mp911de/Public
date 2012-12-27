package de.paluch.test.rest;

import java.util.Arrays;
import java.util.List;

import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.client.cache.CacheFactory;
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

@RunWith(MockitoJUnitRunner.class)
public class RESTResourceRemoteProxyTest {

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

        ITestRESTResource proxy = ProxyFactory.create(ITestRESTResource.class, "http://localhost:" + port);
        CacheFactory.makeCacheable(proxy);
        System.out.println("Result ab: " + proxy.getSummtin("a", "b"));
        System.out.println("Result ab: " + proxy.getSummtin("a", "b"));
        System.out.println("Result ab: " + proxy.getSummtin("a", "b"));
        System.out.println("Result ac: " + proxy.getSummtin("a", "c"));
        System.out.println("Result ax: " + proxy.getSummtin("a", "x"));

    }
}

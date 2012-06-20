package de.paluch.test.soap;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;

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
public class WebServiceRemoteTest {

    private static Endpoint endpoint;
    private static int port;

    @InjectMocks
    private static TestWebService sut = new TestWebService();

    @Mock
    private ICalculator calc;

    @BeforeClass
    public static void beforeClass() throws Exception {
        port = RemoteUtil.findFreePort();
        endpoint = Endpoint.publish("http://localhost:" + port + "/testWebService", sut);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        endpoint.stop();
    }

    @Test
    public void testAddNoMockSetup() throws Exception {

        URL wsdlUrl = new URL("http://localhost:" + port + "/testWebService?wsdl");
        QName serviceName = new QName("http://test.paluch.de/", "TestWebServiceService");
        Service service = Service.create(wsdlUrl, serviceName);

        ITestWebService port = service.getPort(ITestWebService.class);

        int result = port.add(1, 2);
        assertEquals(0, result);
    }

    @Test
    public void testAdd() throws Exception {

        Service service = Service.create(new URL("http://localhost:" + port + "/testWebService?wsdl"), new QName(
                "http://test.paluch.de/", "TestWebServiceService"));

        ITestWebService port = service.getPort(ITestWebService.class);

        when(calc.add(1, 2)).thenReturn(3);
        int result = port.add(1, 2);
        assertEquals(3, result);
    }
}

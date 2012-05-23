package de.paluch.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.when;

/**
 * <br>
 * <br>
 * Project: webservice-test <br>
 * Autor: mark <br>
 * Created: 21.05.2012 <br>
 * <br>
 */
@RunWith(MockitoJUnitRunner.class)
public class TestWebServiceTest {

    @InjectMocks
    private TestWebService sut = new TestWebService();

    @Mock
    private ICalculator calc;

    @Test
    public void testAddNoMockSetup() {
        int result = sut.add(1, 2);
        assertEquals(0, result);
    }

    @Test
    public void testAdd() {
        when(calc.add(1, 2)).thenReturn(3);
        int result = sut.add(1, 2);
        assertEquals(3, result);
    }
}

package de.paluch.test.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.paluch.test.ICalculator;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RESTResourceTest {

    @InjectMocks
    private final TestRESTResource sut = new TestRESTResource();

    @Mock
    private ICalculator calc;

    @Test
    public void testAddNoMockSetup() {
        TestModel result = sut.postAdd(1, 2);
        assertEquals(0, result.getResult());
    }

    @Test
    public void testAdd() {
        when(calc.add(1, 2)).thenReturn(3);
        TestModel result = sut.postAdd(1, 2);
        assertEquals(3, result.getResult());
    }
}

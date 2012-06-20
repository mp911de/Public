package de.paluch.test.soap;

import javax.jws.WebService;

import de.paluch.test.ICalculator;

@WebService(endpointInterface = "de.paluch.test.ITestWebService")
public class TestWebService implements ITestWebService {

    private ICalculator calc;

    @Override
    public int add(int a, int b) {
        return calc.add(a, b);
    }
}
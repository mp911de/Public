package de.paluch.test;

import javax.jws.WebService;

@WebService(endpointInterface = "de.paluch.test.ITestWebService")
public class TestWebService implements ITestWebService {

    private ICalculator calc;

    @Override
    public int add(int a, int b) {
        return calc.add(a, b);
    }
}
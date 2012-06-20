package de.paluch.test.rest;

import de.paluch.test.ICalculator;

public class TestRESTResource implements ITestRESTResource {

    private ICalculator calc;

    /**
     * @see de.paluch.test.rest.ITestRESTResource#getExample()
     */
    @Override
    public String getExample() {
        return "hello world";
    }

    /**
     * @see de.paluch.test.rest.ITestRESTResource#postAdd(int, int)
     */
    @Override
    public TestModel postAdd(int a, int b) {
        int calcResult = calc.add(a, b);
        return new TestModel(calcResult);
    }

}

package de.paluch.test.rest;

import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.cache.Cache;

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

    /**
     * @see de.paluch.test.rest.ITestRESTResource#getSummtin(java.lang.String, java.lang.String)
     */
    @Override
    @Cache(maxAge = 360)
    public Response getSummtin(String id, String q) {

        System.err.println(id + q);
        CacheControl cc = new CacheControl();
        cc.setMaxAge(360);

        return Response.ok(id + q).cacheControl(cc).build();
    }

}

package de.paluch.test;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface ITestWebService {

    /**
     * Adds a plus b and returns the result.
     */
    int add(@WebParam(name = "a") int a, @WebParam(name = "b") int b);
}
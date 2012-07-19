package de.paluch.test.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/test")
public interface ITestRESTResource {

    /**
     * @return "hello world"
     */
    @Produces(MediaType.APPLICATION_XML)
    @GET
    String getExample();

    /**
     * add a plus b and return it.
     * 
     * @param a
     * @param b
     * @return TestModel
     */
    @Produces(MediaType.APPLICATION_XML)
    @POST
    TestModel postAdd(@FormParam("a") int a, @FormParam("b") int b);
}

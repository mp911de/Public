package de.paluch.test.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.cache.Cache;

@Path("/test")
public interface ITestRESTResource {

    /**
     * @return "hello world"
     */
    @Produces(MediaType.APPLICATION_XML)
    @GET
    @Cache(maxAge = 360)
    String getExample();

    /**
     * @return "hello world"
     */
    @Produces(MediaType.APPLICATION_XML)
    @GET
    @Path("{id}")
    Response getSummtin(@PathParam("id") String id, @QueryParam("q") String q);

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

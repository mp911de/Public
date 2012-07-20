package de.paluch.jira.compare.rest;

import java.util.Collections;
import java.util.Set;

import javax.ws.rs.core.Application;

/**
 * Chart Rest-Application (JAX-RS). <br>
 * <br>
 * Project: burdnown-chart <br>
 * Autor: mark <br>
 * Created: 22.03.2012 <br>
 * <br>
 */
public class CompareApplication extends Application {

    /**
	 *
	 */
    public CompareApplication() {

    }

    /**
     * @see javax.ws.rs.core.Application#getClasses()
     */
    @SuppressWarnings("unchecked")
    @Override
    public Set<Class<?>> getClasses() {

        return (Set) Collections.singleton(QueryResource.class);
    }
}

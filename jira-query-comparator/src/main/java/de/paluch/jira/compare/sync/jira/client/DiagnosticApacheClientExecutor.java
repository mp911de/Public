package de.paluch.jira.compare.sync.jira.client;

import java.io.ByteArrayOutputStream;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HttpContext;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.core.executors.ApacheHttpClient4Executor;

/**
 * Customized Diagnostic Client Executor. Stores Request/Response per Thread. This class is derived from
 * ApacheHttpClientExecutor
 * 
 * @author <a href="mailto:mark.paluch@1und1.de">Mark Paluch</a> CHECKSTYLE:OFF NOSONAR
 */
public class DiagnosticApacheClientExecutor extends ApacheHttpClient4Executor {

    /**
     *
     */
    public DiagnosticApacheClientExecutor() {
        super();
    }

    /**
     * @param httpClient
     * @param httpContext
     */
    public DiagnosticApacheClientExecutor(HttpClient httpClient, HttpContext httpContext) {
        super(httpClient, httpContext);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param httpClient
     */
    public DiagnosticApacheClientExecutor(HttpClient httpClient) {
        super(httpClient);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void loadHttpMethod(final ClientRequest request, HttpRequestBase httpMethod) throws Exception {
        super.loadHttpMethod(request, httpMethod);
        if (httpMethod instanceof HttpEntityEnclosingRequestBase) {
            HttpEntityEnclosingRequestBase post = (HttpEntityEnclosingRequestBase) httpMethod;
            if (post.getEntity() != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                post.getEntity().writeTo(baos);
                baos.close();

                System.err.println(baos.toString());
            }
        }
    }

}

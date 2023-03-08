package org.ingi;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.ingi.repository.models.DatabaseCredentails;

import java.util.Map;
import java.util.Properties;

@Path("/admin")
public class CredentialsResource {

    @Inject
    MyCredentialsProvider credentialsProvider;

    @POST
    public String setCredentials(DatabaseCredentails credentials) {
        credentialsProvider.setPassword(credentials.getPassword());
        credentialsProvider.setUserName(credentials.getUser());
        return "Credentials are set";
    }

    @GET
    public Map<String, String>  getCurrentCredentials() {
        return credentialsProvider.getCredentials("my-credentials-provider");
    }
}

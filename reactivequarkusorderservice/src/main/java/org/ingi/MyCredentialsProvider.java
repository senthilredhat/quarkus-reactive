package org.ingi;

import io.quarkus.arc.Unremovable;
import io.quarkus.credentials.CredentialsProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
@Unremovable
@Named("my-credentials-provider")
public class MyCredentialsProvider implements CredentialsProvider {

    @ConfigProperty(name = "database.user")
    private String userName;

    @ConfigProperty(name = "database.password")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public Map<String, String> getCredentials(String credentialsProviderName) {
        Map<String, String> properties = new HashMap<>();
        properties.put(USER_PROPERTY_NAME, userName);
        properties.put(PASSWORD_PROPERTY_NAME, password);
        return properties;
    }


}
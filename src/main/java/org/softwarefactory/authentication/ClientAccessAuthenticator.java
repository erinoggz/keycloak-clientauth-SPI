package org.softwarefactory.keycloak.providers.authentication;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;

import java.util.List;

public class ClientAccessAuthenticator implements Authenticator {
    private static final Logger LOG = Logger.getLogger(ClientAccessAuthenticator.class);

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        UserModel user = context.getUser();
        
        if (user == null) {
            LOG.warn("User is null, failing authentication");
            context.failure(AuthenticationFlowError.UNKNOWN_USER);
            return;
        }
        
        String clientId = context.getAuthenticationSession().getClient().getClientId();
        LOG.info("Checking access for user: " + user.getUsername() + " to client: " + clientId);
        
        // Get allowed clients from user attributes
        List<String> allowedClients = user.getAttributes().get("website");
        LOG.info("User's allowed clients: " + (allowedClients != null ? String.join(", ", allowedClients) : "none"));
        
        if (allowedClients != null && allowedClients.contains(clientId)) {
            LOG.info("Access GRANTED for client: " + clientId);
            context.success();
        } else {
            LOG.warn("Access DENIED for client: " + clientId);
            context.failure(AuthenticationFlowError.ACCESS_DENIED);
        }
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        // Not needed for this authenticator
    }

    @Override
    public boolean requiresUser() {
        return true;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        boolean configured = user != null && user.getAttributes().containsKey("website");
        LOG.info("Checking if configured for user: " + (user != null ? user.getUsername() : "null") + " - " + configured);
        return configured;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
        // Not needed for this authenticator
    }

    @Override
    public void close() {
        // No resources to close
    }
}

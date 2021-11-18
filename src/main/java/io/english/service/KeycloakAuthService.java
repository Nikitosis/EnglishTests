package io.english.service;

import io.english.entity.dao.User;
import io.english.exceptions.InvalidCredentialsException;
import io.english.exceptions.KeycloakException;
import io.english.exceptions.RequestValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.admin.client.token.TokenManager;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class KeycloakAuthService {

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak-auth.adminUsername}")
    private String adminUsername;

    @Value("${keycloak-auth.adminPassword}")
    private String adminPassword;

    private final KeycloakBuilderService keycloakBuilderService;

    public String createNewUser(String email, String password, List<String> roles, Long coreId) {
        log.info("Creating new merchant in keycloak email={}; password={}; roles={}; coreId={}",
                email, password, roles, coreId);

        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setUsername(email);
        userRepresentation.setAttributes(new HashMap<>());
        userRepresentation.getAttributes().put("core_id", Collections.singletonList(String.valueOf(coreId)));

        // Get realm
        UsersResource usersResource = getRealmResource().users();

        // Create user (requires manage-users role)
        Response response = usersResource.create(userRepresentation);
        switch (response.getStatus()) {
            case HttpServletResponse.SC_CREATED:
                break;
            case HttpServletResponse.SC_CONFLICT:
                throw new RequestValidationException(String.format("User with username %s already exists in Keycloak",
                        email));
            default:
                throw new KeycloakException(String.format("Error occur while creating a user in keycloak: %d %s",
                        response.getStatus(), response.getStatusInfo()));
        }

        // Fetch newly created user id
        String keycloakId = CreatedResponseUtil.getCreatedId(response);

        UserResource userResource = usersResource.get(keycloakId);

        // Set user password
        setMerchantPassword(userResource, password);

        // Set user roles
        setMerchantRoles(userResource, roles);

        return keycloakId;
    }

    private void setMerchantPassword(String keycloakUserId, String password) {
        UserResource userResource = getUserResource(keycloakUserId);
        setMerchantPassword(userResource, password);
    }

    private void setMerchantPassword(UserResource userResource, String password) {
        CredentialRepresentation passwordCred = new CredentialRepresentation();
        passwordCred.setTemporary(false);
        passwordCred.setType(CredentialRepresentation.PASSWORD);
        passwordCred.setValue(password);

        // Set password credential
        try {
            userResource.resetPassword(passwordCred);
        } catch (BadRequestException e) {
            throw new RequestValidationException("Incorrect password provided", e);
        }
    }

    public void setMerchantRoles(String keycloakMerchant, List<String> roles) {
        UserResource userResource = getUserResource(keycloakMerchant);
        setMerchantRoles(userResource, roles);
    }

    private void setMerchantRoles(UserResource userResource, List<String> roles) {
        List<RoleRepresentation> roleRepresentations = new ArrayList<>();
        for (String role : roles) {
            RoleRepresentation roleRepresentation = getRealmResource().roles().get(role).toRepresentation();
            roleRepresentations.add(roleRepresentation);
        }

        userResource.roles().realmLevel().add(roleRepresentations);
    }

    public AccessTokenResponse authorizeClient() {
        Keycloak keycloak = keycloakBuilderService.getClientCredentialsKeycloak();
        TokenManager tokenManager = keycloak.tokenManager();
        return tokenManager.getAccessToken();
    }

    public AccessTokenResponse authorize(User user, String password) {
        try {
            return getAccessToken(user.getEmail(), password);
        } catch (NotAuthorizedException e) {
            throw new InvalidCredentialsException("Can't authorize user. Wrong credentials", e);
        } catch (NotFoundException e) {
            throw new KeycloakException("Error occur while creating a user in keycloak", e);
        }
    }

    private AccessTokenResponse getAccessToken(String username, String password) {
        Keycloak keycloak = keycloakBuilderService.getPasswordKeycloak(username, password);
        TokenManager tokenManager = keycloak.tokenManager();
        return tokenManager.getAccessToken();
    }

    private RealmResource getRealmResource() {
        return keycloakBuilderService.getPasswordKeycloak(adminUsername, adminPassword).realm(realm);
    }

    private UserResource getUserResource(String keycloakUserId) {
        return getRealmResource().users().get(keycloakUserId);
    }
}

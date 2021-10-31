package io.english.utils;

import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class PrincipalUtils {

    private final String CORE_ID_CLAIM = "core_id";

    public Long getUserIdFromPrincipal() {
        KeycloakAuthenticationToken principal = getKeycloakToken();

        String userIdStr = Optional.ofNullable(String.valueOf(principal.getAccount().getKeycloakSecurityContext().getToken().getOtherClaims().get(CORE_ID_CLAIM)))
                .orElseThrow(() -> new RuntimeException(String.format("Couldn't fetch user id from keycloak principal %s", principal.getPrincipal())));
        return Long.parseLong(userIdStr);
    }

    private KeycloakAuthenticationToken getKeycloakToken() {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = sra.getRequest();
        return (KeycloakAuthenticationToken) req.getUserPrincipal();
    }

}

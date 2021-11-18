package io.english.controller;

import io.english.entity.dao.User;
import io.english.entity.request.LoginRequest;
import io.english.service.KeycloakAuthService;
import io.english.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    UserService userService;
    KeycloakAuthService keycloakAuthService;

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        User user = userService.getByEmail(loginRequest.getEmail());
        AccessTokenResponse tokenResponse = keycloakAuthService.authorize(user, loginRequest.getPassword());
        return ResponseEntity.ok(tokenResponse);
    }
}

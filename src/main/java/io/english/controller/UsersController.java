package io.english.controller;

import io.english.entity.dao.User;
import io.english.entity.request.UserCreateRequest;
import io.english.entity.response.UserResponse;
import io.english.mappers.UserMapper;
import io.english.service.UserService;
import io.english.utils.PrincipalUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;
    private final PrincipalUtils principalUtils;

    @GetMapping("/search")
    public ResponseEntity<List<UserResponse>> search() {
        List<User> users = userService.getAll();
        List<UserResponse> responses = UserMapper.INSTANCE.toResponses(users);

        return ResponseEntity.ok(responses);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<UserResponse> getUser() {
        Long userId = principalUtils.getUserIdFromPrincipal();
        User user = userService.getById(userId);

        UserResponse response = UserMapper.INSTANCE.toResponse(user);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserCreateRequest request) {
        User user = userService.create(request);
        UserResponse response = UserMapper.INSTANCE.toResponse(user);
        return ResponseEntity.ok(response);
    }
}

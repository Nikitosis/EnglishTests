package io.english.controller;

import io.english.entity.dao.User;
import io.english.entity.response.UserResponse;
import io.english.mappers.UserMapper;
import io.english.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {
        List<User> users = userService.getAll();
        List<UserResponse> responses = UserMapper.INSTANCE.toResponses(users);

        return ResponseEntity.ok(responses);
    }
}

package io.english.controller;

import io.english.entity.dao.EnglishLevel;
import io.english.entity.dao.User;
import io.english.entity.dao.UserType;
import io.english.entity.request.UserCreateRequest;
import io.english.entity.request.UserSearchRequest;
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
    public ResponseEntity<List<UserResponse>> search(@RequestParam(value = "userType", required = false) UserType userType,
                                                     @RequestParam(value = "id", required = false) Long id,
                                                     @RequestParam(value = "teacherId", required = false) Long teacherId,
                                                     @RequestParam(value = "englishLevel", required = false) EnglishLevel englishLevel,
                                                     @RequestParam(value = "isKnowledgeTestPassed", required = false) Boolean isKnowledgeTestPassed,
                                                     @RequestParam(value = "email", required = false) String email,
                                                     @RequestParam(value = "firstName", required = false) String firstName,
                                                     @RequestParam(value = "lastName", required = false) String lastName) {
        UserSearchRequest request = new UserSearchRequest()
                .setUserType(userType).setId(id).setTeacherId(teacherId)
                .setEnglishLevel(englishLevel).setIsKnowledgeTestPassed(isKnowledgeTestPassed)
                .setEmail(email).setFirstName(firstName).setLastName(lastName);

        List<User> users = userService.search(request);
        List<UserResponse> responses = UserMapper.INSTANCE.toResponses(users);

        return ResponseEntity.ok(responses);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<UserResponse> getUser() {
        Long userId = principalUtils.getUserIdFromPrincipal();
        var user = userService.getById(userId);

        UserResponse response = UserMapper.INSTANCE.toResponse(user);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserCreateRequest request) {
        var user = userService.create(request);
        UserResponse response = UserMapper.INSTANCE.toResponse(user);
        return ResponseEntity.ok(response);
    }

    @PutMapping("{id}/teacher")
    public ResponseEntity<UserResponse> assignTeacher(@PathVariable Long id) {
        Long teacherId = principalUtils.getUserIdFromPrincipal();
        User user = userService.assignTeacher(id, teacherId);
        UserResponse response = UserMapper.INSTANCE.toResponse(user);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}/teacher")
    public ResponseEntity<UserResponse> deleteTeacher(@PathVariable Long id) {
        var user = userService.deleteTeacher(id);
        UserResponse response = UserMapper.INSTANCE.toResponse(user);
        return ResponseEntity.ok(response);
    }

    @GetMapping("teachers")
    public ResponseEntity<List<UserResponse>> getTeachers() {
        List<User> users = userService.getTeachers();
        List<UserResponse> responses = UserMapper.INSTANCE.toResponses(users);
        return ResponseEntity.ok(responses);
    }
}

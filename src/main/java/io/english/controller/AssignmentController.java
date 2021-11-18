package io.english.controller;

import io.english.entity.response.UserAvailableAssignmentResponse;
import io.english.service.UserAvailableAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "assignments")
public class AssignmentController {
    private final UserAvailableAssignmentService userAvailableAssignmentService;

    @GetMapping("available")
    public List<UserAvailableAssignmentResponse> getAvailableAssignments() {
        Long userId = 1L;
        return userAvailableAssignmentService.getAvailableAssignmentsByUserId(userId);
    }
}

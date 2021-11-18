package io.english.controller;

import io.english.entity.response.UserAvailableAssignmentResponse;
import io.english.service.UserAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "assignments")
public class AssignmentController {
    private final UserAssignmentService userAvailableAssignmentService;

    @GetMapping("available")
    public List<UserAvailableAssignmentResponse> getAvailableAssignments() {
        return userAvailableAssignmentService.getAvailableAssignments();
    }
}

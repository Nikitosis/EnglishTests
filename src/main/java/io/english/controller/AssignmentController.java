package io.english.controller;

import io.english.entity.dao.UserAssignment;
import io.english.entity.request.ChangeAssignmentIsAvailableRequest;
import io.english.entity.response.UserAssignmentResponse;
import io.english.entity.response.UserAvailableAssignmentResponse;
import io.english.mappers.UserAssignmentMapper;
import io.english.service.UserAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "assignment")
public class AssignmentController {
    private final UserAssignmentService userAssignmentService;

    @GetMapping("available")
    public List<UserAvailableAssignmentResponse> getAvailableAssignments() {
        return userAssignmentService.getAvailableAssignments();
    }

    @PutMapping("{assignmentId}/user/{userId}")
    public UserAssignmentResponse changeAssignmentIsAvailable(
            @RequestBody ChangeAssignmentIsAvailableRequest changeAssignmentIsAvailableRequest,
            @PathVariable Long assignmentId,
            @PathVariable Long userId) {
        UserAssignment userAssignment = userAssignmentService.changeAssignmentIsAvailable(assignmentId, userId, changeAssignmentIsAvailableRequest);
        return UserAssignmentMapper.INSTANCE.toUserAssignmentResponse(userAssignment);
    }
}

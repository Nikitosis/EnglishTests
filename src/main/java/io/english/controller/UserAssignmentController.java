package io.english.controller;

import io.english.entity.dao.UserAssignment;
import io.english.entity.request.ChangeAssignmentIsAvailableRequest;
import io.english.entity.request.UserAnswersRequest;
import io.english.entity.response.UserAnswerResponse;
import io.english.entity.response.UserAssignmentResponse;
import io.english.entity.response.UserAvailableAssignmentResponse;
import io.english.mappers.UserAssignmentMapper;
import io.english.service.UserAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "user-assignment")
public class UserAssignmentController {
    private final UserAssignmentService userAssignmentService;

    @GetMapping("available")
    public List<UserAvailableAssignmentResponse> getAvailableAssignments() {
        List<UserAssignment> userAssignments = userAssignmentService.getAvailableAssignments();
        return UserAssignmentMapper.INSTANCE.toAvailableResponses(userAssignments);
    }

    @PutMapping("{assignmentId}/user/{userId}")
    public UserAssignmentResponse changeAssignmentIsAvailable(
            @RequestBody ChangeAssignmentIsAvailableRequest changeAssignmentIsAvailableRequest,
            @PathVariable Long assignmentId,
            @PathVariable Long userId) {
        var userAssignment = userAssignmentService.changeAssignmentIsAvailable(
                assignmentId,
                userId,
                changeAssignmentIsAvailableRequest);
        return UserAssignmentMapper.INSTANCE.toUserAssignmentResponse(userAssignment);
    }

    @PostMapping("{assignmentId}")
    public UserAnswerResponse checkUserAnswers(@RequestBody UserAnswersRequest userAnswersRequest,
                                               @PathVariable Long assignmentId) {
        var userAssignment = userAssignmentService.checkUserAnswers(userAnswersRequest, assignmentId);
        return UserAssignmentMapper.INSTANCE.toUserAnswerResponse(userAssignment);
    }

    @GetMapping("available/{studentId}")
    public List<UserAvailableAssignmentResponse> getAvailableAssignments(@PathVariable Long studentId) {
        List<UserAssignment> userAssignments = userAssignmentService.getAvailableAssignments(studentId);
        return UserAssignmentMapper.INSTANCE.toAvailableResponses(userAssignments);
    }
}

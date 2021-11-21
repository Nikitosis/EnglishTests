package io.english.service;

import io.english.entity.dao.*;
import io.english.entity.request.ChangeAssignmentIsAvailableRequest;
import io.english.entity.request.UserAnswersRequest;
import io.english.exceptions.EntityNotFoundException;
import io.english.exceptions.InvalidAccessException;
import io.english.repository.UserAssignmentRepository;
import io.english.utils.PrincipalUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserAssignmentService {
    private final UserAssignmentRepository userAssignmentRepository;
    private final AssignmentService assignmentService;
    private final UserService userService;
    private final AssignmentItemAnswerService assignmentItemAnswerService;
    private final PrincipalUtils principalUtils;

    public List<UserAssignment> getAvailableAssignments() {
        Long userId = principalUtils.getUserIdFromPrincipal();
        return userAssignmentRepository.findAllByUserIdAndIsAvailableTrue(userId);
    }

    public UserAssignment changeAssignmentIsAvailable(Long assignmentId, Long userId, ChangeAssignmentIsAvailableRequest changeAssignmentIsAvailableRequest) {
        var assignment = assignmentService.getById(assignmentId);
        Boolean isAvailable = changeAssignmentIsAvailableRequest.getIsAvailable();
        var student = userService.getById(userId);
        Optional<UserAssignment> optionalUserAssignment = userAssignmentRepository.findByAssignmentAndUser(assignment, student);
        UserAssignment userAssignment;
        if (optionalUserAssignment.isEmpty()) {
            userAssignment = createUserAssignment(student, isAvailable, assignment);
        } else {
            userAssignment = updateUserAssignment(optionalUserAssignment.get(), isAvailable);
        }
        return userAssignment;
    }

    private UserAssignment createUserAssignment(User student, Boolean isAvailable, Assignment assignment) {
        return userAssignmentRepository.save(UserAssignment.builder()
                .user(student)
                .isAvailable(isAvailable)
                .status(UserAssignmentStatus.NOT_STARTED)
                .assignment(assignment)
                .build());
    }

    private UserAssignment updateUserAssignment(UserAssignment userAssignment, Boolean isAvailable) {
        Long teacherId = principalUtils.getUserIdFromPrincipal();
        if (!teacherId.equals(userAssignment.getAssignment().getCreatedBy().getId())) {
            throw new InvalidAccessException("Assignment was created by a different user");
        }
        userAssignment.setIsAvailable(isAvailable);
        userAssignmentRepository.save(userAssignment);

        return userAssignment;
    }

    public UserAssignment checkUserAnswers(UserAnswersRequest userAnswersRequest, Long assignmentId) {
        var assignment = assignmentService.getById(assignmentId);
        var user = userService.getCurrentUser();
        var userAssignment = getByAssignmentAndUser(assignment, user);
        int mark = (int) userAnswersRequest.getAnswers().stream()
                .filter(answer -> assignmentItemAnswerService.getById(answer.getAssignmentAnswerId()).getIsCorrectAnswer())
                .count();
        userAssignment.setMark(mark);
        if (assignment.getType().equals(AssignmentType.DEFAULT_KNOWLEDGE_TEST)) {
            var englishLevel = getEnglishLevelFromMarkAndAssignment(mark, assignment);
            user.setEnglishLevel(englishLevel);
        }
        return userAssignment;
    }

    private EnglishLevel getEnglishLevelFromMarkAndAssignment(int mark, Assignment assignment) {
        int numberOfItems = assignment.getAssignmentItems().size();
        double correctPercentage = (double) mark / numberOfItems;
        if (correctPercentage > 0.95) {
            return EnglishLevel.C2;
        } else if (correctPercentage > 0.9) {
            return EnglishLevel.C1;
        } else if (correctPercentage > 0.8) {
            return EnglishLevel.B2;
        } else if (correctPercentage > 0.6) {
            return EnglishLevel.B1;
        } else if (correctPercentage > 0.35) {
            return EnglishLevel.A2;
        } else return EnglishLevel.A1;
    }

    public UserAssignment getByAssignmentAndUser(Assignment assignment, User student) {
        return userAssignmentRepository.findByAssignmentAndUser(assignment, student).orElseThrow(() ->
                new EntityNotFoundException(String.format("Assignment not found by assignment id=%d and user id=%d",
                        assignment.getId(), student.getId())));
    }
}
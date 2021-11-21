package io.english.service;

import io.english.entity.dao.*;
import io.english.entity.request.ChangeAssignmentIsAvailableRequest;
import io.english.entity.request.UserAnswersRequest;
import io.english.exceptions.EntityNotFoundException;
import io.english.exceptions.InvalidAccessException;
import io.english.repository.UserAssignmentRepository;
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

    public UserAssignment changeAssignmentIsAvailable(Long assignmentId, Long userId, ChangeAssignmentIsAvailableRequest changeAssignmentIsAvailableRequest, Long teacherId) {
        var assignment = assignmentService.getById(assignmentId);
        Boolean isAvailable = changeAssignmentIsAvailableRequest.getIsAvailable();
        var student = userService.getById(userId);
        Optional<UserAssignment> optionalUserAssignment = userAssignmentRepository.findByAssignmentAndUser(assignment, student);
        UserAssignment userAssignment;
        if (optionalUserAssignment.isEmpty()) {
            userAssignment = createUserAssignment(student, isAvailable, assignment);
        } else {
            userAssignment = updateUserAssignment(optionalUserAssignment.get(), isAvailable, teacherId);
        }
        return userAssignment;
    }

    private UserAssignment createUserAssignment(User student, Boolean isAvailable, Assignment assignment) {
        var userAssignment = UserAssignment.builder()
                .user(student)
                .isAvailable(isAvailable)
                .status(UserAssignmentStatus.NOT_STARTED)
                .assignment(assignment)
                .build();
        return userAssignmentRepository.save(userAssignment);
    }

    private UserAssignment updateUserAssignment(UserAssignment userAssignment, Boolean isAvailable, Long teacherId) {
        if (!teacherId.equals(userAssignment.getAssignment().getCreatedBy().getId())) {
            throw new InvalidAccessException("Assignment was created by a different user");
        }
        userAssignment.setIsAvailable(isAvailable);
        userAssignmentRepository.save(userAssignment);

        return userAssignment;
    }

    public UserAssignment checkUserAnswers(UserAnswersRequest userAnswersRequest, Long assignmentId, Long userId) {
        var assignment = assignmentService.getById(assignmentId);
        var user = userService.getById(userId);
        var userAssignment = getByAssignmentAndUser(assignment, user);
        int mark = (int) userAnswersRequest.getAnswers().stream()
                .filter(answer -> assignmentItemAnswerService.getById(answer.getAssignmentAnswerId()).getIsCorrectAnswer())
                .count();
        userAssignment.setMark(mark);
        if (assignment.getType().equals(AssignmentType.DEFAULT_KNOWLEDGE_TEST)) {
            var englishLevel = getEnglishLevelFromMarkAndAssignment(mark, assignment);
            userService.assignEnglishLevel(user, englishLevel);
        }
        userAssignmentRepository.save(userAssignment);
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

    public List<UserAssignment> getAvailableAssignments(Long studentId) {
        return userAssignmentRepository.findAllByUserIdAndIsAvailableTrue(studentId);
    }
}
package io.english.service;

import io.english.entity.dao.Assignment;
import io.english.entity.dao.User;
import io.english.entity.dao.UserAssignment;
import io.english.entity.dao.UserAssignmentStatus;
import io.english.entity.request.ChangeAssignmentIsAvailableRequest;
import io.english.entity.response.UserAvailableAssignmentResponse;
import io.english.exceptions.InvalidAccessException;
import io.english.mappers.UserAssignmentMapper;
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
    private final PrincipalUtils principalUtils;

    public List<UserAvailableAssignmentResponse> getAvailableAssignments() {
        Long userId = principalUtils.getUserIdFromPrincipal();
        List<UserAssignment> userAssignments = userAssignmentRepository.findAllByUserIdAndIsAvailableTrue(userId);
        return UserAssignmentMapper.INSTANCE.toAvailableResponses(userAssignments);
    }

    public UserAssignment changeAssignmentIsAvailable(Long assignmentId, Long userId, ChangeAssignmentIsAvailableRequest changeAssignmentIsAvailableRequest) {
        Assignment assignment = assignmentService.findById(assignmentId);
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
}
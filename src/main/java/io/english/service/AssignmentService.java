package io.english.service;

import io.english.entity.dao.Assignment;
import io.english.entity.dao.AssignmentItem;
import io.english.entity.dao.AssignmentType;
import io.english.entity.request.AssignmentRequest;
import io.english.exceptions.EntityNotFoundException;
import io.english.mappers.AssignmentItemMapper;
import io.english.mappers.AssignmentMapper;
import io.english.repository.AssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final UserService userService;

    public Assignment getById(Long id) {
        return assignmentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Assignment not found by id=%d", id)));
    }

    public Assignment createAssignment(AssignmentRequest assignmentRequest) {
        Assignment assignment = AssignmentMapper.INSTANCE.toEntity(assignmentRequest);
        var user = userService.getCurrentUser();
        assignment.setCreatedAt(LocalDateTime.now());
        assignment.setCreatedBy(user);
        assignment.setType(AssignmentType.CUSTOM_TEST);
        return assignmentRepository.save(assignment);
    }

    public Assignment updateAssignment(AssignmentRequest assignmentRequest, Long id) {
        var assignment = getById(id);
        List<AssignmentItem> assignmentItems = AssignmentItemMapper.INSTANCE.toEntity(assignmentRequest.getAssignmentItems());
        assignment.setAssignmentItems(assignmentItems);
        return assignmentRepository.save(assignment);
    }

    public Assignment deleteAssignment(Long id) {
        var assignment = getById(id);
        assignmentRepository.deleteById(id);
        return assignment;
    }
}

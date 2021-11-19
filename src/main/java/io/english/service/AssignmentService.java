package io.english.service;

import io.english.entity.dao.Assignment;
import io.english.exceptions.EntityNotFoundException;
import io.english.repository.AssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;

    public Assignment findById(Long id) {
        return assignmentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Assignment not found by id=%d", id)));
    }
}

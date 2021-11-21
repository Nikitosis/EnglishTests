package io.english.service;

import io.english.entity.dao.AssignmentItemAnswer;
import io.english.exceptions.EntityNotFoundException;
import io.english.repository.AssignmentItemAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignmentItemAnswerService {
    private final AssignmentItemAnswerRepository assignmentItemAnswerRepository;

    public AssignmentItemAnswer getById(Long id){
        return assignmentItemAnswerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("AssignmentItemAnswer not found by id=%d", id)));
    }
}

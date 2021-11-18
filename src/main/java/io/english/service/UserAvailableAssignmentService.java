package io.english.service;

import io.english.entity.dao.UserAvailableAssignment;
import io.english.entity.response.UserAvailableAssignmentResponse;
import io.english.mappers.UserAvailableAssignmentMapper;
import io.english.repository.UserAvailableAssignmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserAvailableAssignmentService {
    private final UserAvailableAssignmentRepository userAvailableAssignmentRepository;
    private final UserAvailableAssignmentMapper userAvailableAssignmentMapper;

    public List<UserAvailableAssignmentResponse> getAvailableAssignmentsByUserId(Long userId) {
        List<UserAvailableAssignment> userAvailableAssignments = userAvailableAssignmentRepository.findAllByUserId(userId);
        return userAvailableAssignmentMapper.toResponses(userAvailableAssignments);
    }

}

package io.english.service;

import io.english.entity.dao.UserAssignment;
import io.english.entity.response.UserAvailableAssignmentResponse;
import io.english.mappers.UserAssignmentMapper;
import io.english.repository.UserAssignmentRepository;
import io.english.utils.PrincipalUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserAssignmentService {
    private final UserAssignmentRepository userAssignmentRepository;
    private final PrincipalUtils principalUtils;

    public List<UserAvailableAssignmentResponse> getAvailableAssignments() {
        Long userId = principalUtils.getUserIdFromPrincipal();
        List<UserAssignment> userAssignments = userAssignmentRepository.findAllByUserIdAndIsAvailableTrue(userId);
        return UserAssignmentMapper.INSTANCE.toAvailableResponses(userAssignments);
    }

}

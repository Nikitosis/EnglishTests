package io.english.mappers;

import io.english.entity.dao.UserAvailableAssignment;
import io.english.entity.response.UserAvailableAssignmentResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserAvailableAssignmentMapper {
    List<UserAvailableAssignmentResponse> toResponses(List<UserAvailableAssignment> userAvailableAssignments);
}

package io.english.mappers;

import io.english.entity.dao.UserAssignment;
import io.english.entity.response.UserAvailableAssignmentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserAssignmentMapper {
    UserAssignmentMapper INSTANCE = Mappers.getMapper(UserAssignmentMapper.class);

    List<UserAvailableAssignmentResponse> toAvailableResponses(List<UserAssignment> userAvailableAssignments);
}

package io.english.mappers;

import io.english.entity.dao.Assignment;
import io.english.entity.request.AssignmentRequest;
import io.english.entity.response.AssignmentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AssignmentMapper {
    AssignmentMapper INSTANCE = Mappers.getMapper(AssignmentMapper.class);

    AssignmentResponse toResponse(Assignment assignment);

    Assignment toEntity(AssignmentRequest assignmentRequest);

    List<AssignmentResponse> toResponses(List<Assignment> assignments);
}

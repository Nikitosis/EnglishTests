package io.english.mappers;

import io.english.entity.dao.Assignment;
import io.english.entity.request.AssignmentRequest;
import io.english.entity.response.AssignmentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AssignmentMapper {
    AssignmentMapper INSTANCE = Mappers.getMapper(AssignmentMapper.class);

    AssignmentResponse toResponse(Assignment assignment);

    Assignment toEntity(AssignmentRequest assignmentRequest);
}

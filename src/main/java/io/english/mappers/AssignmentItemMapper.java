package io.english.mappers;

import io.english.entity.dao.AssignmentItem;
import io.english.entity.request.AssignmentItemRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AssignmentItemMapper {
    AssignmentItemMapper INSTANCE = Mappers.getMapper(AssignmentItemMapper.class);

    List<AssignmentItem> toEntity(List<AssignmentItemRequest> assignmentItemRequests);
}

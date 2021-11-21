package io.english.entity.dao;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AssignmentSearchRequest {
    private Long id;
    private AssignmentType type;
    private Long createdById;
}

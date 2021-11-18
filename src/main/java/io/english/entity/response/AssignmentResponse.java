package io.english.entity.response;

import io.english.entity.dao.AssignmentType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AssignmentResponse {
    private Long id;
    private AssignmentType type;
    private LocalDateTime createdAt;
    private UserResponse createdBy;
}

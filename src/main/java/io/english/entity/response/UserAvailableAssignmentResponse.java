package io.english.entity.response;

import io.english.entity.dao.UserAssignmentStatus;
import lombok.Data;

@Data
public class UserAvailableAssignmentResponse {
    private AssignmentResponse assignment;
    private UserAssignmentStatus status;
}

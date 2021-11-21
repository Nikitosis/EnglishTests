package io.english.entity.request;

import lombok.Data;

import java.util.List;

@Data
public class AssignmentRequest {
    private List<AssignmentItemRequest> assignmentItems;
}

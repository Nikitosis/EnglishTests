package io.english.entity.request;

import io.english.entity.dao.AssignmentItemType;
import lombok.Data;

import java.util.List;

@Data
public class AssignmentItemRequest {
    private String description;
    private List<AssignmentItemAnswerRequest> assignmentItemAnswers;
    private AssignmentItemType type;
}

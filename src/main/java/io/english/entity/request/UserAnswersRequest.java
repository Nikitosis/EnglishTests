package io.english.entity.request;

import lombok.Data;

import java.util.List;

@Data
public class UserAnswersRequest {
    private final Integer assignmentId;
    private List<AnswerRequest> answers;
}

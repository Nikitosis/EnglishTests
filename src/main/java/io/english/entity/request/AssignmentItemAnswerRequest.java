package io.english.entity.request;

import lombok.Data;

@Data
public class AssignmentItemAnswerRequest {
    private final Boolean isCorrectAnswer;
    private final String text;
}

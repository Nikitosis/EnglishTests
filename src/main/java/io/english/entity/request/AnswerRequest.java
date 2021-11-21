package io.english.entity.request;

import lombok.Data;

@Data
public class AnswerRequest {
    private Long assignmentId;
    private Long assignmentAnswerId;
}

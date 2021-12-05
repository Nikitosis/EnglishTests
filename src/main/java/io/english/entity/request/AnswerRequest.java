package io.english.entity.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerRequest {
    private Long assignmentId;
    private Long assignmentAnswerId;
}

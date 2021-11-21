package io.english.entity.response;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class UserAnswerResponse {
    private final Integer mark;
}

package io.english.entity.request;

import io.english.entity.dao.EnglishLevel;
import io.english.entity.dao.Gender;
import io.english.entity.dao.UserType;
import io.english.entity.response.UserResponse;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class UserSearchRequest {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private Long teacherId;

    private UserType userType;

    private EnglishLevel englishLevel;

    private Boolean isKnowledgeTestPassed;
}

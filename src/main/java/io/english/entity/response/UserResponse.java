package io.english.entity.response;

import io.english.entity.dao.EnglishLevel;
import io.english.entity.dao.Gender;
import io.english.entity.dao.User;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
public class UserResponse {
    private Long id;

    private String firstName;

    private String lastName;

    private Gender gender;

    private LocalDateTime birthday;

    private String email;

    private UserResponse teacher;

    private EnglishLevel englishLevel;

    private Boolean isKnowledgeTestPassed;
}

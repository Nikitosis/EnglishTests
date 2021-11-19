package io.english.entity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.english.entity.dao.Gender;
import io.english.entity.dao.UserType;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class UserCreateRequest {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private Gender gender;

    private LocalDateTime birthday;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotNull
    private UserType userType;
}

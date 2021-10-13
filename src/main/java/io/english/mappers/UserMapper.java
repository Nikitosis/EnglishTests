package io.english.mappers;

import io.english.entity.dao.User;
import io.english.entity.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    List<UserResponse> toResponses(List<User> users);
}

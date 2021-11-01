package io.english.service;

import io.english.entity.dao.User;
import io.english.entity.request.UserCreateRequest;
import io.english.exceptions.EntityNotFoundException;
import io.english.exceptions.RequestValidationException;
import io.english.repository.UserRepository;
import io.english.utils.SecurityRoles;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final KeycloakAuthService keycloakAuthService;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User not found by email=%s", email)));
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User not found by id=%d", id)));
    }

    @Transactional
    public User create(UserCreateRequest request) {
        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RequestValidationException(String.format("User with already exists with email=%s", request.getEmail()));
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setGender(request.getGender());
        user.setBirthday(request.getBirthday());
        user.setEmail(request.getEmail());
        user.setIsKnowledgeTestPassed(false);

        userRepository.save(user);

        String keycloakId = keycloakAuthService.createNewUser(request.getEmail(), request.getPassword(),
                Collections.singletonList(SecurityRoles.USER.toString()), user.getId());

        user.setKeycloakId(keycloakId);

        return userRepository.save(user);
    }
}

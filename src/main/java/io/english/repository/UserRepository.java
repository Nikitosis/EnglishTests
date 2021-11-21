package io.english.repository;

import io.english.entity.dao.User;
import io.english.entity.dao.UserType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findAll(Specification<User> specification);
    List<User> findAllByUserType(UserType userType);
}

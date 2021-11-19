package io.english.repository;

import io.english.entity.dao.Assignment;
import io.english.entity.dao.User;
import io.english.entity.dao.UserAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserAssignmentRepository extends JpaRepository<UserAssignment, Long> {
    List<UserAssignment> findAllByUserIdAndIsAvailableTrue(Long userId);

    Optional<UserAssignment> findByAssignmentAndUser(Assignment assignment, User user);
}

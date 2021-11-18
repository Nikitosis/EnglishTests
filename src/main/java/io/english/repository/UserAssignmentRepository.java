package io.english.repository;

import io.english.entity.dao.UserAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAssignmentRepository extends JpaRepository<UserAssignment, Long> {
    List<UserAssignment> findAllByUserIdAndIsAvailableTrue(Long userId);
}

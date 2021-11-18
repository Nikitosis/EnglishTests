package io.english.repository;

import io.english.entity.dao.UserAvailableAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAvailableAssignmentRepository extends JpaRepository<UserAvailableAssignment, Long> {
    List<UserAvailableAssignment> findAllByUserId(Long userId);
}

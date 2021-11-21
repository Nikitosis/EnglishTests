package io.english.repository;

import io.english.entity.dao.Assignment;
import io.english.repository.specification.AssignmentSearchSpecification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findAll(AssignmentSearchSpecification specification);
}

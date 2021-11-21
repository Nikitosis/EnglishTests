package io.english.repository;

import io.english.entity.dao.AssignmentItemAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentItemAnswerRepository extends JpaRepository<AssignmentItemAnswer, Long> {
}

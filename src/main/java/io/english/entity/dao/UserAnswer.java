package io.english.entity.dao;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "user_answer")
public class UserAnswer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_assignment_id", nullable = false)
    private UserAssignment userAssignment;

    @ManyToOne
    @JoinColumn(nullable = false)
    private AssignmentItemAnswer answer;
}

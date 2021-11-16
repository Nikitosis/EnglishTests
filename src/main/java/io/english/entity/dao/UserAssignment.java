package io.english.entity.dao;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "user_available_test")
public class UserAssignment implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserAssignmentStatus status;

    @OneToMany(mappedBy = "userAssignment")
    private List<UserAnswer> userAnswers;

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable;
}

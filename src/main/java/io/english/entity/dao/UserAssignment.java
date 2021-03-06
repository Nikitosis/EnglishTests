package io.english.entity.dao;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_available_test")
public class UserAssignment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

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

    @Column(name = "mark")
    private Integer mark;
}

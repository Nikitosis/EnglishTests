package io.english.entity.dao;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "assignment_item_answer")
public class AssignmentItemAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "assignment_item_id", nullable = false)
    private AssignmentItem assignmentItem;

    @Column(name = "is_correct_answer")
    private Boolean isCorrectAnswer;

    private String text;
}

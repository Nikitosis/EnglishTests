package io.english.entity.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

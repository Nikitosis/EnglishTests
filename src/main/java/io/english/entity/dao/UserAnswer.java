package io.english.entity.dao;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "user_answer")
public class UserAnswer implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "assignment_item_id", nullable = false)
    private AssignmentItem assignmentItem;

    @ManyToOne
    @JoinColumn(nullable = false)
    private AssignmentItemAnswer answer;
}

package io.english.entity.dao;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "assignment_item")
public class AssignmentItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AssignmentItemType type;
}

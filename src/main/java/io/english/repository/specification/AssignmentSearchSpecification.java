package io.english.repository.specification;

import io.english.entity.dao.Assignment;
import io.english.entity.dao.AssignmentSearchRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@AllArgsConstructor
public class AssignmentSearchSpecification implements Specification<Assignment> {
    private AssignmentSearchRequest request;

    @Override
    public Predicate toPredicate(Root<Assignment> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Predicate result = criteriaBuilder.conjunction();

        if (request.getId() != null) {
            result = criteriaBuilder.and(result, criteriaBuilder.equal(root.get("id"), request.getId()));
        }

        if (request.getType() != null) {
            result = criteriaBuilder.and(result, criteriaBuilder.equal(root.get("type"), request.getType()));
        }

        if (request.getCreatedById() != null) {
            result = criteriaBuilder.and(result, criteriaBuilder.equal(root.get("createdBy").get("id"), request.getCreatedById()));
        }

        return result;
    }
}

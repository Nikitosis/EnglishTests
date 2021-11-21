package io.english.repository.specification;

import io.english.entity.dao.User;
import io.english.entity.request.UserSearchRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

@AllArgsConstructor
public class UserSearchSpecification implements Specification<User> {

    private UserSearchRequest request;

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Predicate result = criteriaBuilder.conjunction();

        if (request.getId() != null) {
            result = criteriaBuilder.and(result, criteriaBuilder.equal(root.get("id"), request.getId()));
        }

        if(request.getFirstName() != null) {
            result = criteriaBuilder.and(result, criteriaBuilder.equal(root.get("firstName"), request.getFirstName()));
        }

        if(request.getLastName() != null) {
            result = criteriaBuilder.and(result, criteriaBuilder.equal(root.get("lastName"), request.getLastName()));
        }

        if(request.getEmail() != null) {
            result = criteriaBuilder.and(result, criteriaBuilder.equal(root.get("email"), request.getEmail()));
        }

        if(request.getTeacherId() != null) {
            result = criteriaBuilder.and(result, criteriaBuilder.equal(root.get("teacher").get("id"), request.getTeacherId()));
        }

        if(request.getUserType() != null) {
            result = criteriaBuilder.and(result, criteriaBuilder.equal(root.get("userType"), request.getUserType()));
        }

        if(request.getEnglishLevel() != null) {
            result = criteriaBuilder.and(result, criteriaBuilder.equal(root.get("englishLevel"), request.getEnglishLevel()));
        }

        if(request.getIsKnowledgeTestPassed() != null) {
            result = criteriaBuilder.and(result, criteriaBuilder.equal(root.get("isKnowledgeTestPassed"), request.getIsKnowledgeTestPassed()));
        }

        return result;
    }
}

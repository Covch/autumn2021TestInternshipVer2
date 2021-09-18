package com.game.specification;

import com.game.entity.Player;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

public class PlayerSpecification implements Specification<Player> {

    private SearchCriteria searchCriteria;

    public PlayerSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        String key = searchCriteria.getKey();
        String value = searchCriteria.getValue();
        switch (searchCriteria.getSearchOperation()) {
            case EQUALITY:
                return criteriaBuilder.equal(root.get(key), castToRequiredType(root.get(key).getJavaType(), value));
            case GREATER_THAN:
                return criteriaBuilder.greaterThanOrEqualTo(root.get(key), (Integer) castToRequiredType(root.get(key).getJavaType(), value));
            case LESS_THAN:
                return criteriaBuilder.lessThanOrEqualTo(root.get(key), (Integer) castToRequiredType(root.get(key).getJavaType(), value));
            case GREATER_THAN_DATE:
                return criteriaBuilder.greaterThanOrEqualTo(root.get(key), (Date) castToRequiredType(root.get(key).getJavaType(), value));
            case LESS_THAN_DATE:
                return criteriaBuilder.lessThanOrEqualTo(root.get(key), (Date) castToRequiredType(root.get(key).getJavaType(), value));
            case LIKE:
                return criteriaBuilder.like(root.get(key), "%" + castToRequiredType(root.get(key).getJavaType(), value) + "%");
            default:
                throw new RuntimeException();
        }
    }

    private Object castToRequiredType(Class fieldType, String value) {
        if (fieldType.isAssignableFrom(Integer.class)) {
            return Integer.valueOf(value);
        } else if (Enum.class.isAssignableFrom(fieldType)) {
            return Enum.valueOf(fieldType, value);
        } else if (fieldType.isAssignableFrom(String.class)) {
            return String.valueOf(value);
        } else if (fieldType.isAssignableFrom(Boolean.class)) {
            return Boolean.valueOf(value);
        } else if (fieldType.isAssignableFrom(Date.class)) {
            return new Date(Long.parseLong(value));
        }
        return null;
    }
}

package com.game.specification;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

public class PlayerSpecification implements Specification<Player> {

    private SearchCriteria searchCriteria;

    public SearchCriteria getSearchCriteria() {
        return searchCriteria;
    }

    public PlayerSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Player> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        String key = searchCriteria.getKey();
        String value = searchCriteria.getValue();
        switch (searchCriteria.getSearchOperation()) {
            case EQUALITY:
                switch (key) {
                    case "banned":
                        return criteriaBuilder.equal(root.get(key), Boolean.parseBoolean(value));
                    case "profession":
                        return criteriaBuilder.equal(root.get(key), Profession.valueOf(value));
                    case "race":
                        return criteriaBuilder.equal(root.get(key), Race.valueOf(value));
                }
            case GREATER_THAN:
                return criteriaBuilder.greaterThanOrEqualTo(root.get(key), Integer.parseInt(value));
            case LESS_THAN:
                return criteriaBuilder.lessThanOrEqualTo(root.get(key), Integer.parseInt(value));
            case GREATER_THAN_DATE:
                return criteriaBuilder.greaterThanOrEqualTo(root.get(key), new Date(Long.parseLong(value)));
            case LESS_THAN_DATE:
                return criteriaBuilder.lessThanOrEqualTo(root.get(key), new Date(Long.parseLong(value)));
            case LIKE:
                return criteriaBuilder.like(root.get(key), "%" + value + "%");
            default:
                throw new RuntimeException();
        }
    }


    @Override
    public String toString() {
        return "PlayerSpecification{" +
                "searchCriteria=" + searchCriteria +
                '}';
    }
}

package com.mjc.school.repository.filter.specification;

import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.Comment;
import com.mjc.school.repository.model.Tag;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import javax.persistence.criteria.*;

import static com.mjc.school.repository.filter.specification.SearchFilterConstants.*;
import static com.mjc.school.repository.filter.specification.SearchFilterConstants.MODEL_NAME_FIELD;

public class SearchFilterSpecification<T> implements Specification<T> {

    private static final String PREDICATE_PATTERN = "%";
    private final transient SearchCriteria sc;

    public SearchFilterSpecification(SearchCriteria sc) {
        this.sc = sc;
    }

    @Override
    public Predicate toPredicate(
            @NonNull Root<T> root,
            @NonNull CriteriaQuery<?> query,
            @NonNull CriteriaBuilder criteriaBuilder
    ) {
        String filterKey = sc.filterKey();
        Object value = sc.value();
        return switch (SearchOperation.getSimpleOperation(sc.operation())) {
            case CONTAINS -> criteriaBuilder.like(
                    getExpression(root, filterKey), PREDICATE_PATTERN + value.toString() + PREDICATE_PATTERN);
            case DOES_NOT_CONTAIN -> criteriaBuilder.notLike(
                    getExpression(root, filterKey), PREDICATE_PATTERN + value.toString() + PREDICATE_PATTERN);
            case EQUAL -> criteriaBuilder.equal(getExpression(root, filterKey), value.toString());
            case NOT_EQUAL -> criteriaBuilder.notEqual(getExpression(root, filterKey), value.toString());
            default -> null;
        };
    }

    private Expression<String> getExpression(Root<T> root, String filterKey){
        Expression<String> exp;
        switch (filterKey) {
            case SEARCH_TAG_NAME -> {
                Join<Tag, T> tagJoin = root.join(MODEL_TAG_FIELD);
                exp = tagJoin.get(MODEL_NAME_FIELD);
            }
            case SEARCH_TAG_IDS -> {
                Join<Tag, T> tagJoin = root.join(MODEL_TAG_FIELD);
                exp = tagJoin.get(MODEL_ID_FIELD);
            }
            case SEARCH_COMMENT_CONTENT -> {
                Join<Comment, T> commentJoin = root.join(filterKey);
                exp = commentJoin.get(MODEL_CONTENT_FIELD);
            }
            case SEARCH_AUTHOR_NAME -> {
                Join<Author, T> authorJoin = root.join(filterKey);
                exp = authorJoin.get(MODEL_NAME_FIELD);
            }
            default -> exp = root.get(filterKey);
        }
        return exp;
    }

}

package com.mjc.school.repository.impl;

import com.mjc.school.repository.BaseEntity;
import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.filter.pagination.Page;
import com.mjc.school.repository.filter.sorting.SortOrder;
import com.mjc.school.repository.filter.sorting.Sorting;
import com.mjc.school.repository.filter.specification.EntitySearchSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@NoRepositoryBean
@SuppressWarnings({ "unchecked" })
public class BaseRepositoryImpl<T extends BaseEntity<K>, K> extends SimpleJpaRepository<T, K>
        implements BaseRepository<T, K> {

    @PersistenceContext
    private final EntityManager entityManager;
    private final Class<T> entity;

    @Autowired
    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        entity = getDomainClass();
    }

    @Override
    public Page<T> readAll(EntitySearchSpecification searchSpecification) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entity);
        final Root<T> root = criteriaQuery.from(entity);
        Specification<T> specification = searchSpecification.getSpecification();

        setSearchConditions(specification, criteriaBuilder, criteriaQuery, root);
        setOrder(searchSpecification.getSortingList(), criteriaBuilder, criteriaQuery, root);

        final int page = searchSpecification.getPagination().page();
        final int size = searchSpecification.getPagination().size();
        TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult((page - 1) * size);
        typedQuery.setMaxResults(size);
        return new Page<>(typedQuery.getResultList(), page, countPages(specification, size));
    }

    private void setSearchConditions(Specification<T> searchSpecification,
                                     CriteriaBuilder criteriaBuilder,
                                     CriteriaQuery<?> criteriaQuery,
                                     Root<T> root) {
        if (searchSpecification != null) {
            Predicate predicate = searchSpecification.toPredicate(root, criteriaQuery, criteriaBuilder);
            criteriaQuery.where(predicate);
        }
    }

    private void setOrder(List<Sorting> sortingList, CriteriaBuilder criteriaBuilder,
                          CriteriaQuery<T> criteriaQuery, Root<T> root) {
        List<Order> orders = new ArrayList<>();
        for (Sorting sorting : sortingList) {
            Path<Object> fieldPath = root.get(sorting.field());
            Order order = SortOrder.ASC.equals(sorting.order())
                    ? criteriaBuilder.asc(fieldPath)
                    : criteriaBuilder.desc(fieldPath);
            orders.add(order);
        }
        criteriaQuery.orderBy(orders);
    }

    private int countPages(Specification<T> specification, int pageSize) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        final Root<T> root = countQuery.from(entity);

        setSearchConditions(specification, criteriaBuilder, countQuery, root);

        countQuery.select(criteriaBuilder.count(root));
        Long entityCount = entityManager.createQuery(countQuery).getSingleResult();

        return (entityCount % pageSize == 0)
                ? (int) (entityCount / pageSize)
                : (int) (entityCount / pageSize) + 1;
    }
    @SuppressWarnings("squid:S2077")
    public List<T> readByNewsId(K newsId) {
        String query = "SELECT m FROM " + entity.getSimpleName() + " m INNER JOIN m.news n WHERE n.id =:newsId";
        TypedQuery<T> typedQuery = entityManager.createQuery(query, entity);
        typedQuery.setParameter("newsId", newsId);
        return typedQuery.getResultList();
    }

}

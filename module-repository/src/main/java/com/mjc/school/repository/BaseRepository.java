package com.mjc.school.repository;

import com.mjc.school.repository.filter.pagination.Page;
import com.mjc.school.repository.filter.specification.EntitySearchSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BaseRepository<T extends BaseEntity<K>, K> extends JpaRepository<T, K> {

    Page<T> readAll(EntitySearchSpecification searchSpecification);
    List<T> readByNewsId(K newsId);

}

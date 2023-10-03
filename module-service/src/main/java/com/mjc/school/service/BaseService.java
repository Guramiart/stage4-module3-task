package com.mjc.school.service;

import com.mjc.school.repository.filter.specification.EntitySearchSpecification;
import com.mjc.school.service.dto.query.ResourceSearchDtoRequest;
import com.mjc.school.service.dto.response.PageDtoResponse;
import com.mjc.school.service.filter.ResourceSearchFilter;

public interface BaseService<T, R, K> {

    PageDtoResponse<R> readAll(ResourceSearchDtoRequest searchRequest);

    R readById(K id);

    R create(T createRequest);

    R update(K id, T updateRequest);

    void deleteById(K id);

    default EntitySearchSpecification getEntitySpecification(ResourceSearchFilter resourceSearchFilter) {
        return EntitySearchSpecification.builder()
                .pagination(resourceSearchFilter.pagination())
                .sortingList(resourceSearchFilter.sorting())
                .specification(resourceSearchFilter.searchCriteria(), resourceSearchFilter.dataOption())
                .option(resourceSearchFilter.dataOption())
                .build();
    }

}

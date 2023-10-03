package com.mjc.school.service.dto.query;

import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Request for search, sort and pagination.
 *
 * @param page current page
 * @param size pages count
 * @param sortByAndOrder sort and order field. Value example: name:asc
 * @param searchCriteria search criteria field. Value example name:cn:author
 * @param dataOption conjunction or disjunction of data property.
 */
public record ResourceSearchDtoRequest(
        int page,
        int size,
        List<String> sortByAndOrder,
        List<String> searchCriteria,
        String dataOption) {

    public ResourceSearchDtoRequest(
            @Nullable int page,
            @Nullable int size,
            @Nullable List<String> sortByAndOrder,
            @Nullable List<String> searchCriteria,
            @Nullable String dataOption) {
        this.page = page > 0 ? page : 1;
        this.size = size > 0 ? size : 10;
        this.sortByAndOrder = sortByAndOrder != null ? sortByAndOrder : new ArrayList<>();
        this.searchCriteria = searchCriteria != null ? searchCriteria : new ArrayList<>();
        this.dataOption = dataOption;
    }

}

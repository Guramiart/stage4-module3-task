package com.mjc.school.service.filter;

import com.mjc.school.repository.filter.pagination.Pagination;
import com.mjc.school.repository.filter.sorting.Sorting;
import com.mjc.school.repository.filter.specification.SearchCriteria;

import java.util.List;

public record ResourceSearchFilter(
        Pagination pagination,
        List<Sorting> sorting,
        List<SearchCriteria> searchCriteria,
        String dataOption
) {
}

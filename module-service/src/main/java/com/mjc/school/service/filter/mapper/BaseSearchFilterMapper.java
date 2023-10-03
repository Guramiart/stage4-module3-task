package com.mjc.school.service.filter.mapper;

import com.mjc.school.repository.filter.pagination.Pagination;
import com.mjc.school.repository.filter.sorting.SortOrder;
import com.mjc.school.repository.filter.sorting.Sorting;
import com.mjc.school.repository.filter.specification.SearchCriteria;
import com.mjc.school.service.dto.query.ResourceSearchDtoRequest;
import com.mjc.school.service.filter.ResourceSearchFilter;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class BaseSearchFilterMapper {

    public static final String SORT_AND_FILTER_DELIMITER = ":";
    protected Map<String, String> defaultSortingMap;

    public List<Sorting> getDefaultSorting() {
        return defaultSortingMap.entrySet().stream()
                .map(e -> new Sorting(e.getKey(), SortOrder.valueOf(e.getValue())))
                .toList();
    }

    public abstract ResourceSearchFilter map(ResourceSearchDtoRequest searchFilterRequest);

    protected ResourceSearchFilter createResourceSearchFilter(ResourceSearchDtoRequest searchDtoRequest) {
        Pagination pagination = new Pagination(searchDtoRequest.page(), searchDtoRequest.size());
        List<Sorting> sorting = createSorting(searchDtoRequest.sortByAndOrder());
        List<SearchCriteria> searchCriteriaList = createSearchCriteriaList(searchDtoRequest.searchCriteria());
        return new ResourceSearchFilter(pagination, sorting, searchCriteriaList, searchDtoRequest.dataOption());
    }

    private List<Sorting> createSorting(List<String> sorting) {
        List<Sorting> sortingList = new ArrayList<>();
        if (CollectionUtils.isEmpty(sorting)) {
            return getDefaultSorting();
        }
        for (String sort : sorting) {
            String[] splitSorting = sort.split(SORT_AND_FILTER_DELIMITER);
            sortingList.add(new Sorting(splitSorting[0], SortOrder.valueOf(splitSorting[1].toUpperCase())));
        }
        return sortingList;
    }

    private List<SearchCriteria> createSearchCriteriaList(List<String> searchCriteria) {
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();
        if (CollectionUtils.isEmpty(searchCriteria)) {
            return Collections.emptyList();
        }
        for (String filter : searchCriteria) {
            String[] splitFilter = filter.split(SORT_AND_FILTER_DELIMITER);
            if (splitFilter.length == 3) {
                searchCriteriaList.add(
                        new SearchCriteria(splitFilter[0], splitFilter[1], splitFilter[2])
                );
            }
        }
        return searchCriteriaList;
    }

}

package com.mjc.school.service.filter.mapper;

import com.mjc.school.service.dto.query.ResourceSearchDtoRequest;
import com.mjc.school.service.filter.ResourceSearchFilter;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class NewsSearchFilterMapper extends BaseSearchFilterMapper {

    private static final  String TITLE_FIELD_TO_SORT_BY = "title";
    private static final String TITLE_FIELD_SORT_ORDER = "ASC";
    private static final String CREATED_DATE_FIELD_TO_SORT_BY = "createdDate";
    private static final String CREATED_DATE_FIELD_SORT_ORDER = "ASC";

    @Override
    public ResourceSearchFilter map(ResourceSearchDtoRequest searchFilterRequest) {
        defaultSortingMap = new HashMap<>();
        defaultSortingMap.put(TITLE_FIELD_TO_SORT_BY, TITLE_FIELD_SORT_ORDER);
        defaultSortingMap.put(CREATED_DATE_FIELD_TO_SORT_BY, CREATED_DATE_FIELD_SORT_ORDER);
        return createResourceSearchFilter(searchFilterRequest);
    }
}

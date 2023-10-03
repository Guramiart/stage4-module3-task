package com.mjc.school.service.filter.mapper;

import com.mjc.school.service.dto.query.ResourceSearchDtoRequest;
import com.mjc.school.service.filter.ResourceSearchFilter;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class TagSearchFilterMapper extends BaseSearchFilterMapper {

    private static final String NAME_FIELD_TO_SORT_BY = "name";
    private static final String NAME_FIELD_SORT_ORDER = "ASC";

    @Override
    public ResourceSearchFilter map(ResourceSearchDtoRequest searchFilterRequest) {
        defaultSortingMap = new HashMap<>();
        defaultSortingMap.put(NAME_FIELD_TO_SORT_BY, NAME_FIELD_SORT_ORDER);
        return createResourceSearchFilter(searchFilterRequest);
    }
}

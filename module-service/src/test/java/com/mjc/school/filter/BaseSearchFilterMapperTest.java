package com.mjc.school.filter;

import com.mjc.school.repository.filter.sorting.SortOrder;
import com.mjc.school.service.dto.query.ResourceSearchDtoRequest;
import com.mjc.school.service.filter.ResourceSearchFilter;
import com.mjc.school.service.filter.mapper.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class BaseSearchFilterMapperTest {

    BaseSearchFilterMapper baseSearchFilterMapper;

    @Test
    void shouldMapAuthorSearchRequest() {
        baseSearchFilterMapper = new AuthorSearchFilterMapper();
        ResourceSearchFilter searchFilter = baseSearchFilterMapper.map(
                new ResourceSearchDtoRequest(1, 2, List.of("name:asc"), null, null)
        );
        assertEquals(1, searchFilter.pagination().page());
        assertEquals(2, searchFilter.pagination().size());
        assertEquals("name", searchFilter.sorting().get(0).field());
        assertEquals(SortOrder.ASC, searchFilter.sorting().get(0).order());
    }

    @Test
    void shouldMapNewsSearchRequest() {
        baseSearchFilterMapper = new NewsSearchFilterMapper();
        ResourceSearchFilter searchFilter = baseSearchFilterMapper.map(
                new ResourceSearchDtoRequest(1, 2, List.of("title:asc"), null, null)
        );
        assertEquals(1, searchFilter.pagination().page());
        assertEquals(2, searchFilter.pagination().size());
        assertEquals("title", searchFilter.sorting().get(0).field());
        assertEquals(SortOrder.ASC, searchFilter.sorting().get(0).order());
    }

    @Test
    void shouldMapCommentSearchRequest() {
        baseSearchFilterMapper = new CommentSearchFilterMapper();
        ResourceSearchFilter searchFilter = baseSearchFilterMapper.map(
                new ResourceSearchDtoRequest(1, 2, List.of("content:asc"), null, null)
        );
        assertEquals(1, searchFilter.pagination().page());
        assertEquals(2, searchFilter.pagination().size());
        assertEquals("content", searchFilter.sorting().get(0).field());
        assertEquals(SortOrder.ASC, searchFilter.sorting().get(0).order());
    }

    @Test
    void shouldMapTagSearchRequest() {
        baseSearchFilterMapper = new TagSearchFilterMapper();
        ResourceSearchFilter searchFilter = baseSearchFilterMapper.map(
                new ResourceSearchDtoRequest(1, 2, Collections.emptyList(), null, null)
        );
        assertEquals(1, searchFilter.pagination().page());
        assertEquals(2, searchFilter.pagination().size());
        assertEquals("name", searchFilter.sorting().get(0).field());
        assertEquals(SortOrder.ASC, searchFilter.sorting().get(0).order());
    }
}

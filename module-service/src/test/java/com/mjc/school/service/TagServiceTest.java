package com.mjc.school.service;

import com.mjc.school.repository.impl.NewsRepository;
import com.mjc.school.repository.impl.TagRepository;
import com.mjc.school.repository.model.Tag;
import com.mjc.school.service.dto.request.TagDtoRequest;
import com.mjc.school.service.dto.response.TagDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ServiceErrorCode;
import com.mjc.school.service.filter.mapper.TagSearchFilterMapper;
import com.mjc.school.service.impl.TagService;
import com.mjc.school.service.mapper.TagMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {
    @Mock
    TagRepository repository;
    @Mock
    NewsRepository newsRepository;
    @Spy
    private TagMapper mapper = Mappers.getMapper(TagMapper.class);
    @Spy
    private TagSearchFilterMapper filterMapper;
    private TagService service;
    private Tag tag;

    @BeforeEach
    public void setup() {
        service = new TagService(repository, newsRepository, mapper, filterMapper);
        tag = Tag.builder()
                .id(1L)
                .name("Tag")
                .build();
    }

    @Test
    void shouldMapAuthorModel() {
        TagDtoRequest dto = new TagDtoRequest("Tag");
        assertEquals(tag.getName(), mapper.dtoToModel(dto).getName());
    }

    @Test
    void shouldFindTagByNewsId() {
        final Long newsId = 1L;
        given(newsRepository.existsById(any())).willReturn(true);
        given(repository.readByNewsId(any())).willReturn(List.of(tag));
        final List<TagDtoResponse> expected = service.readByNewsId(newsId);
        assertThat(expected).hasSize(1);
    }

    @Test
    void shouldThrowExceptionWhenNewsIdNotExist() {
        given(newsRepository.existsById(any())).willReturn(false);
        Exception exception = assertThrows(NotFoundException.class, () -> service.readByNewsId(1L));
        String expectedMessage = exception.getMessage();
        assertEquals(String.format(ServiceErrorCode.NEWS_ID_DOES_NOT_EXIST.getErrorMessage(), 1), expectedMessage);
    }

    @Test
    void shouldThrowExceptionWhenTagByNewsIdIsNonExist() {
        given(newsRepository.existsById(any())).willReturn(true);
        given(repository.readByNewsId(any())).willReturn(Collections.emptyList());
        Exception exception = assertThrows(NotFoundException.class, () -> service.readByNewsId(1L));
        String expectedMessage = exception.getMessage();
        assertEquals(String.format(ServiceErrorCode.TAG_DOES_NOT_EXIST_FOR_NEWS_ID.getErrorMessage(), 1), expectedMessage);
    }
}

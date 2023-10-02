package com.mjc.school.service;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.filter.pagination.Page;
import com.mjc.school.repository.filter.specification.EntitySearchSpecification;
import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.Comment;
import com.mjc.school.repository.model.News;
import com.mjc.school.repository.model.Tag;
import com.mjc.school.service.dto.query.ResourceSearchDtoRequest;
import com.mjc.school.service.dto.request.AuthorDtoRequest;
import com.mjc.school.service.dto.request.CommentDtoRequest;
import com.mjc.school.service.dto.request.NewsDtoRequest;
import com.mjc.school.service.dto.request.TagDtoRequest;
import com.mjc.school.service.dto.response.*;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ResourceConflictServiceException;
import com.mjc.school.service.exceptions.ServiceErrorCode;
import com.mjc.school.service.filter.mapper.NewsSearchFilterMapper;
import com.mjc.school.service.impl.NewsService;
import com.mjc.school.service.mapper.*;
import org.hibernate.PersistentObjectException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AbstractServiceTest {

    @Mock
    private BaseRepository<News, Long> newsRepository;
    @InjectMocks
    private NewsService newsService;
    @Spy
    private NewsSearchFilterMapper searchFilterMapper;
    @Spy
    private static BaseMapper<News, NewsDtoRequest, NewsDtoResponse> newsMapper =
            Mappers.getMapper(NewsMapper.class);
    @SpyBean
    private static final BaseMapper<Author, AuthorDtoRequest, AuthorDtoResponse> authorMapper =
            Mappers.getMapper(AuthorMapper.class);
    @SpyBean
    private static final BaseMapper<Tag, TagDtoRequest, TagDtoResponse> tagMapper =
            Mappers.getMapper(TagMapper.class);
    @SpyBean
    private static final BaseMapper<Comment, CommentDtoRequest, CommentDtoResponse> commentMapper =
            Mappers.getMapper(CommentMapper.class);

    private final News news = News.builder()
            .id(1L)
            .title("NewsTitle")
            .content("NewsContent")
            .author(Author.builder().id(1L).build())
            .tags(List.of(Tag.builder().id(1L).build()))
            .build();

    private final NewsDtoRequest request =
            new NewsDtoRequest("NewsTitle", "NewsContent", 1L, List.of(1L), List.of(1L));

    @BeforeAll
    public static void initMappers() {
        ReflectionTestUtils.setField(newsMapper,"authorMapper", authorMapper);
        ReflectionTestUtils.setField(newsMapper,"tagMapper", tagMapper);
        ReflectionTestUtils.setField(newsMapper,"commentMapper", commentMapper);
    }

    @Test
    void shouldReadAllEntities() {
        List<News> newsList = new ArrayList<>();
        newsList.add(this.news);
        newsList.add(this.news);
        newsList.add(this.news);

        given(newsRepository.readAll(any(EntitySearchSpecification.class)))
                .willReturn(new Page<>(newsList, 1, 3));

        PageDtoResponse<NewsDtoResponse> newsWithCriteria = newsService.readAll(
                new ResourceSearchDtoRequest(0, 0, List.of("title:asc"), List.of("title:eq:T"), "all"));
        assertEquals(newsWithCriteria.modelDtoList(), newsMapper.modelListToDto(newsList));
        PageDtoResponse<NewsDtoResponse> newsWithoutCriteria = newsService.readAll(
                new ResourceSearchDtoRequest(0, 0, null, null, null));
        assertEquals(newsWithoutCriteria.modelDtoList(), newsMapper.modelListToDto(newsList));

        given(newsRepository.readAll(any())).willReturn(new Page<>(null, 1, 1));
        PageDtoResponse<NewsDtoResponse> emptyList = newsService.readAll(
                new ResourceSearchDtoRequest(0, 0, null, null, null)
        );
        assertThat(emptyList.modelDtoList()).isEmpty();
    }

    @Test
    void shouldCreateNewEntityAndReturnDto() {
        given(newsMapper.dtoToModel(any(NewsDtoRequest.class))).willReturn(news);
        given(newsRepository.save(news)).willReturn(news);

        NewsDtoResponse newsDto = newsService.create(request);
        assertThat(newsDto).isNotNull();
        assertEquals(newsDto.getTitle(), request.title());
        assertEquals(newsDto.getContent(), request.content());
        assertEquals(newsDto.getAuthorDto().getId(), request.authorId());
        assertEquals(newsDto.getTagsDto().get(0).getId(), request.tagsId().get(0));
    }

    @Test
    void shouldThrowExceptionWhenCreateInvalidEntity() {
        given(newsMapper.dtoToModel(any(NewsDtoRequest.class))).willReturn(news);
        given(newsRepository.save(any())).willThrow(new PersistentObjectException(""));
        Exception exception = assertThrows(ResourceConflictServiceException.class, () -> {
            newsService.create(request);
        });
        String expectedMessage = exception.getMessage();
        assertEquals(String.format(ServiceErrorCode.NEWS_CONFLICT.getErrorMessage(), 1), expectedMessage);
    }

    @Test
    void shouldFindEntityById() {
        final Long id = 1L;
        given(newsRepository.findById(id)).willReturn(Optional.of(news));
        final NewsDtoResponse expected = newsService.readById(id);
        assertThat(expected).isNotNull();
    }

    @Test
    void shouldReturnNotFoundErrorMessage() {
        final Long id = 1L;
        given(newsRepository.findById(id)).willReturn(Optional.empty());
        Exception exception = assertThrows(NotFoundException.class, () -> {
            newsService.readById(1L);
        });
        String expectedMessage = exception.getMessage();
        assertEquals(String.format(ServiceErrorCode.NEWS_ID_DOES_NOT_EXIST.getErrorMessage(), 1), expectedMessage);
    }

    @Test
    void shouldUpdateNews() {
        given(newsMapper.dtoToModel(any(NewsDtoRequest.class))).willReturn(news);
        given(newsRepository.save(news)).willReturn(news);
        given(newsRepository.existsById(any())).willReturn(true);
        news.setContent("UpdatedContent");
        NewsDtoResponse updatedNews = newsService.update(
                1L, new NewsDtoRequest(news.getTitle(), news.getContent(), null, null, null));
        assertThat(updatedNews.getId()).isEqualTo(news.getId());
        assertThat(updatedNews.getContent()).isEqualTo(news.getContent());
    }

    @Test
    void shouldThrowExceptionWhenUpdateNonExistNews() {
        given(newsRepository.existsById(any(Long.class))).willReturn(false);
        Exception exception = assertThrows(NotFoundException.class, () -> {
            newsService.update(1L, request);
        });
        String expectedMessage = exception.getMessage();
        assertEquals(String.format(ServiceErrorCode.NEWS_ID_DOES_NOT_EXIST.getErrorMessage(), 1), expectedMessage);
    }

    @Test
    void shouldDeleteNews() {
        given(newsRepository.existsById(any())).willReturn(true);
        willDoNothing().given(newsRepository).deleteById(any());

        newsService.deleteById(news.getId());
        verify(newsRepository, times(1)).deleteById(news.getId());
    }

    @Test
    void shouldThrowExceptionWhenDeleteNonExistNews() {
        given(newsRepository.existsById(any(Long.class))).willReturn(false);
        Exception exception = assertThrows(NotFoundException.class, () -> {
            newsService.deleteById(1L);
        });
        String expectedMessage = exception.getMessage();
        assertEquals(String.format(ServiceErrorCode.NEWS_ID_DOES_NOT_EXIST.getErrorMessage(), 1), expectedMessage);
    }

}

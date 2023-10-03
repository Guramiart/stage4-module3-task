package com.mjc.school.service;

import com.mjc.school.repository.impl.CommentRepository;
import com.mjc.school.repository.impl.NewsRepository;
import com.mjc.school.repository.model.Comment;
import com.mjc.school.repository.model.News;
import com.mjc.school.service.dto.request.CommentDtoRequest;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ServiceErrorCode;
import com.mjc.school.service.filter.mapper.CommentSearchFilterMapper;
import com.mjc.school.service.impl.CommentService;
import com.mjc.school.service.mapper.CommentMapper;
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
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    @Mock
    private CommentRepository repository;
    @Mock
    private NewsRepository newsRepository;
    private CommentService service;
    @Spy
    private CommentMapper mapper = Mappers.getMapper(CommentMapper.class);
    @Spy
    private CommentSearchFilterMapper filterMapper;
    private Comment comment;

    @BeforeEach
    public void setup() {
        service = new CommentService(repository, newsRepository, mapper, filterMapper);
        comment = Comment.builder()
                .id(1L)
                .content("Comment")
                .news(News.builder().id(1L).build())
                .build();
    }

    @Test
    void shouldFindCommentByNewsId() {
        CommentDtoRequest dtoRequest = new CommentDtoRequest("Comment", 1L);
        given(newsRepository.existsById(1L)).willReturn(true);
        given(repository.readByNewsId(1L)).willReturn(List.of(comment));
        assertThat(service.readByNewsId(1L)).isNotEmpty();
        assertEquals(dtoRequest.content(), service.readByNewsId(1L).get(0).getContent());
        assertEquals(dtoRequest.newsId(), service.readByNewsId(1L).get(0).getNewsId());
    }

    @Test
    void shouldThrowExceptionWhenNewsIdNotExist() {
        given(newsRepository.existsById(2L)).willReturn(false);
        assertThrows(NotFoundException.class, () -> service.readByNewsId(2L));
    }

    @Test
    void shouldThrowExceptionWhenCommentByNewsIdNonExist() {
        given(newsRepository.existsById(3L)).willReturn(true);
        given(repository.readByNewsId(3L)).willReturn(Collections.emptyList());
        Exception exception = assertThrows(NotFoundException.class, () -> service.readByNewsId(3L));
        String expectedMessage = exception.getMessage();
        assertEquals(String.format(ServiceErrorCode.COMMENT_DOES_NOT_EXIST_FOR_NEWS_ID.getErrorMessage(), 3), expectedMessage);
    }
}

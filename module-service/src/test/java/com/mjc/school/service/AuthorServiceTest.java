package com.mjc.school.service;

import com.mjc.school.repository.impl.AuthorRepository;
import com.mjc.school.repository.impl.NewsRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.service.dto.request.AuthorDtoRequest;
import com.mjc.school.service.dto.response.AuthorDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ServiceErrorCode;
import com.mjc.school.service.filter.mapper.AuthorSearchFilterMapper;
import com.mjc.school.service.impl.AuthorService;
import com.mjc.school.service.mapper.AuthorMapper;
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
class AuthorServiceTest {
    @Mock
    private AuthorRepository repository;
    @Mock
    private NewsRepository newsRepository;
    private AuthorService service;
    @Spy
    private AuthorMapper mapper = Mappers.getMapper(AuthorMapper.class);
    @Spy
    private AuthorSearchFilterMapper filterMapper;
    private Author author;

    @BeforeEach
    public void setup() {
        service = new AuthorService(repository, newsRepository, mapper, filterMapper);
        author = Author.builder()
                .id(1L)
                .name("Author")
                .build();
    }

    @Test
    void shouldMapAuthorModel() {
        AuthorDtoRequest dto = new AuthorDtoRequest("Author");
        assertEquals(author.getName(), mapper.dtoToModel(dto).getName());
    }

    @Test
    void shouldFindAuthorByNewsId() {
        final Long newsId = 1L;
        given(newsRepository.existsById(any())).willReturn(true);
        given(repository.readByNewsId(any())).willReturn(List.of(author));
        final AuthorDtoResponse expected = service.readByNewsId(newsId);
        assertThat(expected).isNotNull();
    }

    @Test
    void shouldThrowExceptionWhenNewsIdNotExist() {
        given(newsRepository.existsById(any())).willReturn(false);
        Exception exception = assertThrows(NotFoundException.class, () -> service.readByNewsId(1L));
        String expectedMessage = exception.getMessage();
        assertEquals(String.format(ServiceErrorCode.NEWS_ID_DOES_NOT_EXIST.getErrorMessage(), 1), expectedMessage);
    }

    @Test
    void shouldThrowExceptionWhenAuthorByNewsIdIsNonExist() {
        given(newsRepository.existsById(any())).willReturn(true);
        given(repository.readByNewsId(any())).willReturn(Collections.emptyList());
        Exception exception = assertThrows(NotFoundException.class, () -> service.readByNewsId(1L));
        String expectedMessage = exception.getMessage();
        assertEquals(String.format(ServiceErrorCode.AUTHOR_DOES_NOT_EXIST_FOR_NEWS_ID.getErrorMessage(), 1), expectedMessage);
    }

}

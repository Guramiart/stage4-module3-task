package com.mjc.school.service.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.News;
import com.mjc.school.service.dto.request.AuthorDtoRequest;
import com.mjc.school.service.dto.response.AuthorDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ServiceErrorCode;
import com.mjc.school.service.filter.mapper.AuthorSearchFilterMapper;
import com.mjc.school.service.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mjc.school.service.exceptions.ServiceErrorCode.*;

@Service
public class AuthorService extends AbstractService<AuthorDtoRequest, AuthorDtoResponse, Author, Long> {

    private final BaseRepository<Author, Long> authorRepository;
    private final BaseRepository<News, Long> newsRepository;
    private final BaseMapper<Author, AuthorDtoRequest, AuthorDtoResponse> mapper;

    @Autowired
    public AuthorService(
            BaseRepository<Author, Long> authorRepository,
            BaseRepository<News, Long> newsRepository,
            BaseMapper<Author, AuthorDtoRequest, AuthorDtoResponse> mapper,
            AuthorSearchFilterMapper filterMapper
    ) {
        super(authorRepository, mapper, filterMapper);
        this.authorRepository = authorRepository;
        this.newsRepository = newsRepository;
        this.mapper = mapper;
    }

    @Override
    protected ServiceErrorCode getConflictErrorMessage() {
        return AUTHOR_CONFLICT;
    }

    @Override
    protected ServiceErrorCode getNotFoundMessage() {
        return AUTHOR_ID_DOES_NOT_EXIST;
    }

    public AuthorDtoResponse readByNewsId(Long id) {
        if(!newsRepository.existsById(id)) {
            throw new NotFoundException(String.format(
                    NEWS_ID_DOES_NOT_EXIST.getErrorMessage(), id
            ));
        }
        List<AuthorDtoResponse> authorList = mapper.modelListToDto(authorRepository.readByNewsId(id));
        if(authorList.isEmpty()) {
            throw new NotFoundException(String.format(AUTHOR_DOES_NOT_EXIST_FOR_NEWS_ID.getErrorMessage(), id));
        }
        return authorList.get(0);
    }
}

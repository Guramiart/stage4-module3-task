package com.mjc.school.service.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.Comment;
import com.mjc.school.repository.model.News;
import com.mjc.school.service.dto.request.CommentDtoRequest;
import com.mjc.school.service.dto.response.CommentDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ServiceErrorCode;
import com.mjc.school.service.filter.mapper.CommentSearchFilterMapper;
import com.mjc.school.service.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mjc.school.service.exceptions.ServiceErrorCode.*;

@Service
public class CommentService extends AbstractService<CommentDtoRequest, CommentDtoResponse, Comment, Long> {

    private final BaseRepository<Comment, Long> commentRepository;
    private final BaseRepository<News, Long> newsRepository;
    private final BaseMapper<Comment, CommentDtoRequest, CommentDtoResponse> mapper;

    @Autowired
    public CommentService(
            BaseRepository<Comment, Long> commentRepository,
            BaseRepository<News, Long> newsRepository,
            BaseMapper<Comment, CommentDtoRequest, CommentDtoResponse> mapper,
            CommentSearchFilterMapper filterMapper
    ) {
        super(commentRepository, mapper, filterMapper);
        this.commentRepository = commentRepository;
        this.newsRepository = newsRepository;
        this.mapper = mapper;
    }

    @Override
    protected ServiceErrorCode getConflictErrorMessage() {
        return COMMENT_CONFLICT;
    }

    @Override
    protected ServiceErrorCode getNotFoundMessage() {
        return COMMENT_ID_DOES_NOT_EXIST;
    }

    public List<CommentDtoResponse> readByNewsId(Long id) {
        if(!newsRepository.existsById(id)) {
            throw new NotFoundException(String.format(
                    NEWS_ID_DOES_NOT_EXIST.getErrorMessage(), id
            ));
        }
        List<CommentDtoResponse> commentList = mapper.modelListToDto(commentRepository.readByNewsId(id));
        if(commentList.isEmpty()) {
            throw new NotFoundException(String.format(COMMENT_DOES_NOT_EXIST_FOR_NEWS_ID.getErrorMessage(), id));
        }
        return commentList;
    }
}

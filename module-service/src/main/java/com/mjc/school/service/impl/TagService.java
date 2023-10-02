package com.mjc.school.service.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.News;
import com.mjc.school.repository.model.Tag;
import com.mjc.school.service.dto.request.TagDtoRequest;
import com.mjc.school.service.dto.response.TagDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ServiceErrorCode;
import com.mjc.school.service.filter.mapper.TagSearchFilterMapper;
import com.mjc.school.service.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mjc.school.service.exceptions.ServiceErrorCode.*;

@Service
public class TagService extends AbstractService<TagDtoRequest, TagDtoResponse, Tag, Long> {

    private final BaseRepository<Tag, Long> tagRepository;
    private final BaseRepository<News, Long> newsRepository;
    private final BaseMapper<Tag, TagDtoRequest, TagDtoResponse> mapper;

    @Autowired
    public TagService(BaseRepository<Tag, Long> tagRepository,
                      BaseRepository<News, Long> newsRepository,
                      BaseMapper<Tag, TagDtoRequest, TagDtoResponse> mapper,
                      TagSearchFilterMapper filterMapper
    ) {
        super(tagRepository, mapper, filterMapper);
        this.tagRepository = tagRepository;
        this.newsRepository = newsRepository;
        this.mapper = mapper;
    }

    @Override
    protected ServiceErrorCode getConflictErrorMessage() {
        return TAG_CONFLICT;
    }

    @Override
    protected ServiceErrorCode getNotFoundMessage() {
        return TAG_ID_DOES_NOT_EXIST;
    }

    public List<TagDtoResponse> readByNewsId(Long id) {
        if(!newsRepository.existsById(id)) {
            throw new NotFoundException(String.format(
                    NEWS_ID_DOES_NOT_EXIST.getErrorMessage(), id
            ));
        }
        List<TagDtoResponse> tagList = mapper.modelListToDto(tagRepository.readByNewsId(id));
        if(tagList.isEmpty()) {
            throw new NotFoundException(String.format(TAG_DOES_NOT_EXIST_FOR_NEWS_ID.getErrorMessage(), id));
        }
        return tagList;
    }
}

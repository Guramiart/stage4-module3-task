package com.mjc.school.service.impl;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.News;
import com.mjc.school.service.dto.request.NewsDtoRequest;
import com.mjc.school.service.dto.response.NewsDtoResponse;
import com.mjc.school.service.exceptions.ServiceErrorCode;
import com.mjc.school.service.filter.mapper.NewsSearchFilterMapper;
import com.mjc.school.service.mapper.BaseMapper;
import org.springframework.stereotype.Service;

import static com.mjc.school.service.exceptions.ServiceErrorCode.NEWS_CONFLICT;
import static com.mjc.school.service.exceptions.ServiceErrorCode.NEWS_ID_DOES_NOT_EXIST;

@Service
public class NewsService extends AbstractService<NewsDtoRequest, NewsDtoResponse, News, Long> {

    public NewsService(BaseRepository<News, Long> repository,
                       BaseMapper<News, NewsDtoRequest, NewsDtoResponse> mapper,
                       NewsSearchFilterMapper filterMapper
    ) {
        super(repository, mapper, filterMapper);
    }

    @Override
    protected ServiceErrorCode getConflictErrorMessage() {
        return NEWS_CONFLICT;
    }

    @Override
    protected ServiceErrorCode getNotFoundMessage() {
        return NEWS_ID_DOES_NOT_EXIST;
    }

}

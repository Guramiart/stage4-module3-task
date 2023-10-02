package com.mjc.school.service.dto.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("squid:S107")
public class NewsDtoResponse extends BaseResponseEntity<Long> {

    private final String title;
    private final String content;
    private final LocalDateTime createdDate;
    private final LocalDateTime lastUpdatedDate;
    private final AuthorDtoResponse authorDto;
    private final List<TagDtoResponse> tagsDto;
    private final List<CommentDtoResponse> commentsDto;

    public NewsDtoResponse(
            Long id,
            String title,
            String content,
            LocalDateTime createdDate,
            LocalDateTime lastUpdatedDate,
            AuthorDtoResponse authorDto,
            List<TagDtoResponse> tagsDto,
            List<CommentDtoResponse> commentsDto
    ) {
        super(id);
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.lastUpdatedDate = lastUpdatedDate;
        this.authorDto = authorDto;
        this.tagsDto = tagsDto;
        this.commentsDto = commentsDto;
    }

}

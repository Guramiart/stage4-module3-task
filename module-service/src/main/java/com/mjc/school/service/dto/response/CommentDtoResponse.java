package com.mjc.school.service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(callSuper = false)
public class CommentDtoResponse extends BaseResponseEntity<Long> {

    @Schema(description = "comment content", example = "It's really cute!")
    private final String content;
    @Schema(description = "linked news identifier", example = "7")
    private final Long newsId;
    private final LocalDateTime createdDate;
    private final LocalDateTime lastUpdatedDate;

    public CommentDtoResponse(
            Long id,
            String content,
            Long newsId,
            LocalDateTime createdDate,
            LocalDateTime lastUpdatedDate
    ) {
        super(id);
        this.content = content;
        this.newsId = newsId;
        this.createdDate = createdDate;
        this.lastUpdatedDate = lastUpdatedDate;
    }

}

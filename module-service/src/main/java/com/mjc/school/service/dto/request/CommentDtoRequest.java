package com.mjc.school.service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record CommentDtoRequest(
        @NotNull
        @Size(min = 5, max = 255, message = "Comment content field should have length from 5 to 255 letters")
        @Schema(description = "comment content", example = "It's really cute!")
        String content,
        @Schema(description = "linked news identifier", example = "7")
        Long newsId
) {
}

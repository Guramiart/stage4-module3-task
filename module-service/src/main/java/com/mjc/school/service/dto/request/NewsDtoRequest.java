package com.mjc.school.service.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public record NewsDtoRequest(
        @NotNull
        @Size(min = 5, max = 30, message = "News title field should have length from 5 to 30 letters")
        String title,
        @NotNull
        @Size(min = 5, max = 255, message = "News content field should have length from 5 to 255 letters")
        String content,
        Long authorId,
        List<Long> tagsId,
        List<Long> commentsId
) {
    public NewsDtoRequest {
        if (tagsId == null) {
            tagsId = new ArrayList<>();
        }
        if (commentsId == null) {
            commentsId = new ArrayList<>();
        }
    }
}

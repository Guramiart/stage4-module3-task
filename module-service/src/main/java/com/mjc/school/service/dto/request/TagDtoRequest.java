package com.mjc.school.service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record TagDtoRequest(
        @NotNull
        @Size(min = 3, max = 15, message = "Tag field should have length from 3 to 15 letters")
        @Schema(description = "news tag", example = "Technology")
        String name
) {
}

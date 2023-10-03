package com.mjc.school.service.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public record AuthorDtoRequest(
        @NotNull
        @Size(min = 3, max = 15, message = "Author name field should have length from 3 to 15 letters")
        @Schema(description = "author name", example = "John Orman")
        String name
) {
}

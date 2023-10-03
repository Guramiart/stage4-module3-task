package com.mjc.school.service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class AuthorDtoResponse extends BaseResponseEntity<Long> {
    @Schema(description = "author name", example = "John Orman")
    private final String name;

    public AuthorDtoResponse(Long id, String name) {
        super(id);
        this.name = name;
    }

}

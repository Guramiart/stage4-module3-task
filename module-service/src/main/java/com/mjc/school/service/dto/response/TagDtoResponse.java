package com.mjc.school.service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class TagDtoResponse extends BaseResponseEntity<Long> {

    @Schema(description = "news tag", example = "Technology")
    private final String name;

    public TagDtoResponse(Long id, String name) {
        super(id);
        this.name = name;
    }

}

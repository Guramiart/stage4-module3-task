package com.mjc.school.service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BaseResponseEntity<K> extends RepresentationModel<BaseResponseEntity<K>> {

    @Schema(description = "identifier", example = "7")
    private final K id;

}

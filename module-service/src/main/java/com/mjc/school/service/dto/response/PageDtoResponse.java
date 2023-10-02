package com.mjc.school.service.dto.response;

import java.util.ArrayList;
import java.util.List;

public record PageDtoResponse<T>(
        List<T> modelDtoList,
        int page,
        int size
) {
    public PageDtoResponse {
        if(modelDtoList == null) {
            modelDtoList = new ArrayList<>();
        }
    }
}

package com.mjc.school.web.controller;

import com.mjc.school.service.dto.response.PageDtoResponse;

public interface BaseController<T, R, S, K>  {

    PageDtoResponse<R> readAll(S searchRequest);

    R readById(K id);

    R create(T createRequest);

    R update(K id, T updateRequest);

    void deleteById(K id);

}

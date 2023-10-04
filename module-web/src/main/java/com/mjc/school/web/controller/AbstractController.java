package com.mjc.school.web.controller;

import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.query.ResourceSearchDtoRequest;
import com.mjc.school.service.dto.response.BaseResponseEntity;
import com.mjc.school.service.dto.response.PageDtoResponse;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@SuppressWarnings("unchecked")
public abstract class AbstractController<T, R extends BaseResponseEntity<K>, S, K> implements BaseController<T, R, S, K> {

    private final BaseService<T, R, K> service;

    protected AbstractController(BaseService<T, R, K> service) {
        this.service = service;
    }

    public PageDtoResponse<R> readAll(ResourceSearchDtoRequest searchRequest) {
        PageDtoResponse<R> responses = service.readAll(searchRequest);
        responses.modelDtoList().forEach(t -> t.add(getLink(t.getId())));
        return responses;
    }

    public R readById(K id) {
        R response = service.readById(id);
        response.add(getLink(id));
        return response;
    }

    public R create(T createRequest) {
        R response = service.create(createRequest);
        response.add(getLink(response.getId()));
        return response;
    }

    public R update(K id, T updateRequest) {
        R response = service.update(id, updateRequest);
        response.add(getLink(id));
        return response;
    }

    public void deleteById(K id) {
        service.deleteById(id);
    }

    private Link getLink(K id) {
        return linkTo(methodOn(getClass()).readById(id)).withSelfRel();
    }
}

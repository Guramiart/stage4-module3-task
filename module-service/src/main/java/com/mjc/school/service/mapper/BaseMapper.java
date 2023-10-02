package com.mjc.school.service.mapper;

import java.util.List;

public interface BaseMapper<M, T, R> {

    List<R> modelListToDto(List<M> modelList);

    R modelToDto(M model);

    M dtoToModel(T dto);

}

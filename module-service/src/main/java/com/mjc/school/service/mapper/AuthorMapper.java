package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.Author;
import com.mjc.school.service.dto.request.AuthorDtoRequest;
import com.mjc.school.service.dto.response.AuthorDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper extends BaseMapper<Author, AuthorDtoRequest, AuthorDtoResponse> {

    List<AuthorDtoResponse> modelListToDto(List<Author> modelList);

    AuthorDtoResponse modelToDto(Author model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "news", ignore = true)
    Author dtoToModel(AuthorDtoRequest dto);

}

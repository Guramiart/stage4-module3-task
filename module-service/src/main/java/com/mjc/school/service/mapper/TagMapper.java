package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.Tag;
import com.mjc.school.service.dto.request.TagDtoRequest;
import com.mjc.school.service.dto.response.TagDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper extends BaseMapper<Tag, TagDtoRequest, TagDtoResponse> {

    List<TagDtoResponse> modelListToDto(List<Tag> modelList);

    TagDtoResponse modelToDto(Tag model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "news", ignore = true)
    Tag dtoToModel(TagDtoRequest dto);

}

package com.mjc.school.service.mapper;

import com.mjc.school.repository.impl.NewsRepository;
import com.mjc.school.repository.model.Comment;
import com.mjc.school.service.dto.request.CommentDtoRequest;
import com.mjc.school.service.dto.response.CommentDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = { NewsMapper.class })
public abstract class CommentMapper implements BaseMapper<Comment, CommentDtoRequest, CommentDtoResponse> {

    @Autowired
    protected NewsRepository repository;

    @Mapping(target = "newsId", expression = "java(model.getNews().getId())")
    public abstract CommentDtoResponse modelToDto(Comment model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastUpdatedDate", ignore = true)
    @Mapping(target = "news", expression = "java(repository.getReferenceById(dto.newsId()))")
    public abstract Comment dtoToModel(CommentDtoRequest dto);

}

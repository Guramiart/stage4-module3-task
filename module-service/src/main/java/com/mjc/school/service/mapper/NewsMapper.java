package com.mjc.school.service.mapper;

import com.mjc.school.repository.impl.AuthorRepository;
import com.mjc.school.repository.impl.CommentRepository;
import com.mjc.school.repository.impl.TagRepository;
import com.mjc.school.repository.model.News;
import com.mjc.school.service.dto.request.NewsDtoRequest;
import com.mjc.school.service.dto.response.NewsDtoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = { AuthorMapper.class, TagMapper.class, CommentMapper.class })
public abstract class NewsMapper implements BaseMapper<News, NewsDtoRequest, NewsDtoResponse> {

    @Autowired
    protected AuthorRepository authorRepository;
    @Autowired
    protected TagRepository tagRepository;
    @Autowired
    protected CommentRepository commentRepository;

    @Mapping(source = "author", target = "authorDto")
    @Mapping(source = "tags", target = "tagsDto")
    @Mapping(source = "comments", target = "commentsDto")
    public abstract NewsDtoResponse modelToDto(News model);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastUpdatedDate", ignore = true)
    @Mapping(target = "author", expression = "java(authorRepository.findById(dto.authorId()).get())")
    @Mapping(
            target = "tags",
            expression = "java(dto.tagsId().stream().map(id -> tagRepository.findById(id).get()).toList())"
    )
    @Mapping(
            target = "comments",
            expression = "java(dto.commentsId().stream().map(id -> commentRepository.findById(id).get()).toList())"
    )
    public abstract News dtoToModel(NewsDtoRequest dto);

}

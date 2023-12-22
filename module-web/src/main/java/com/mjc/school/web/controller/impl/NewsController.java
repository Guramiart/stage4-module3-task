package com.mjc.school.web.controller.impl;

import com.mjc.school.web.controller.AbstractController;
import com.mjc.school.web.controller.constants.PathConstants;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.query.ResourceSearchDtoRequest;
import com.mjc.school.service.dto.request.NewsDtoRequest;
import com.mjc.school.service.dto.response.*;
import com.mjc.school.service.impl.AuthorService;
import com.mjc.school.service.impl.CommentService;
import com.mjc.school.service.impl.TagService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = PathConstants.NEWS_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Operations for creating, updating, retrieving and deleting news in the application", tags = "News")
@SuppressWarnings({"unchecked", "rawtypes"})
public class NewsController
        extends AbstractController<NewsDtoRequest, NewsDtoResponse, ResourceSearchDtoRequest, Long> {

    private final BaseService<NewsDtoRequest, NewsDtoResponse, Long> service;
    private final AuthorService authorService;
    private final TagService tagService;
    private final CommentService commentService;

    @Autowired
    public NewsController(BaseService<NewsDtoRequest, NewsDtoResponse, Long> service,
                          AuthorService authorService, TagService tagService,
                          CommentService commentService) {
        super(service);
        this.service = service;
        this.authorService = authorService;
        this.tagService = tagService;
        this.commentService = commentService;
    }

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retrieve list of news", response = NewsDtoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieve list of news"),
            @ApiResponse(code = 401, message = "You aren't authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you're trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you're trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "int", dataTypeClass = Integer.class, paramType = "query", value = "Results of page to retrieve"),
            @ApiImplicitParam(name = "size", dataType = "int", dataTypeClass = Integer.class, paramType = "query", value = "Number of records per page", defaultValue = "10"),
            @ApiImplicitParam(name = "sortByAndOrder", dataType = "string", dataTypeClass = String.class, paramType = "query", value = "Sort by and order (default - title:asc)"),
            @ApiImplicitParam(name = "searchCriteria", dataType = "string", dataTypeClass = String.class, paramType = "query", value = "Search criteria (example: name:cn:author)"),
            @ApiImplicitParam(name = "dataOption", dataType = "string", dataTypeClass = String.class, paramType = "query", value = "Search option (all | any)"),
    })
    public PageDtoResponse<NewsDtoResponse> readAll(ResourceSearchDtoRequest searchRequest) {
        PageDtoResponse<NewsDtoResponse> response = service.readAll(searchRequest);
        response.modelDtoList().forEach(this::mapSelfLinks);
        response.modelDtoList().stream()
                .map(NewsDtoResponse::getTagsDto)
                .forEach(tagDtoResponses -> tagDtoResponses
                        .forEach(t -> t.add(getEntityLinkById(TagController.class, t.getId()))));
        response.modelDtoList().stream()
                .map(NewsDtoResponse::getAuthorDto)
                .forEach(a -> a.add(getEntityLinkById(AuthorController.class, a.getId())));
        response.modelDtoList().stream()
                .map(NewsDtoResponse::getCommentsDto)
                .forEach(commentDtoResponses -> commentDtoResponses
                        .forEach(c -> c.add(getEntityLinkById(CommentController.class, c.getId()))));
        return response;
    }

    @Override
    @GetMapping(value = PathConstants.ID_PATH)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retrieve specific news by provided id", response = NewsDtoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieve specific news by provided id"),
            @ApiResponse(code = 401, message = "You aren't authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you're trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you're trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public NewsDtoResponse readById(@PathVariable Long id) {
        NewsDtoResponse news = service.readById(id);
        mapLinks(news);
        return news;
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create new news", response = NewsDtoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully create new news"),
            @ApiResponse(code = 401, message = "You aren't authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you're trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you're trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public NewsDtoResponse create(@RequestBody @Valid NewsDtoRequest createRequest) {
        NewsDtoResponse news = service.create(createRequest);
        mapLinks(news);
        return news;
    }

    @Override
    @Secured(value = { "ROLE_ADMIN" })
    @PatchMapping(value = PathConstants.ID_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update specific news by provided id", response = NewsDtoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully update specific news by id"),
            @ApiResponse(code = 401, message = "You aren't authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you're trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you're trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public NewsDtoResponse update(@PathVariable Long id, @RequestBody @Valid NewsDtoRequest updateRequest) {
        NewsDtoResponse news = service.update(id, updateRequest);
        mapLinks(news);
        return news;
    }

    @Override
    @Secured(value = { "ROLE_ADMIN" })
    @DeleteMapping(value = PathConstants.ID_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete specific news by provided id", response = TagDtoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully delete specific news by id"),
            @ApiResponse(code = 401, message = "You aren't authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you're trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you're trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public void deleteById(@PathVariable Long id) {
        super.deleteById(id);
    }

    @GetMapping(value = PathConstants.ID_PATH + "/authors")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get author by provided news id", response = AuthorDtoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully get specific author by news id"),
            @ApiResponse(code = 401, message = "You aren't authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you're trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you're trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public AuthorDtoResponse readAuthorByNewsId(@PathVariable Long id) {
        AuthorDtoResponse response = authorService.readByNewsId(id);
        response.add(getEntityLinkById(AuthorController.class, id));
        return response;
    }

    @GetMapping(value = PathConstants.ID_PATH + "/tags")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get tags by provided news id", response = TagDtoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully get specific tags by news id"),
            @ApiResponse(code = 401, message = "You aren't authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you're trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you're trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public List<TagDtoResponse> readTagsByNewsId(@PathVariable Long id) {
        List<TagDtoResponse> response = tagService.readByNewsId(id);
        response.forEach(r -> r.add(getEntityLinkById(TagController.class, id)));
        return response;
    }

    @GetMapping(value = PathConstants.ID_PATH + "/comments")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get comments by provided news id", response = CommentDtoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully get specific comments by news id"),
            @ApiResponse(code = 401, message = "You aren't authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you're trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you're trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public List<CommentDtoResponse> readCommentsByNewsId(@PathVariable Long id) {
        List<CommentDtoResponse> response = commentService.readByNewsId(id);
        response.forEach(r -> r.add(getEntityLinkById(CommentController.class, id)));
        return response;
    }

    private void mapSelfLinks(NewsDtoResponse response) {
        response.add(linkTo(methodOn(NewsController.class).readById(response.getId())).withSelfRel());
        response.add(linkTo(methodOn(NewsController.class).readAuthorByNewsId(response.getId())).withRel("author"));
        response.add(linkTo(methodOn(NewsController.class).readTagsByNewsId(response.getId())).withRel("tags"));
        response.add(linkTo(methodOn(NewsController.class).readCommentsByNewsId(response.getId())).withRel("comments"));
    }

    private void mapLinks(NewsDtoResponse response) {
        mapSelfLinks(response);
        response.getAuthorDto().add(getEntityLinkById(AuthorController.class ,response.getAuthorDto().getId()));
        response.getTagsDto().forEach(t -> t.add(getEntityLinkById(TagController.class, t.getId())));
        response.getCommentsDto().forEach(c -> c.add(getEntityLinkById(CommentController.class, c.getId())));
    }

    private Link getEntityLinkById(Class<? extends AbstractController> clazz, Long id) {
        return linkTo(methodOn(clazz).readById(id)).withSelfRel();
    }
}

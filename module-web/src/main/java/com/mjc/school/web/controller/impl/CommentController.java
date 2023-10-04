package com.mjc.school.web.controller.impl;

import com.mjc.school.web.controller.AbstractController;
import com.mjc.school.web.controller.PathConstants;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.query.ResourceSearchDtoRequest;
import com.mjc.school.service.dto.request.CommentDtoRequest;
import com.mjc.school.service.dto.response.CommentDtoResponse;
import com.mjc.school.service.dto.response.PageDtoResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = PathConstants.COMMENT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Operations for creating, updating, retrieving and deleting news comments in the application", tags = "Comments")
public class CommentController
        extends AbstractController<CommentDtoRequest, CommentDtoResponse, ResourceSearchDtoRequest, Long> {

    @Autowired
    public CommentController(BaseService<CommentDtoRequest, CommentDtoResponse, Long> service) {
        super(service);
    }

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retrieve list of news comments", response = CommentDtoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieve list of news comments"),
            @ApiResponse(code = 401, message = "You aren't authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you're trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you're trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "int", paramType = "query", value = "Results of page to retrieve"),
            @ApiImplicitParam(name = "size", dataType = "int", paramType = "query", value = "Number of records per page", defaultValue = "10"),
            @ApiImplicitParam(name = "sortByAndOrder", dataType = "string", paramType = "query", value = "Sort by and order (default - content:asc)"),
            @ApiImplicitParam(name = "searchCriteria", dataType = "string", paramType = "query", value = "Search criteria (example: name:cn:author)"),
            @ApiImplicitParam(name = "dataOption", dataType = "string", paramType = "query", value = "Search option (all | any)"),
    })
    public PageDtoResponse<CommentDtoResponse> readAll(final ResourceSearchDtoRequest searchRequest) {
        return super.readAll(searchRequest);
    }

    @Override
    @GetMapping(value = PathConstants.ID_PATH)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retrieve specific news comment by provided id", response = CommentDtoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieve specific news comment by provided id"),
            @ApiResponse(code = 401, message = "You aren't authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you're trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you're trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public CommentDtoResponse readById(@PathVariable Long id) {
        return super.readById(id);
    }

    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create new news comment", response = CommentDtoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully create new news comment"),
            @ApiResponse(code = 401, message = "You aren't authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you're trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you're trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public CommentDtoResponse create(@RequestBody @Valid CommentDtoRequest createRequest) {
        return super.create(createRequest);
    }

    @Override
    @PatchMapping(value = PathConstants.ID_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update specific news comment by provided id", response = CommentDtoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully update specific news comment by id"),
            @ApiResponse(code = 401, message = "You aren't authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you're trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you're trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public CommentDtoResponse update(@PathVariable Long id, @RequestBody @Valid CommentDtoRequest updateRequest) {
        return super.update(id, updateRequest);
    }

    @Override
    @DeleteMapping(value = PathConstants.ID_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete specific news comment by provided id", response = CommentDtoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully delete specific news comment by id"),
            @ApiResponse(code = 401, message = "You aren't authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you're trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you're trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public void deleteById(@PathVariable Long id) {
        super.deleteById(id);
    }

}

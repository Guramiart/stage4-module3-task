package com.mjc.school.web.controller.impl;

import com.mjc.school.web.controller.AbstractController;
import com.mjc.school.web.controller.constants.PathConstants;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.query.ResourceSearchDtoRequest;
import com.mjc.school.service.dto.request.TagDtoRequest;
import com.mjc.school.service.dto.response.PageDtoResponse;
import com.mjc.school.service.dto.response.TagDtoResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = PathConstants.TAG_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Operations for creating, updating, retrieving and deleting tags in the application", tags = "Tags")
public class TagController extends AbstractController<TagDtoRequest, TagDtoResponse, ResourceSearchDtoRequest, Long> {

    @Autowired
    public TagController(BaseService<TagDtoRequest, TagDtoResponse, Long> service) {
        super(service);
    }

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retrieve list of tags", response = TagDtoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieve list of tags"),
            @ApiResponse(code = 401, message = "You aren't authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you're trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you're trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "int", dataTypeClass = Integer.class, paramType = "query", value = "Results of page to retrieve"),
            @ApiImplicitParam(name = "size", dataType = "int", dataTypeClass = Integer.class, paramType = "query", value = "Number of records per page", defaultValue = "10"),
            @ApiImplicitParam(name = "sortByAndOrder", dataType = "string", dataTypeClass = String.class, paramType = "query", value = "Sort by and order (default - name:asc)"),
            @ApiImplicitParam(name = "searchCriteria", dataType = "string", dataTypeClass = String.class, paramType = "query", value = "Search criteria (example: name:cn:author)"),
            @ApiImplicitParam(name = "dataOption", dataType = "string", dataTypeClass = String.class, paramType = "query", value = "Search option (all | any)"),
    })
    public PageDtoResponse<TagDtoResponse> readAll(final ResourceSearchDtoRequest searchRequest) {
        return super.readAll(searchRequest);
    }

    @Override
    @GetMapping(value = PathConstants.ID_PATH)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Retrieve specific tag by provided id", response = TagDtoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieve specific tag by provided id"),
            @ApiResponse(code = 401, message = "You aren't authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you're trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you're trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public TagDtoResponse readById(@PathVariable Long id) {
        return super.readById(id);
    }

    @Override
    @Secured(value = { "ROLE_ADMIN" })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create new tag", response = TagDtoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully create new tag"),
            @ApiResponse(code = 401, message = "You aren't authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you're trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you're trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public TagDtoResponse create(@RequestBody @Valid TagDtoRequest createRequest) {
        return super.create(createRequest);
    }

    @Override
    @Secured(value = { "ROLE_ADMIN" })
    @PatchMapping(value = PathConstants.ID_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update specific tag by provided id", response = TagDtoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully update specific tag by id"),
            @ApiResponse(code = 401, message = "You aren't authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you're trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you're trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public TagDtoResponse update(@PathVariable Long id, @RequestBody @Valid TagDtoRequest updateRequest) {
        return super.update(id, updateRequest);
    }

    @Override
    @Secured(value = { "ROLE_ADMIN" })
    @DeleteMapping(value = PathConstants.ID_PATH)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete specific tag by provided id", response = TagDtoResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully delete specific tag by id"),
            @ApiResponse(code = 401, message = "You aren't authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you're trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you're trying to reach is not found"),
            @ApiResponse(code = 500, message = "Application failed to process the request")
    })
    public void deleteById(@PathVariable Long id) {
        super.deleteById(id);
    }
}

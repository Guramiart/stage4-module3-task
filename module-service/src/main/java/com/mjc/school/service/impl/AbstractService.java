package com.mjc.school.service.impl;

import com.mjc.school.repository.BaseEntity;
import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.filter.pagination.Page;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.dto.query.ResourceSearchDtoRequest;
import com.mjc.school.service.dto.response.PageDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.exceptions.ResourceConflictServiceException;
import com.mjc.school.service.exceptions.ServiceErrorCode;
import com.mjc.school.service.filter.ResourceSearchFilter;
import com.mjc.school.service.filter.mapper.BaseSearchFilterMapper;
import com.mjc.school.service.mapper.BaseMapper;
import org.hibernate.PersistentObjectException;

import javax.validation.ConstraintViolationException;
import java.util.List;

public abstract class AbstractService<T, R, M extends BaseEntity<K>, K> implements BaseService<T, R, K> {
    private final BaseRepository<M, K> repository;
    private final BaseMapper<M, T, R> mapper;
    private final BaseSearchFilterMapper filterMapper;

    protected AbstractService(
            BaseRepository<M, K> repository,
            BaseMapper<M, T, R> mapper,
            BaseSearchFilterMapper filterMapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.filterMapper = filterMapper;
    }

    protected abstract ServiceErrorCode getConflictErrorMessage();
    protected abstract ServiceErrorCode getNotFoundMessage();

    @Override
    public PageDtoResponse<R> readAll(ResourceSearchDtoRequest searchRequest) {
        final ResourceSearchFilter searchFilter = filterMapper.map(searchRequest);
        final Page<M> page = repository.readAll(getEntitySpecification(searchFilter));
        final List<R> modelDtoList = mapper.modelListToDto(page.entities());
        return new PageDtoResponse<>(modelDtoList, page.currentPage(), page.pageCount());
    }

    @Override
    public R readById(K id) {
        return repository.findById(id)
                .map(mapper::modelToDto)
                .orElseThrow(() -> {
                    throw new NotFoundException(String.format(getNotFoundMessage().getErrorMessage(), id));
                });
    }

    @Override
    public R create(T createRequest) {
        try {
            M model = repository.save(mapper.dtoToModel(createRequest));
            return mapper.modelToDto(model);
        } catch (PersistentObjectException | ConstraintViolationException ex) {
            throw new ResourceConflictServiceException(
                    getConflictErrorMessage().getErrorMessage(),
                    getConflictErrorMessage().getErrorCode(),
                    ex.getMessage()
            );
        }
    }

    @Override
    public R update(K id, T updateRequest) {
        if(repository.existsById(id)) {
            M model = mapper.dtoToModel(updateRequest);
            model.setId(id);
            return mapper.modelToDto(repository.save(model));
        }
        throw new NotFoundException(String.format(getNotFoundMessage().getErrorMessage(), id));
    }

    @Override
    public void deleteById(K id) {
        if(repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new NotFoundException(String.format(getNotFoundMessage().getErrorMessage(), id));
        }
    }


}

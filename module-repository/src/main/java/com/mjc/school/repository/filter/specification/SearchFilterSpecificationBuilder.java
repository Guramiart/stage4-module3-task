package com.mjc.school.repository.filter.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchFilterSpecificationBuilder<T> {

    private final List<SearchCriteria> criteriaList;
    private final String dataOption;

    public SearchFilterSpecificationBuilder(String dataOption) {
        criteriaList = new ArrayList<>();
        this.dataOption = dataOption;
    }

    public SearchFilterSpecificationBuilder<T> with(final List<SearchCriteria> searchCriteriaList) {
        if (!CollectionUtils.isEmpty(searchCriteriaList)) {
            criteriaList.addAll(searchCriteriaList);
        }
        return this;
    }

    public final SearchFilterSpecificationBuilder<T> with(SearchCriteria searchCriteria){
        criteriaList.add(searchCriteria);
        return this;
    }

    public Specification<T> build(){
        if(criteriaList.isEmpty()){
            return null;
        }

        List<SearchFilterSpecification<T>> specs = criteriaList.stream()
                .map(SearchFilterSpecification<T>::new).toList();

        Specification<T> result = specs.get(0);

        for (int i = 1; i < criteriaList.size(); i++){
            result =  SearchOperation.getDataOption(dataOption) == SearchOperation.ALL
                    ? Specification.where(result).and(specs.get(i))
                    : Specification.where(result).or(specs.get(i));
        }
        return result;
    }
}

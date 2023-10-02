package com.mjc.school.repository.filter.specification;

import com.mjc.school.repository.filter.pagination.Pagination;
import com.mjc.school.repository.filter.sorting.Sorting;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("rawtypes")
public class EntitySearchSpecification {

    private Pagination pagination;
    private List<Sorting> sortingList;
    private Specification specification;
    private String option;

    public static class EntitySearchSpecificationBuilder {
        public EntitySearchSpecificationBuilder specification(List<SearchCriteria> searchCriteria, String option) {
            this.specification = new SearchFilterSpecificationBuilder<>(option)
                    .with(searchCriteria)
                    .build();
            return this;
        }
    }

}

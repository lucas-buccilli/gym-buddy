package com.example.gymbuddy.infrastructure.models;

import com.example.gymbuddy.implementation.database.specificationBuilders.SpecificationBuilder;
import com.example.gymbuddy.infrastructure.models.enums.SortOptions;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Data
public class PageRequest {
    @NotNull
    private Integer pageNumber;
    @NotNull
    private Integer pageSize;
    @NotNull
    private Map<String, SortOptions> sort = new HashMap<>();
    @NotNull
    private Map<String, String> filter = new HashMap<>();

    public static PageRequest build(Integer pageNumber, Integer pageSize, Map<String, SortOptions> sort, Map<String, String> filter) {
        var pr = new PageRequest();
        pr.setPageNumber(pageNumber);
        pr.setPageSize(pageSize);
        pr.setSort(sort);
        pr.setFilter(filter);
        return pr;
    }

    public Pageable toPageable() {
        var sortOptional = toSort();
        return sortOptional
                .map(sort -> org.springframework.data.domain.PageRequest.of(pageNumber, pageSize).withSort(sort))
                .orElseGet(() -> org.springframework.data.domain.PageRequest.of(pageNumber, pageSize));
    }

    public <K> Specification<K> toSpecification() {
        var builder = new SpecificationBuilder<K>();
        if (this.filter.isEmpty()) {
            return null;
        }

        filter.keySet().forEach(key -> {
            builder.hasField(key, filter.get(key));
        });

        return builder.build();
    }

    private Optional<Sort> toSort() {
        return sort.entrySet().stream()
                .map(kv ->
                        kv.getValue().equals(SortOptions.ASC) ?
                                Sort.by(kv.getKey()).ascending() :
                                Sort.by(kv.getKey()).descending()
                )
                .reduce(Sort::and);
    }
}

package com.postdm.backend.domain.estimate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class PageResponse<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean first;
    private boolean last;
    private List<SortInfo> sort;

    public static <T> PageResponse<T> from(Page<T> page) {
        List<SortInfo> sortInfo = page.getSort().stream()
                .map(SortInfo::new)
                .collect(Collectors.toList());

        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast(),
                sortInfo
        );
    }

    @Getter
    @AllArgsConstructor
    public static class SortInfo {
        private String property;
        private String direction;

        public SortInfo(Sort.Order order) {
            this.property = order.getProperty();
            this.direction = order.getDirection().name();
        }
    }
}
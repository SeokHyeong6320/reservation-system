package com.project.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;


/**
 * 페이징 결과를 반환하는 Response
 * 페이징 부가정보(페이지 번호, 총 갯수 등등) 반환하기 위해 생성
 */
@Getter
@AllArgsConstructor
@Builder
public class PageResponse <T>{

    private final List<T> content;
    private final int pageNumber;
    private final int pageSize;
    private final long totalElement;
    private final int totalPage;
    private final boolean last;

    public PageResponse(Page<T> page) {
        this.content = page.getContent();
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
        this.totalElement = page.getTotalElements();
        this.totalPage = page.getTotalPages();
        this.last = page.isLast();
    }

    public static <T> PageResponse<T> of(Page<T> page) {
        return new PageResponse<>(page);
    }
}

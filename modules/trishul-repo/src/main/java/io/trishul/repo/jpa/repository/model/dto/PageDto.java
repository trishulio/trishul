package io.trishul.repo.jpa.repository.model.dto;

import io.trishul.model.base.dto.BaseDto;
import java.util.Iterator;
import java.util.List;

public class PageDto<T extends BaseDto> extends BaseDto implements Iterable<T> {
    private List<T> content;
    private long totalElements;
    private int totalPages;

    public PageDto() {
        this(null, 0, 0);
    }

    public PageDto(List<T> content, int totalPages, long totalElements) {
        this.content = content;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public List<T> getContent() {
        return content;
    }

    public final void setContent(List<T> content) {
        this.content = content;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public final void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public final void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public Iterator<T> iterator() {
        return content.iterator();
    }
}

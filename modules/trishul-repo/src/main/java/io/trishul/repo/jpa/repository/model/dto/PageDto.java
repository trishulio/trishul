package io.trishul.repo.jpa.repository.model.dto;

import java.util.Iterator;
import java.util.List;
import io.trishul.model.base.dto.BaseDto;

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

  public final PageDto<T> setContent(List<T> content) {
    this.content = content;
    return this;
  }

  public long getTotalElements() {
    return totalElements;
  }

  public final PageDto<T> setTotalElements(long totalElements) {
    this.totalElements = totalElements;
    return this;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public final PageDto<T> setTotalPages(int totalPages) {
    this.totalPages = totalPages;
    return this;
  }

  @Override
  public Iterator<T> iterator() {
    return content.iterator();
  }
}

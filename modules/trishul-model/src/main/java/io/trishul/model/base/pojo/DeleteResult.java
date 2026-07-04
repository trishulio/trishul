package io.trishul.model.base.pojo;

public class DeleteResult extends BaseModel {
  private Long count;

  public DeleteResult() {}

  public DeleteResult(Long count) {
    this.count = count;
  }

  public Long getCount() {
    return count;
  }

  public DeleteResult setCount(Long count) {
    this.count = count;
    return this;
  }
}

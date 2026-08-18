package sh.trishul.model.base.dto;

public class DeleteResultDto extends BaseDto {
  private Long count;

  public DeleteResultDto() {}

  public DeleteResultDto(Long count) {
    this.count = count;
  }

  public Long getCount() {
    return count;
  }

  public DeleteResultDto setCount(Long count) {
    this.count = count;
    return this;
  }
}

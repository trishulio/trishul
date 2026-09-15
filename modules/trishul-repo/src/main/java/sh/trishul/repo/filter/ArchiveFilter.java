package sh.trishul.repo.filter;

public final class ArchiveFilter {
  public static final String NAME = "archiveFilter";
  public static final String PARAM_IS_ARCHIVED = "isArchived";
  public static final String CONDITION = "archived = :isArchived";

  private ArchiveFilter() {}
}

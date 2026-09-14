package sh.trishul.user.filter;

public final class OwnerFilter {
  public static final String NAME = "ownerFilter";
  public static final String PARAM_OWNER_USERNAME = "ownerUsername";
  public static final String CONDITION = "owner_username = :ownerUsername";

  private OwnerFilter() {}
}

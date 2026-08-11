package io.trishul.auth.session.context.holder;

import io.trishul.auth.session.context.PrincipalContext;
import java.util.UUID;

public class ThreadLocalContextHolder implements ContextHolder {
  private final InheritableThreadLocal<PrincipalContext> principalCtxContainer;
  private final InheritableThreadLocal<UUID> sessionTenantId;
  private final InheritableThreadLocal<String> requestId;

  public ThreadLocalContextHolder() {
    this.principalCtxContainer = new InheritableThreadLocal<>();
    this.sessionTenantId = new InheritableThreadLocal<>();
    this.requestId = new InheritableThreadLocal<>();
  }

  @Override
  public PrincipalContext getPrincipalContext() {
    return this.principalCtxContainer.get();
  }

  public ThreadLocalContextHolder setContext(PrincipalContext principalCtx) {
    this.principalCtxContainer.set(principalCtx);
    return this;
  }

  public ThreadLocalContextHolder setSessionTenantId(UUID tenantId) {
    if (tenantId == null) {
      throw new IllegalArgumentException("Tenant ID cannot be null");
    }

    if (getPrincipalContext() != null && !getPrincipalContext().getTenantIds().contains(tenantId)) {
      throw new IllegalArgumentException(
          String.format("Tenant ID %s not found in principal context", tenantId));
    }

    this.sessionTenantId.set(tenantId);
    return this;
  }

  @Override
  public UUID getSessionTenantId() {
    return this.sessionTenantId.get();
  }

  public ThreadLocalContextHolder setRequestId(String id) {
    this.requestId.set(id);
    return this;
  }

  @Override
  public String getRequestId() {
    return this.requestId.get();
  }

  @Override
  public void clear() {
    this.principalCtxContainer.remove();
    this.sessionTenantId.remove();
    this.requestId.remove();
  }
}

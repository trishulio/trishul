package io.trishul.auth.session.context.holder;

import io.trishul.auth.session.context.PrincipalContext;

public class ThreadLocalContextHolder implements ContextHolder {
  private final InheritableThreadLocal<PrincipalContext> principalCtxContainer;

  public ThreadLocalContextHolder() {
    this.principalCtxContainer = new InheritableThreadLocal<>();
  }

  @Override
  public PrincipalContext getPrincipalContext() {
    return this.principalCtxContainer.get();
  }

  public final ThreadLocalContextHolder setContext(PrincipalContext principalCtx) {
    this.principalCtxContainer.set(principalCtx);
    return this;
  }
}

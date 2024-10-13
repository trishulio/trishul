package io.trishul.auth.session.context.holder;

import io.trishul.auth.session.context.PrincipalContext;

public interface ContextHolder {
    PrincipalContext getPrincipalContext();
}

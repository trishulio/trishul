package io.trishul.iaas.auth.session.filters;

import io.trishul.iaas.auth.session.context.IaasAuthorizationCredentials;
import io.trishul.iaas.auth.session.context.IaasAuthorizationCredentialsBuilder;
import io.trishul.iaas.auth.session.context.holder.ThreadLocalIaasAuthorizationCredentialsHolder;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;

public class IaasAuthorizationCredentialsHolderFilter implements Filter {
    private final ThreadLocalIaasAuthorizationCredentialsHolder ctxHolder;
    private final IaasAuthorizationCredentialsBuilder credentialsBuilder;

    public IaasAuthorizationCredentialsHolderFilter(
            ThreadLocalIaasAuthorizationCredentialsHolder ctxHolder,
            IaasAuthorizationCredentialsBuilder credentialsBuilder) {
        this.ctxHolder = ctxHolder;
        this.credentialsBuilder = credentialsBuilder;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        setIaasAuthorizationCredentialsContext(request);

        chain.doFilter(request, response);
    }

    private void setIaasAuthorizationCredentialsContext(ServletRequest request) {
        IaasAuthorizationCredentials creds = this.credentialsBuilder.build(request);

        this.ctxHolder.setIaasAuthorizationCredentials(creds);
    }
}

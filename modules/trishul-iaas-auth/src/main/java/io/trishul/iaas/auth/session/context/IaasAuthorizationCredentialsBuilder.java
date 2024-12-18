package io.trishul.iaas.auth.session.context;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

public class IaasAuthorizationCredentialsBuilder {
    public IaasAuthorizationCredentials build(ServletRequest request) {
        if (!(request instanceof HttpServletRequest)) {
            throw new IllegalArgumentException(
                    String.format(
                            "Only HTTP requests are compatible, found: %s",
                            request.getClass().getSimpleName()));
        }

        HttpServletRequest r = (HttpServletRequest) request;

        String iaasToken = r.getHeader(IaasAuthorizationCredentials.HEADER_NAME_IAAS_TOKEN);

        if (iaasToken == null) {
            throw new RuntimeException(
                    String.format(
                            "No token set for header: %s",
                            IaasAuthorizationCredentials.HEADER_NAME_IAAS_TOKEN));
        }

        return new IaasAuthorizationCredentials(iaasToken);
    }
}

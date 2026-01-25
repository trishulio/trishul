package io.trishul.iaas.auth.session.context;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;

public class IaasAuthorizationCredentialsBuilder {
  public IaasAuthorizationCredentials build(ServletRequest request) {
    if (request == null) {
      throw new IllegalArgumentException("Request cannot be null");
    }

    if (!(request instanceof HttpServletRequest)) {
      throw new IllegalArgumentException(String.format(
          "Only HTTP requests are compatible, found: %s", request.getClass().getSimpleName()));
    }

    HttpServletRequest r = (HttpServletRequest) request;

    IaasAuthorizationCredentials creds = null;
    String iaasToken = r.getHeader(IaasAuthorizationCredentials.HEADER_NAME_IAAS_TOKEN);

    // Don't throw exception when token is missing during authorization to support
    // unauthenticated requests like /health
    if (iaasToken != null) {
      creds = new IaasAuthorizationCredentials(iaasToken);
    }

    return creds;
  }
}

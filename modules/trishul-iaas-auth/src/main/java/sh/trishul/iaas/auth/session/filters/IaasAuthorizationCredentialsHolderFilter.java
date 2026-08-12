package sh.trishul.iaas.auth.session.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;
import sh.trishul.iaas.auth.session.context.IaasAuthorizationCredentials;
import sh.trishul.iaas.auth.session.context.IaasAuthorizationCredentialsBuilder;
import sh.trishul.iaas.auth.session.context.holder.ThreadLocalIaasAuthorizationCredentialsHolder;

public class IaasAuthorizationCredentialsHolderFilter implements Filter {
  private final ThreadLocalIaasAuthorizationCredentialsHolder iaasAuthorizationCredentialsHolder;
  private final IaasAuthorizationCredentialsBuilder iaasAuthorizationCredentialsBuilder;

  public IaasAuthorizationCredentialsHolderFilter(
      ThreadLocalIaasAuthorizationCredentialsHolder iaasAuthorizationCredentialsHolder,
      IaasAuthorizationCredentialsBuilder iaasAuthorizationCredentialsBuilder) {
    this.iaasAuthorizationCredentialsHolder = iaasAuthorizationCredentialsHolder;
    this.iaasAuthorizationCredentialsBuilder = iaasAuthorizationCredentialsBuilder;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    setIaasAuthorizationCredentialsContext(request);

    chain.doFilter(request, response);
  }

  private void setIaasAuthorizationCredentialsContext(ServletRequest request) {
    IaasAuthorizationCredentials creds = this.iaasAuthorizationCredentialsBuilder.build(request);
    this.iaasAuthorizationCredentialsHolder.setIaasAuthorizationCredentials(creds);
  }
}

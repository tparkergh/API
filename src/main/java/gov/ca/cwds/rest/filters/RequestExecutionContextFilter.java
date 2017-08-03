package gov.ca.cwds.rest.filters;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import gov.ca.cwds.rest.filters.RequestExecutionContext.Parameter;
import gov.ca.cwds.security.shiro.web.PerryAuthenticatingFilter;

/**
 * Store common info when an HTTP request begins. Can be merged with
 * {@link PerryAuthenticatingFilter} when ready. See story #147865633.
 * 
 * @author CWDS API Team
 * @see RequestExecutionContextImpl
 */
@Provider
@WebFilter
public class RequestExecutionContextFilter implements Filter {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(RequestExecutionContextFilter.class);

  /**
   * Constructor
   */
  @Inject
  public RequestExecutionContextFilter() {
    // No-op.
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    if (request instanceof HttpServletRequest) {
      final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
      final HttpServletResponse httpServletResponse = (HttpServletResponse) response;
      RequestExecutionContextImpl.startRequest();
      try {
        LOGGER.info("started request at {}", new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.SSS")
            .format(RequestExecutionContext.instance().get(Parameter.REQUEST_START_TIME)));
        chain.doFilter(httpServletRequest, httpServletResponse);
      } catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
        throw e;
      } finally {
        RequestExecutionContextImpl.stopRequest();
      }
    }

  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    LOGGER.info("filterConfig={}", filterConfig.getFilterName());
  }

  @Override
  public void destroy() {
    // No-op.
  }

}

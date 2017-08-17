package gov.ca.cwds.rest;

import gov.ca.cwds.rest.api.domain.cms.SystemCodeCache;
import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Injector;
import com.google.inject.Module;

import gov.ca.cwds.inject.ApplicationModule;
import gov.ca.cwds.rest.filters.RequestExecutionContextFilter;
import gov.ca.cwds.rest.filters.RequestResponseLoggingFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Core execution class of CWDS REST API server application.
 * 
 * <h3>Standard command line arguments:</h3>
 * 
 * <blockquote> server config/api.yml </blockquote>
 * 
 * <h3>Standard JVM arguments:</h3>
 * 
 * <blockquote> -Ddb2.jcc.charsetDecoderEncoder=3
 * -Djava.library.path=${workspace_loc:CWDS_API}/lib:/usr/local/lib/ </blockquote>
 * 
 * @author CWDS API Team
 */
public class ApiApplication extends BaseApiApplication<ApiConfiguration> {

  @SuppressWarnings("unused")
  private static final Logger LOGGER = LoggerFactory.getLogger(ApiApplication.class);

  /**
   * Start the CWDS RESTful API application.
   * 
   * @param args command line
   * @throws Exception if startup fails
   */
  public static void main(final String[] args) throws Exception {
    new ApiApplication().run(args);
  }

  /**
   * {@inheritDoc}
   * 
   * @see gov.ca.cwds.rest.BaseApiApplication#applicationModule(io.dropwizard.setup.Bootstrap)
   */
  @Override
  public Module applicationModule(Bootstrap<ApiConfiguration> bootstrap) {
    return new ApplicationModule(bootstrap);
  }

  @Override
  public void runInternal(final ApiConfiguration configuration, final Environment environment) {
    Injector injector = guiceBundle.getInjector();

    environment.servlets()
        .addFilter("RequestExecutionContextManagingFilter",
            injector.getInstance(RequestExecutionContextFilter.class))
        .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");

    environment.servlets()
        .addFilter("AuditAndLoggingFilter",
            injector.getInstance(RequestResponseLoggingFilter.class))
        .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");

    injector.getInstance(SystemCodeCache.class);
  }
}

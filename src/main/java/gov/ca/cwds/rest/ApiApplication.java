package gov.ca.cwds.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Module;

import gov.ca.cwds.inject.ApplicationModule;
import io.dropwizard.setup.Bootstrap;

/**
 * Core execution class of CWDS REST API server application.
 * 
 * @author CWDS API Team
 */
public class ApiApplication extends BaseApiApplication<ApiConfiguration> {

  @SuppressWarnings("unused")
  private static final Logger LOGGER = LoggerFactory.getLogger(ApiApplication.class);

  public static void main(final String[] args) throws Exception {
    new ApiApplication().run(args);
  }

  /*
   * (non-Javadoc)
   * 
   * @see gov.ca.cwds.rest.BaseApiApplication#applicationModule(io.dropwizard.setup.Bootstrap)
   */
  @Override
  public Module applicationModule(Bootstrap<ApiConfiguration> bootstrap) {
    return new ApplicationModule(bootstrap);
  }



}

package gov.ca.cwds.rest.setup;

import java.util.concurrent.ExecutorService;

import javax.servlet.Servlet;
import javax.validation.Validator;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.ca.cwds.rest.services.Service;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.jetty.MutableServletContextHandler;
import io.dropwizard.jetty.setup.ServletEnvironment;
import io.dropwizard.lifecycle.setup.LifecycleEnvironment;
import io.dropwizard.setup.AdminEnvironment;
import io.dropwizard.setup.Environment;

/**
 * Decorator for {@link Environment}. Adds ability to register {@link Service}. Loaded from api.yml.
 * 
 * @author CWDS API Team
 */
public class ApiEnvironment {
  private Environment environment;

  /**
   * @param environment environment settings from YAML
   */
  public ApiEnvironment(Environment environment) {
    this.environment = environment;
  }

  /**
   * Returns the underlying DropWizard environment
   * 
   * @return The underlying environment
   */
  public Environment environment() {
    return environment;
  }

  /**
   * 
   * @return Jersey env settings
   * @see io.dropwizard.setup.Environment#jersey()
   */
  public JerseyEnvironment jersey() {
    return environment.jersey();
  }

  /**
   * Gets Java thread executor for health checks
   * 
   * @return the Java threading service
   * @see io.dropwizard.setup.Environment#getHealthCheckExecutorService()
   */
  public ExecutorService getHealthCheckExecutorService() {
    return environment.getHealthCheckExecutorService();
  }

  /**
   * @return DropWizard admin env settings
   * @see io.dropwizard.setup.Environment#admin()
   */
  public AdminEnvironment admin() {
    return environment.admin();
  }

  /**
   * @return DropWizard lifecycle env settings
   * @see io.dropwizard.setup.Environment#lifecycle()
   */
  public LifecycleEnvironment lifecycle() {
    return environment.lifecycle();
  }

  /**
   * @return DropWizard servlet env settings
   * @see io.dropwizard.setup.Environment#servlets()
   */
  public ServletEnvironment servlets() {
    return environment.servlets();
  }

  /**
   * @return Jackson ObjectMapper
   * @see io.dropwizard.setup.Environment#getObjectMapper()
   */
  public ObjectMapper getObjectMapper() {
    return environment.getObjectMapper();
  }

  /**
   * @return DropWizard setup env name
   * @see io.dropwizard.setup.Environment#getName()
   */
  public String getName() {
    return environment.getName();
  }

  /**
   * @return DropWizard validator
   * @see io.dropwizard.setup.Environment#getValidator()
   */
  public Validator getValidator() {
    return environment.getValidator();
  }

  /**
   * @param validator DropWizard validator
   * @see io.dropwizard.setup.Environment#setValidator(javax.validation.Validator)
   */
  public void setValidator(Validator validator) {
    environment.setValidator(validator);
  }

  /**
   * @return DropWizard metrics
   * @see io.dropwizard.setup.Environment#metrics()
   */
  public MetricRegistry metrics() {
    return environment.metrics();
  }

  /**
   * @return DropWizard env for health checks
   * @see io.dropwizard.setup.Environment#healthChecks()
   */
  public HealthCheckRegistry healthChecks() {
    return environment.healthChecks();
  }

  /**
   * @return DropWizard env application context
   * @see io.dropwizard.setup.Environment#getApplicationContext()
   */
  public MutableServletContextHandler getApplicationContext() {
    return environment.getApplicationContext();
  }

  /**
   * @return DropWizard Jersey servlet container for REST services
   * @see io.dropwizard.setup.Environment#getJerseyServletContainer()
   */
  public Servlet getJerseyServletContainer() {
    return environment.getJerseyServletContainer();
  }

  /**
   * @return DropWizard env admin context
   * @see io.dropwizard.setup.Environment#getAdminContext()
   */
  public MutableServletContextHandler getAdminContext() {
    return environment.getAdminContext();
  }
}

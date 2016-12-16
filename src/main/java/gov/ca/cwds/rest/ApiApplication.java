package gov.ca.cwds.rest;

import java.util.EnumSet;
import java.util.List;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.flywaydb.core.Flyway;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.inject.Injector;
import com.hubspot.dropwizard.guice.GuiceBundle;

import gov.ca.cwds.inject.ApplicationModule;
import gov.ca.cwds.rest.filters.RequestResponseLoggingFilter;
import gov.ca.cwds.rest.resources.SwaggerResource;
import gov.ca.cwds.rest.setup.ApiEnvironment;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.flyway.FlywayBundle;
import io.dropwizard.flyway.FlywayFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;

/**
 * Core execution class of CWDS REST API server application.
 * 
 * @author CWDS API Team
 */
public class ApiApplication extends Application<ApiConfiguration> {
  private static final Logger LOGGER = LoggerFactory.getLogger(ApiApplication.class);

  private GuiceBundle<ApiConfiguration> guiceBundle;

  private final FlywayBundle<ApiConfiguration> flywayBundle = new FlywayBundle<ApiConfiguration>() {
    @Override
    public DataSourceFactory getDataSourceFactory(ApiConfiguration configuration) {
      return configuration.getNsDataSourceFactory();
    }

    @Override
    public FlywayFactory getFlywayFactory(ApiConfiguration configuration) {
      return configuration.getFlywayFactory();
    }


  };

  public static void main(final String[] args) throws Exception {
    new ApiApplication().run(args);
  }

  @Override
  public void initialize(Bootstrap<ApiConfiguration> bootstrap) {
    bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
        bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(false)));

    bootstrap.addBundle(new ViewBundle<ApiConfiguration>());

    guiceBundle = GuiceBundle.<ApiConfiguration>newBuilder()
        .addModule(new ApplicationModule(bootstrap)).setConfigClass(ApiConfiguration.class)
        .enableAutoConfig(getClass().getPackage().getName()).build();

    bootstrap.addBundle(guiceBundle);
    bootstrap.addBundle(flywayBundle);
  }

  private void migrateDatabase(final ApiConfiguration configuration) {
    Flyway flyway = new Flyway();
    flyway.setDataSource(flywayBundle.getDataSourceFactory(configuration).build(null, "ds"));
    List<String> locations = flywayBundle.getFlywayFactory(configuration).getLocations();
    flyway.setLocations(locations.toArray(new String[locations.size()]));
    flyway.setSqlMigrationPrefix(
        flywayBundle.getFlywayFactory(configuration).getSqlMigrationPrefix());
    flyway.migrate();
  }

  @Override
  public void run(final ApiConfiguration configuration, final Environment environment)
      throws Exception {
    environment.jersey().getResourceConfig().packages(getClass().getPackage().getName())
        .register(DeclarativeLinkingFeature.class);

    environment.servlets().setSessionHandler(new SessionHandler());

    LOGGER.info("Application name: {}", configuration.getApplicationName());
    ApiEnvironment apiEnvironment = new ApiEnvironment(environment);

    LOGGER.info("Migrating Database");
    migrateDatabase(configuration);

    LOGGER.info("Configuring CORS: Cross-Origin Resource Sharing");
    configureCors(apiEnvironment);

    LOGGER.info("Configuring SWAGGER");
    configureSwagger(configuration, apiEnvironment);

    LOGGER.info("Registering Filters");
    registerFilters(environment);
  }

  public void registerFilters(final Environment environment) {
    Injector injector = guiceBundle.getInjector();
    environment.servlets()
        .addFilter("AuditAndLoggingFilter",
            injector.getInstance(RequestResponseLoggingFilter.class))
        .addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), true, "/*");
  }

  protected void configureCors(final ApiEnvironment apiEnvironment) {
    FilterRegistration.Dynamic filter =
        apiEnvironment.servlets().addFilter("CORS", CrossOriginFilter.class);
    filter.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
    filter.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
    filter.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
    filter.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "*");
    filter.setInitParameter("allowedHeaders",
        "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,X-Auth-Token");
    filter.setInitParameter("allowCredentials", "true");
  }

  protected void configureSwagger(final ApiConfiguration apiConfiguration,
      final ApiEnvironment apiEnvironment) {
    BeanConfig config = new BeanConfig();
    config.setTitle(apiConfiguration.getSwaggerConfiguration().getTitle());
    config.setDescription(apiConfiguration.getSwaggerConfiguration().getDescription());
    config.setResourcePackage(apiConfiguration.getSwaggerConfiguration().getResourcePackage());
    config.setScan(true);

    new AssetsBundle(apiConfiguration.getSwaggerConfiguration().getAssetsPath(),
        apiConfiguration.getSwaggerConfiguration().getAssetsPath(), null, "swagger")
            .run(apiEnvironment.environment());
    apiEnvironment.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
    apiEnvironment.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    LOGGER.info("Registering ApiListingResource");
    apiEnvironment.jersey().register(new ApiListingResource());

    LOGGER.info("Registering SwaggerResource");
    final SwaggerResource swaggerResource =
        new SwaggerResource(apiConfiguration.getSwaggerConfiguration());
    apiEnvironment.jersey().register(swaggerResource);
  }
}

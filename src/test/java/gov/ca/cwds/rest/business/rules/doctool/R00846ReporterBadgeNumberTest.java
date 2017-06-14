package gov.ca.cwds.rest.business.rules.doctool;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.ca.cwds.data.CrudsDao;
import gov.ca.cwds.rest.api.domain.cms.Reporter;
import gov.ca.cwds.rest.core.Api;
import gov.ca.cwds.rest.resources.cms.JerseyGuiceRule;
import gov.ca.cwds.rest.resources.cms.ReporterResource;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.testing.junit.ResourceTestRule;

import java.io.IOException;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squarespace.jersey2.guice.JerseyGuiceUtils;

/**
 * Test Class for DocTool Rule : R - 00846 - Badge Number specification
 * 
 * @author CWDS API Team
 */
@SuppressWarnings("javadoc")
public class R00846ReporterBadgeNumberTest {

  private static final String ROOT_RESOURCE = "/" + Api.RESOURCE_REPORTER + "/";

  private static final ReporterResource mockedReporterResource = mock(ReporterResource.class);

  @After
  public void ensureServiceLocatorPopulated() {
    JerseyGuiceUtils.reset();
  }

  @ClassRule
  public static JerseyGuiceRule rule = new JerseyGuiceRule();

  @ClassRule
  public static final ResourceTestRule resources = ResourceTestRule.builder()
      .addResource(mockedReporterResource).build();

  private static final ObjectMapper MAPPER = Jackson.newObjectMapper();

  @Before
  public void setup() throws Exception {
    @SuppressWarnings("rawtypes")
    CrudsDao crudsDao = mock(CrudsDao.class);
    when(crudsDao.find(any())).thenReturn(mock(gov.ca.cwds.data.persistence.cms.Reporter.class));
    Reporter validReporter = validReporter();

    when(mockedReporterResource.create(eq(validReporter))).thenReturn(
        Response.status(Response.Status.NO_CONTENT).entity(null).build());
  }


  /**
   * <blockquote>
   * 
   * <pre>
   * 
   * DocTool Rule : "R - 00846" - Badge Number specification
   * 
   * BADGE NUMBER may only be specified if the REPORTER is associated with LAW ENFORCEMENT.
   * Access Logic: If Law Enforcement is entered, then Badge Number is enabled, else disabled.
   * </pre>
   * 
   * </blockquote>
   * 
   * @OnlyIf(property = "badgeNumber", ifProperty = "lawEnforcementId")
   * @throws Exception general error
   */
  @Test
  public void testBadgeNumberIsSpecifiedButLawEnforcementIdIsEmptyFails() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/badgeNumberNotLawEnforcementId.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class),
        is(equalTo("{\"errors\":[\"badgeNumber can only be set if lawEnforcementId is set\"]}")));
  }

  @Test
  public void testBadgeNumberIsEmptyButLawEnforcementIdIsSpecifiedSuccess() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/valid/badgeNumberEmptyLawEnforcementId.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  /*
   * Utilities
   */
  private Reporter validReporter() throws JsonParseException, JsonMappingException, IOException {

    Reporter validReporter =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/valid/valid.json"),
            Reporter.class);
    return validReporter;

  }
}

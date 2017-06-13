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



  /*
   * badgeNumber Tests
   * 
   * Referential Integrity Rule tests R - 00846 Badge Number specification. BADGE NUMBER may only be
   * specified if the REPORTER is associated with LAW ENFORCEMENT.
   * 
   * @OnlyIf(property = "badgeNumber", ifProperty = "lawEnforcementId")
   */
  @Test
  public void testBadgeNumberTooLong() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(
            fixture("fixtures/domain/legacy/Reporter/invalid/badgeNumberTooLong.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(422)));
    assertThat(response.readEntity(String.class),
        is(equalTo("{\"errors\":[\"badgeNumber size must be less than or equal to 6\"]}")));
  }

  @Test
  public void testBadgeNumberNotLawEnforcementIdFails() throws Exception {
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
  public void testBadgeNumberEmptyLawEnforcementIdFails() throws Exception {
    Reporter toCreate =
        MAPPER
            .readValue(
                fixture("fixtures/domain/legacy/Reporter/invalid/badgeNumberEmptyLawEnforcementId.json"),
                Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testBadgeNumberEmptySuccess() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/valid/badgeNumberEmpty.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testBadgeNumberMissingSuccess() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/valid/badgeNumberMissing.json"),
            Reporter.class);
    Response response =
        resources.client().target(ROOT_RESOURCE).request().accept(MediaType.APPLICATION_JSON)
            .post(Entity.entity(toCreate, MediaType.APPLICATION_JSON));
    assertThat(response.getStatus(), is(equalTo(204)));
  }

  @Test
  public void testBadgeNumberNullSuccess() throws Exception {
    Reporter toCreate =
        MAPPER.readValue(fixture("fixtures/domain/legacy/Reporter/valid/badgeNumberNull.json"),
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
